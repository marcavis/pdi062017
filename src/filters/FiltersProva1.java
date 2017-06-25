package filters;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class FiltersProva1 {

	public static boolean ehPreto(int[] pixel) {
		return pixel[0] == 0 && pixel[1] == 0 && pixel[2] == 0;
	}
	
	public static boolean testaRetangulo(BufferedImage img) {
		WritableRaster raster = img.getRaster();
		int pixels[] = new int[4];
		int primeiroPontoPreto[] = new int[2];
		boolean primeiroPontoEncontrado = false;
		int ultimoPontoPreto[] = new int[2];
		
		for (int i = 1; i < img.getWidth()-1; i++) {
			for (int j = 1; j < img.getHeight()-1; j++) {
				raster.getPixel(i, j, pixels);
				if(ehPreto(pixels)) {
					//Sempre tem a primeira vez, mas ela apenas acontece uma vez
					if(!primeiroPontoEncontrado) {
						primeiroPontoEncontrado = true;
						primeiroPontoPreto[0] = i;
						primeiroPontoPreto[1] = j;
					}
					ultimoPontoPreto[0] = i;
					ultimoPontoPreto[1] = j;
				}
			}
		}
		
		//testar os pixels do retângulo...
		for (int i = primeiroPontoPreto[0]; i <= ultimoPontoPreto[0]; i++) {
			for (int j = primeiroPontoPreto[1]; j <= ultimoPontoPreto[1]; j++) {
				//...mas apenas se estiverem na borda
				if(i == primeiroPontoPreto[0] || i == ultimoPontoPreto[0] ||
						j == primeiroPontoPreto[1] || j == ultimoPontoPreto[1]) {
					raster.getPixel(i, j, pixels);
					if(!ehPreto(pixels)) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	public static BufferedImage equalizarDiagonal(BufferedImage img) {
		return equalizarDiagonal(img, true, true, true);
	}
	
	public static BufferedImage equalizarDiagonal(BufferedImage img, boolean equalizarVermelho,
			boolean equalizarVerde, boolean equalizarAzul) {
		WritableRaster raster = img.getRaster();
		BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		int tamanho = img.getWidth() * img.getHeight();
		int pixels[] = new int[4];
		int[][] histogramaNormal = Filters.histograma(img);
		int[][] acumulado = Filters.histogramaAcumulado(histogramaNormal);
		int[] tons = Filters.tonsExistentes(histogramaNormal);
		for (int i = 1; i < img.getWidth()-1; i++) {
			for (int j = 1; j < img.getHeight()-1; j++) {
				raster.getPixel(i, j, pixels);
				//se x maior que y, equalizar
				if(i > j) {
					if(equalizarVermelho)
						pixels[0] = ((tons[0] - 1) * acumulado[0][pixels[0]]) / tamanho;
					if(equalizarVerde)
						pixels[1] = ((tons[1] - 1) * acumulado[1][pixels[1]]) / tamanho;
					if(equalizarAzul)
						pixels[2] = ((tons[2] - 1) * acumulado[2][pixels[2]]) / tamanho;
					raster.setPixel(i, j, pixels);
				} else if (i == j) {
					int[] pixelPreto = {0,0,0,0};
					raster.setPixel(i, j, pixelPreto);
				}
			}
		}
		try {
			newImg.setData(raster);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newImg;
	}
	
//	public static boolean estouNoQuadrante(int i, int j, int largura, int altura, int quadrante) {
//		switch (quadrante) {
//		case 1:
//			return i < largura/2 && j < altura/2;
//		case 2:
//			return i >= largura/2 && j < altura/2;
//		case 3:
//			return i < largura/2 && j >= altura/2;
//		case 4:
//			return i >= largura/2 && j >= altura/2;
//		default:
//			return false;
//		}
//	}
//	
	public static int qualQuadrante(int i, int j, int largura, int altura) {
		int resultado = 1;
		if(i >= largura/2)
			resultado++;
		if(j >= altura/2)
			resultado += 2;
		return resultado;
	}
	
	//public static int opostoNoQuadrante
	
	public static BufferedImage rotacionarEmQuadrantes(BufferedImage img, int quadranteA, int quadranteB) {
		//int graus = 180;
		WritableRaster raster = img.getRaster();
		BufferedImage newImg;
		newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		WritableRaster newRaster = newImg.getRaster();
		int pixels[] = new int[4];
		int ni, nj;
		int largura = img.getWidth();
		int altura = img.getHeight();
		
		for (int i = 1; i < largura-1; i++) {
			for (int j = 1; j < altura-1; j++) {
				int quadrante = qualQuadrante(i, j, largura, altura);
				ni = i;
				nj = j;
				raster.getPixel(i, j, pixels);
				if(quadrante == quadranteA || quadrante == quadranteB) {
					switch (quadrante) {
					case 1:
						ni = largura/2 - i;
						nj = altura/2 - j;
						break;
					case 2:
						ni = largura - i + largura/2 - 1;
						nj = altura/2 - j;
						break;
					case 3:
						ni = largura/2 - i;
						nj = altura - j + altura/2 - 1;
						break;
					case 4:
						ni = largura - i + largura/2 - 1;
						nj = altura - j + altura/2 - 1;
						break;
					default:
						break;
					}
				}
				newRaster.setPixel(ni, nj, pixels);
			}
		}
		try {
			newImg.setData(newRaster);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newImg;
	}
}
