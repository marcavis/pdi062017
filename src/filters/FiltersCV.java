package filters;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class FiltersCV {
	public static BufferedImage sobel(Mat origem) throws IOException {
		Mat origemCinza = new Mat();
		Mat borrado = new Mat();
		Mat sobelX = new Mat();
		Mat sobelY = new Mat();
		Mat sobelX2 = new Mat();
		Mat sobelY2 = new Mat();
		Imgproc.cvtColor(origem, origemCinza, Imgproc.COLOR_RGB2GRAY);
		Imgproc.GaussianBlur(origemCinza, borrado, new Size(3, 3), 0);
		Imgproc.Sobel(origem, sobelX, CvType.CV_16S, 1, 0);
		Imgproc.Sobel(origem, sobelY, CvType.CV_16S, 0, 1);
		Core.convertScaleAbs(sobelX, sobelX2);
		Core.convertScaleAbs(sobelY, sobelY2);
		Imgcodecs.imwrite("temp/__sobelX.bmp", sobelX2);
		Imgcodecs.imwrite("temp/__sobelY.bmp", sobelY2);
		BufferedImage resultado = Filters.adicionar(ImageIO.read(new File("temp/__sobelX.bmp")),
				ImageIO.read(new File("temp/__sobelY.bmp")));
		return resultado;
	}
	
	public static BufferedImage laplace(Mat origem) throws IOException {
		BufferedImage resultado = Filters.adicionar(ImageIO.read(new File("temp/__sobelX.bmp")),
				ImageIO.read(new File("temp/__sobelY.bmp")));
		return resultado;
	}
	
//	public static BufferedImage canny(Mat origem) throws IOException {
//		BufferedImage resultado = Filters.adicionar(ImageIO.read(new File("temp/__sobelX.bmp")),
//				ImageIO.read(new File("temp/__sobelY.bmp")));
//		return resultado;
//	}
	
//	public static BufferedImage canny(Mat origem) throws IOException {
//		//Mat gray = Imgcodecs.imread(pathImagem, 0);	
//		Mat processada = origem;
//		Imgproc.Canny(processada, processada, 100, 200, 3, true);
//		Imgcodecs.imwrite("temp/__placa.bmp", processada);
//		return ImageIO.read(new File("temp/__placa.bmp"));
//	}
	
	public static void canny(String pathImagem){
		Mat imagem = Imgcodecs.imread(pathImagem, 0);	
		Imgproc.Canny(imagem, imagem, 100, 200, 3, true); 
		Imgcodecs.imwrite("temp/_canny.bmp", imagem);	
	}
	
	public static void hough(String pathImagem) {
		Mat imagem = Imgcodecs.imread(pathImagem, 0);
		//Imgproc.HoughCircles(imagem, circles, method, dp, minDist);
		Mat circles = new Mat();
		int minRadius = 130;
		int maxRadius = 900;
		Imgproc.HoughCircles(imagem, circles, Imgproc.CV_HOUGH_GRADIENT, 1, minRadius, 120, 10, minRadius, maxRadius);
		//String wtf = circles.get(0, 0);
		//System.out.println(wtf);
		System.out.println("dsasasasasasssssssssssss");
		Imgcodecs.imwrite("temp/_hough.bmp", imagem);
	}
}
