package testes;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;


public class Faces {

	public static void main(String[] args) {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		CascadeClassifier cascadeClassifier = new CascadeClassifier("haarcascade_frontalface_alt.xml");
		// Mat mat = Highgui.imread(System.getProperty("user.dir")
		// +"/chaves.jpg");
		Mat image = Imgcodecs.imread("img/faces.jpg");
		MatOfRect matOfRect = detectarFaces(cascadeClassifier, image);
		List<PropriedadesFace> propsFaces = obterDadosFaces(matOfRect);
		BufferedImage imagemCorteDesfoque = DesfocarImagem(image);
		propsFaces = CortarImagem(propsFaces, imagemCorteDesfoque);
		BufferedImage imagemSemEfeitos = converterParaImage(image);
		imagemCorteDesfoque = juntarImagens(propsFaces, imagemSemEfeitos);
		File outputfile = new File("img/face desfocada.jpg");
		try {
			ImageIO.write(imagemCorteDesfoque, "jpg", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Passo 1 - Detectar faces
	public static MatOfRect detectarFaces(CascadeClassifier cascadeClassifier, Mat mat) {
		MatOfRect matOfRect = new MatOfRect();
		cascadeClassifier.detectMultiScale(mat, matOfRect);
		return matOfRect;
	}

	// Passo 2 - Obter dados das faces
	public static List<PropriedadesFace> obterDadosFaces(MatOfRect matOfRect) {

		List<PropriedadesFace> dados = new ArrayList<PropriedadesFace>();

		for (Rect rect : matOfRect.toArray()) {

			PropriedadesFace prop = new PropriedadesFace();
			prop.setX(rect.x);
			prop.setY(rect.y);
			prop.setHeight(rect.height);
			prop.setWidth(rect.width);

			dados.add(prop);

		}

		return dados;
	}

	// Passo 3 - Desfocar toda a imagem
	public static BufferedImage DesfocarImagem(Mat mat) {

		mat = Desfocar(mat);

		return converterParaImage(mat);
	}

	private static Mat Desfocar(Mat image) {

		Mat destination = new Mat(image.rows(), image.cols(), image.type());

		Imgproc.GaussianBlur(image, destination, new Size(45, 45), 0);

		return destination;
	}

	// Converter imagem
	public static BufferedImage converterParaImage(Mat image) {

		MatOfByte bytemat = new MatOfByte();

		// Highgui.imencode(".jpg", image, bytemat);
		Imgcodecs.imencode(".jpg", image, bytemat);

		byte[] bytes = bytemat.toArray();

		InputStream in = new ByteArrayInputStream(bytes);

		BufferedImage img = null;

		try {
			img = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return img;
	}

	// Passo 4 - Recortar as faces da imagem
	public static List<PropriedadesFace> CortarImagem(List<PropriedadesFace> dados, BufferedImage imagem) {

		for (PropriedadesFace dado : dados) {
			dado.setImageCortada(imagem.getSubimage(dado.getX(), dado.getY(), dado.getWidth(), dado.getHeight()));
		}

		return dados;
	}

	// Passo 6 - Juntar imagem com as faces recortadas
	public static BufferedImage juntarImagens(List<PropriedadesFace> dados, BufferedImage imagemPrincipal) {

		for (PropriedadesFace dado : dados) {
			imagemPrincipal = juntarUmaImage(imagemPrincipal, dado.getImageCortada(), dado.getX(), dado.getY());
		}

		return imagemPrincipal;

	}

	public static BufferedImage juntarUmaImage(BufferedImage imagemPrincipal, BufferedImage imagemCortada, int x,
			int y) {

		Graphics2D g = imagemPrincipal.createGraphics();

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.drawImage(imagemPrincipal, 0, 0, null);

		g.drawImage(imagemCortada, x, y, null);

		g.dispose();
		return imagemPrincipal;
	}

}
