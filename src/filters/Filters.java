package filters;
//fazer 19/04/2017 entregar sistema completo c/erosao e dilatacao 
//p/clavison@gmail.com
//entrega 19:15


import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Arrays;

public class Filters {
	public static BufferedImage cinza(BufferedImage img, int r, int g, int b) {
		if(r == 0 && g == 0 && b == 0) {
			r = 1; g = 1; b = 1;
		}
		WritableRaster raster = img.getRaster();
		BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		int pixels[] = new int[4];
		for (int i = 1; i < img.getWidth()-1; i++) {
			for (int j = 1; j < img.getHeight()-1; j++) {
				//System.out.println(i + " " + j);
				raster.getPixel(i, j, pixels);
				double media = (pixels[0]*r + pixels[1]*g + pixels[2]*b)/(r+g+b);
				pixels[0] = (int)media;
				pixels[1] = (int)media;
				pixels[2] = (int)media;
				raster.setPixel(i, j, pixels);
			}
		}
		try {
			newImg.setData(raster);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newImg;
	}
	
	public static BufferedImage cinza(BufferedImage img) {
		return cinza(img, 1, 1, 1);
	}
	
	public static BufferedImage cinzaZebrado(BufferedImage img, int colunas) {
		if(colunas <= 0)
			colunas = 1;
		WritableRaster raster = img.getRaster();
		BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		int tamanhoDaColuna = img.getWidth()/colunas;
		int pixels[] = new int[4];
		for (int i = 1; i < img.getWidth()-1; i++) {
			for (int j = 1; j < img.getHeight()-1; j++) {
				//System.out.println(i + " " + j);
				raster.getPixel(i, j, pixels);
				if((i/tamanhoDaColuna) % 2 == 0) {
					double media = (pixels[0] + pixels[1] + pixels[2])/3;
					pixels[0] = (int)media;
					pixels[1] = (int)media;
					pixels[2] = (int)media;
				}
				raster.setPixel(i, j, pixels);
			}
		}
		try {
			newImg.setData(raster);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newImg;
	}
	
	//nao acessivel no programa ateh o momento
	public static BufferedImage cinzaQuadriculado(BufferedImage img, int linhas, int colunas) {
		if(colunas <= 0)
			colunas = 1;
		if(linhas <= 0)
			linhas = 1;
		WritableRaster raster = img.getRaster();
		BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		int tamanhoDaColuna = img.getWidth()/colunas;
		int tamanhoDaLinha = img.getWidth()/colunas;
		int pixels[] = new int[4];
		for (int i = 1; i < img.getWidth()-1; i++) {
			for (int j = 1; j < img.getHeight()-1; j++) {
				//System.out.println(i + " " + j);
				raster.getPixel(i, j, pixels);
				if(((i/tamanhoDaColuna) % 2 == 0) ^ ((j/tamanhoDaLinha) % 2 == 0)) {
					double media = (pixels[0] + pixels[1] + pixels[2])/3;
					pixels[0] = (int)media;
					pixels[1] = (int)media;
					pixels[2] = (int)media;
				}
				raster.setPixel(i, j, pixels);
			}
		}
		try {
			newImg.setData(raster);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newImg;
	}
	
	public static BufferedImage limiarizarCinza(BufferedImage img, int limiar) {
		WritableRaster raster = img.getRaster();
		BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		int pixels[] = new int[4];
		for (int i = 1; i < img.getWidth()-1; i++) {
			for (int j = 1; j < img.getHeight()-1; j++) {
				//System.out.println(i + " " + j);
				raster.getPixel(i, j, pixels);
				double media = (pixels[0] + pixels[1] + pixels[2])/3;
				if(media >= limiar) {
					pixels[0] = 255; pixels[1] = 255; pixels[2] = 255;
				} else {
					pixels[0] = 0; pixels[1] = 0; pixels[2] = 0;
				}
				raster.setPixel(i, j, pixels);
			}
		}
		try {
			newImg.setData(raster);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newImg;
	}
	
	public static BufferedImage limiarizarCores(BufferedImage img, int limiar) {
		WritableRaster raster = img.getRaster();
		BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		int pixels[] = new int[4];
		for (int i = 1; i < img.getWidth()-1; i++) {
			for (int j = 1; j < img.getHeight()-1; j++) {
				//System.out.println(i + " " + j);
				raster.getPixel(i, j, pixels);
				for(int cor = 0; cor <= 2; cor++) {
					if (pixels[cor] >= limiar)
						pixels[cor] = 255;
					else
						pixels[cor] = 0;
				}
				raster.setPixel(i, j, pixels);
			}
		}
		try {
			newImg.setData(raster);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newImg;
	}
	
	public static BufferedImage negativa(BufferedImage img) {
		return negativa(img, true, true, true);
	}
	
	public static BufferedImage negativa(BufferedImage img, boolean inverterR, boolean inverterG, boolean inverterB) {
		WritableRaster raster = img.getRaster();
		BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		int pixels[] = new int[4];
		for (int i = 1; i < img.getWidth()-1; i++) {
			for (int j = 1; j < img.getHeight()-1; j++) {
				//System.out.println(i + " " + j);
				raster.getPixel(i, j, pixels);
				if(inverterR)
					pixels[0] = 255 - pixels[0];
				if(inverterG)
					pixels[1] = 255 - pixels[1];
				if(inverterB)
					pixels[2] = 255 - pixels[2];
				raster.setPixel(i, j, pixels);
			}
		}
		try {
			newImg.setData(raster);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newImg;
	}
	
	public static BufferedImage remocaoRuido(BufferedImage img, NeighboringMethod nm, AveragingMethod am) {
		WritableRaster raster = img.getRaster();
		BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		int pixels[] = new int[4];
		for (int i = 1; i < img.getWidth()-1; i++) {
			for (int j = 1; j < img.getHeight()-1; j++) {
				//System.out.println(i + " " + j);
				raster.getPixel(i, j, pixels);
				int[][] area = pegaVizinhos(i, j, nm);
				
				for(int cor = 0; cor < 3; cor++) {
					int[] pixelsVizinhos = new int[area.length];
					for(int k = 0; k < area.length; k++) {
						int estePixel[] = new int[4];
						raster.getPixel(area[k][0], area[k][1], estePixel);
						pixelsVizinhos[k] = estePixel[cor];
					}
					if(am == AveragingMethod.MEAN)
						pixels[cor] = media(pixelsVizinhos);
					else
						pixels[cor] = mediana(pixelsVizinhos);
				}
				raster.setPixel(i, j, pixels);
			}
		}
		try {
			newImg.setData(raster);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newImg;
	}

	public static BufferedImage ampliar(BufferedImage img) {
		return ampliar(img, 2);
	}
	
	public static BufferedImage ampliar(BufferedImage img, int fator) {
		WritableRaster raster = img.getRaster();
		BufferedImage newImg = new BufferedImage(img.getWidth()*fator, img.getHeight()*fator, BufferedImage.TYPE_INT_RGB);
		WritableRaster newRaster = newImg.getRaster();
		int pixels[] = new int[4];
		for (int i = 1; i < img.getWidth()-1; i++) {
			for (int j = 1; j < img.getHeight()-1; j++) {
				raster.getPixel(i, j, pixels);
				for(int ni = i*fator; ni < i * fator + fator; ni++) {
					for(int nj = j*fator; nj < j * fator + fator; nj++) {
						//System.out.println(ni + " " + nj + " " + newImg.getHeight() + " " + newImg.getWidth());
						newRaster.setPixel(ni, nj, pixels);
					}
				}
			}
		}
		try {
			newImg.setData(newRaster);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newImg;
	}
	
	
	public static BufferedImage reduzir(BufferedImage img) {
		return reduzir(img, 2);
	}
	
	public static BufferedImage reduzir(BufferedImage img, int fator) {
		WritableRaster raster = img.getRaster();
		BufferedImage newImg = new BufferedImage(img.getWidth()/fator, img.getHeight()/fator, BufferedImage.TYPE_INT_RGB);
		WritableRaster newRaster = newImg.getRaster();
		int pixels[] = new int[4];
		for (int ni = 1; ni < newImg.getWidth()-1; ni++) {
			for (int nj = 1; nj < newImg.getHeight()-1; nj++) {
				int[][] area = selecao(ni*fator, nj*fator, fator, fator);
				for(int cor = 0; cor < 3; cor++) {
					int[] pixelsDeOrigem = new int[area.length];
					for(int k = 0; k < area.length; k++) {
						int estePixel[] = new int[4];
						raster.getPixel(area[k][0], area[k][1], estePixel);
						pixelsDeOrigem[k] = estePixel[cor];
					}
					pixels[cor] = media(pixelsDeOrigem);
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
	
	//recebe 90, 180 ou 270 graus
	public static BufferedImage rotacionar(BufferedImage img, int graus) {
		WritableRaster raster = img.getRaster();
		BufferedImage newImg;
		if(graus == 180)
			newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		else
			newImg = new BufferedImage(img.getHeight(), img.getWidth(), BufferedImage.TYPE_INT_RGB);
		WritableRaster newRaster = newImg.getRaster();
		int pixels[] = new int[4];
		int ni, nj;
		for (int i = 1; i < img.getWidth()-1; i++) {
			for (int j = 1; j < img.getHeight()-1; j++) {
				ni = i;
				nj = j;
				raster.getPixel(i, j, pixels);
				switch (graus) {
				case 90:
					ni = newImg.getWidth() - j;
					nj = i;
					break;
				case 180:
					ni = newImg.getWidth() - i;
					nj = newImg.getHeight() - j;
					break;
				case 270:
					ni = j;
					nj = newImg.getHeight() - i;
					break;
				default:
					break;
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
	
	public static BufferedImage selecionar(BufferedImage img, int x1, int y1, int x2, int y2) {
		WritableRaster raster = img.getRaster();
		BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		int pixels[] = new int[4];
		for(int i = 0; i < 3; i++)
			pixels[i] = 0;
		if(x1 > x2) {
			int aux = x1;
			x1 = x2;
			x2 = aux;
		}
		if(y1 > y2) {
			int aux = y1;
			y1 = y2;
			y2 = aux;
		}
		for (int i = x1; i <= x2; i++) {
			for (int j = y1; j <= y2; j++) {
				if(i == x1 || i == x2 || j == y1 || j == y2)
					raster.setPixel(i, j, pixels);
			}
		}
		try {
			newImg.setData(raster);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newImg;
	}
	
	public static BufferedImage adicionar(BufferedImage img1, BufferedImage img2) {
		return adicionar(img1, 50, img2, 50);
	}
	
	public static BufferedImage adicionar(BufferedImage img1, int proporcao1, BufferedImage img2, int proporcao2) {
		WritableRaster raster1 = img1.getRaster();
		WritableRaster raster2 = img2.getRaster();
		int newWidth = Math.max(img1.getWidth(), img2.getWidth());
		int newHeight = Math.max(img1.getHeight(), img2.getHeight());
		BufferedImage newImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
		WritableRaster newRaster = newImg.getRaster();
		
		//centralizar as imagens entre si
		int startImg1x = 0;
		int startImg2x = (img1.getWidth() - img2.getWidth())/2;
		int startImg1y = 0;
		int startImg2y = (img1.getHeight() - img2.getHeight())/2;
		if(startImg2x < 0) {
			startImg1x = -startImg2x;
			startImg2x = 0;
		}
		if(startImg2y < 0) {
			startImg1y = -startImg2y;
			startImg2y = 0;
		}
		
		int pixels[] = {0,0,0,0};
		
		for(int i = 0; i < newImg.getWidth(); i++) {
			for(int j = 0; j < newImg.getHeight(); j++) {
				int pixels1[] = {0, 0, 0, 0};
				int pixels2[] = {0, 0, 0, 0};
				if(i > startImg1x && i < newImg.getWidth() - startImg1x - 1 && 
						j > startImg1y && j < newImg.getHeight() - startImg1y - 1 ) {
					raster1.getPixel(i - startImg1x, j - startImg1y, pixels1);
					
				}
				if(i > startImg2x && i < newImg.getWidth() - startImg2x - 1 && 
						j > startImg2y && j < newImg.getHeight() - startImg2y - 1 ) {
					raster2.getPixel(i - startImg2x, j - startImg2y, pixels2);
				}
				for(int canal = 0; canal <= 3; canal++)
					pixels[canal] = (pixels1[canal] * proporcao1 + pixels2[canal] * proporcao2)/100;
				newRaster.setPixel(i, j, pixels);
			}
		}
		try {
			newImg.setData(newRaster);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newImg;
	}
	
	public static BufferedImage subtrair(BufferedImage img1, BufferedImage img2) {
		WritableRaster raster1 = img1.getRaster();
		WritableRaster raster2 = img2.getRaster();
		int newWidth = Math.max(img1.getWidth(), img2.getWidth());
		int newHeight = Math.max(img1.getHeight(), img2.getHeight());
		BufferedImage newImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
		WritableRaster newRaster = newImg.getRaster();
		
		//centralizar as imagens entre si
		int startImg1x = 0;
		int startImg2x = (img1.getWidth() - img2.getWidth())/2;
		int startImg1y = 0;
		int startImg2y = (img1.getHeight() - img2.getHeight())/2;
		if(startImg2x < 0) {
			startImg1x = -startImg2x;
			startImg2x = 0;
		}
		if(startImg2y < 0) {
			startImg1y = -startImg2y;
			startImg2y = 0;
		}
		
		int pixels[] = {0,0,0,0};
		
		for(int i = 0; i < newImg.getWidth(); i++) {
			for(int j = 0; j < newImg.getHeight(); j++) {
				int pixels1[] = {0, 0, 0, 0};
				int pixels2[] = {0, 0, 0, 0};
				if(i > startImg1x && i < newImg.getWidth() - startImg1x - 1 && 
						j > startImg1y && j < newImg.getHeight() - startImg1y - 1 ) {
					raster1.getPixel(i - startImg1x, j - startImg1y, pixels1);
				}
				if(i > startImg2x && i < newImg.getWidth() - startImg2x - 1 && 
						j > startImg2y && j < newImg.getHeight() - startImg2y - 1 ) {
					raster2.getPixel(i - startImg2x, j - startImg2y, pixels2);
				}
				for(int canal = 0; canal <= 3; canal++)
					pixels[canal] = Math.abs(pixels1[canal] - pixels2[canal]);
				newRaster.setPixel(i, j, pixels);
			}
		}
		try {
			newImg.setData(newRaster);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newImg;
	}
	
	public static int[][] histograma(BufferedImage img) {
		WritableRaster raster = img.getRaster();
		
		int red[] = new int[256];
		Arrays.fill(red, 0);
		int green[] = new int[256];
		Arrays.fill(green, 0);
		int blue[] = new int[256];
		Arrays.fill(blue, 0);
		int pixels[] = new int[4];
		for (int i = 1; i < img.getWidth()-1; i++) {
			for (int j = 1; j < img.getHeight()-1; j++) {
				raster.getPixel(i, j, pixels);
				red[pixels[0]]++;
				green[pixels[1]]++;
				blue[pixels[2]]++;
			}
		}
		int[][] resultado = {red, green, blue};
		return resultado;
	}
	
	public static int[][] histograma(BufferedImage img, int classes) throws Exception {
		if(256 % classes > 0)
			throw new Exception("Quantidade de divisões do histograma deve ser uma potência de 2");
		int tamanhoDaClasse = 256/classes;
		WritableRaster raster = img.getRaster();
		int red[] = new int[classes];
		Arrays.fill(red, 0);
		int green[] = new int[classes];
		Arrays.fill(green, 0);
		int blue[] = new int[classes];
		Arrays.fill(blue, 0);
		int pixels[] = new int[4];
		for (int i = 1; i < img.getWidth()-1; i++) {
			for (int j = 1; j < img.getHeight()-1; j++) {
				raster.getPixel(i, j, pixels);
				//exemplo: se o pixel tiver valor 255, com 16 divisões no histograma (cada coluna tem 16 valores)
				//então 255/16 = 15 será a classe que será adicionada.
				red[pixels[0]/tamanhoDaClasse]++;
				green[pixels[1]/tamanhoDaClasse]++;
				blue[pixels[2]/tamanhoDaClasse]++;
			}
		}
		int[][] resultado = {red, green, blue};
		return resultado;
	}
	
	public static BufferedImage mostraHistograma(int[] red, int[] green, int[] blue) {
		BufferedImage newImg = new BufferedImage(1044, 420, BufferedImage.TYPE_INT_RGB);
		WritableRaster histoRaster = newImg.getRaster();
		int maxRed = maximo(red);
		int maxGreen = maximo(green);
		int maxBlue = maximo(blue);
		int maximos[] = {maxRed, maxGreen, maxBlue};
		int maxHistograma = maximo(maximos);
		int escala = maxHistograma/400 + 1;
		int[] pixelVermelho = {255,0,0,0};
		int[] pixelVerde = {0,255,0,0};
		int[] pixelAzul = {0,0,255,0};
		
		//usar o tamanho de um dos histogramas já que eles não terão necessariamente 256 níveis
		for(int i = 0; i < red.length; i++) {
			for(int j = 409 - red[i]/escala; j <= 409; j++)
				histoRaster.setPixel(10 + i*4, j, pixelVermelho);
			for(int j = 409 - green[i]/escala; j <= 409; j++)
				histoRaster.setPixel(11 + i*4, j, pixelVerde);
			for(int j = 409 - blue[i]/escala; j <= 409; j++)
				histoRaster.setPixel(12 + i*4, j, pixelAzul);
		}
		
		try {
			newImg.setData(histoRaster);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newImg;
	}
	
	public static BufferedImage mostraHistogramaCinza(int[] red, int[] green, int[] blue) {
		BufferedImage newImg = new BufferedImage(532, 420, BufferedImage.TYPE_INT_RGB);
		WritableRaster histoRaster = newImg.getRaster();
		int maxRed = maximo(red);
		int maxGreen = maximo(green);
		int maxBlue = maximo(blue);
		int maximos[] = {maxRed, maxGreen, maxBlue};
		int maxHistograma = maximo(maximos);
		int escala = maxHistograma/400 + 1;
		int[] pixelCinza = {191,191,191,0};
		for(int i = 0; i < red.length; i++) {
			int media = ((red[i]+green[i]+blue[i])/escala)/3;
			for(int j = 409 - media; j <= 409; j++) {
				histoRaster.setPixel(10 + i*2, j, pixelCinza);
			}
		}
		try {
			newImg.setData(histoRaster);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newImg;
	}
	
	public static int[][] histogramaAcumulado(int[][] histograma) {
		int[][] acumulado = new int[3][histograma[0].length];
		int valorAcumulado[] = {0, 0, 0};
		for(int cor = 0; cor < 3; cor++) {
			for(int i = 0; i < acumulado[0].length; i++) {
				valorAcumulado[cor] += histograma[cor][i];
				acumulado[cor][i] = valorAcumulado[cor];
			}
		}
		return acumulado;
	}
	
	public static int[] histogramaMedio(int[][] histograma) {
		int[] resultado = new int[histograma[0].length];
		for(int i = 0; i < resultado.length; i++) {
			resultado[i] = (histograma[0][i] + histograma[1][i] +
					histograma[2][i]) / 3;
		}
		return resultado;
	}
	
	public static int[] tonsExistentes(int[][] histograma) {
		int tons[] = {0,0,0};
		for (int i = 0; i < histograma[0].length; i++) {
			for (int cor = 0; cor < 3; cor++) {
				if(histograma[cor][i] > 0) {
					tons[cor]++;
				}
			}
		}
		return tons;
	}
	
	public static int tonsExistentes(int[] histogramaMedio) {
		int tons = 0;
		for (int i = 0; i < histogramaMedio.length; i++) {
			if(histogramaMedio[i] > 0) {
				tons++;
			}
		}
		return tons;
	}
	
	public static BufferedImage equalizar(BufferedImage img) {
		return equalizar(img, true, true, true);
	}
	
	public static BufferedImage equalizar(BufferedImage img, boolean equalizarVermelho,
			boolean equalizarVerde, boolean equalizarAzul) {
		WritableRaster raster = img.getRaster();
		BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		int tamanho = img.getWidth() * img.getHeight();
		int pixels[] = new int[4];
		int[][] histogramaNormal = histograma(img);
		int[][] acumulado = histogramaAcumulado(histogramaNormal);
		int[] tons = tonsExistentes(histogramaNormal);
		for (int i = 1; i < img.getWidth()-1; i++) {
			for (int j = 1; j < img.getHeight()-1; j++) {
				raster.getPixel(i, j, pixels);
				if(equalizarVermelho)
					pixels[0] = ((tons[0] - 1) * acumulado[0][pixels[0]]) / tamanho;
				if(equalizarVerde)
					pixels[1] = ((tons[1] - 1) * acumulado[1][pixels[1]]) / tamanho;
				if(equalizarAzul)
					pixels[2] = ((tons[2] - 1) * acumulado[2][pixels[2]]) / tamanho;
				raster.setPixel(i, j, pixels);
			}
		}
		try {
			newImg.setData(raster);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newImg;
	}
	
	public static BufferedImage dilatar(BufferedImage img, int[][] struct, int corValida) {
		WritableRaster raster = img.getRaster();
		BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		WritableRaster newRaster = newImg.getRaster();
		int[] pixelDilatador;
		if(corValida == 0) {
			pixelDilatador = new int[] {0,0,0,0};
		} else {
			pixelDilatador = new int[] {255,255,255,0};
		}
		int pixels[] = new int[4];
		for (int i = 1; i < img.getWidth()-1; i++) {
			for (int j = 1; j < img.getHeight()-1; j++) {
				raster.getPixel(i, j, pixels);
				newRaster.setPixel(i, j, pixels);
				if(isSameGrayscale(pixels, corValida)) {
					for (int[] pixelStr : struct) {
						if(pixelStr[0] + i >= 0 && pixelStr[0] + i < img.getWidth() &&
								pixelStr[1] + j >= 0 && pixelStr[1] + j < img.getHeight()) {
							newRaster.setPixel(pixelStr[0] + i, pixelStr[1] + j, pixelDilatador);
						}
					}
				} 
			}
		}
		try {
			newImg.setData(newRaster);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newImg;
	}
	
	public static BufferedImage erodir(BufferedImage img, int[][] struct, int corErosao) {
		WritableRaster raster = img.getRaster();
		BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		WritableRaster newRaster = newImg.getRaster();
		int[] pixelErosao;
		if(corErosao == 0) {
			pixelErosao = new int[] {255,255,255,0};
		} else {
			pixelErosao = new int[] {0,0,0,0};
		}
		int pixels[] = new int[4];
		for (int i = 1; i < img.getWidth()-1; i++) {
			for (int j = 1; j < img.getHeight()-1; j++) {
				raster.getPixel(i, j, pixels);
				newRaster.setPixel(i, j, pixels);
				boolean todosValidos = true;
				for(int[] pixelStr : struct) {
					if(pixelStr[0] + i >= 0 && pixelStr[0] + i < img.getWidth() &&
											pixelStr[1] + j >= 0 && pixelStr[1] + j < img.getHeight()) {
						raster.getPixel(pixelStr[0] + i, pixelStr[1] + j, pixels);
						if(!isSameGrayscale(pixels, corErosao)) {
							todosValidos = false;
						}
					}
				}
				if(!todosValidos)
					newRaster.setPixel(i, j, pixelErosao);
			}
		}
		try {
			newImg.setData(newRaster);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newImg;
	}
	
	//experimento interessante na tentativa de segmentar; mantém os pixels que estão em regiões com pixels vizinhos muito diferentes
	public static BufferedImage bordasERuido(BufferedImage img, int regioes) throws Exception {
		WritableRaster raster = img.getRaster();
		BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		WritableRaster newRaster = newImg.getRaster();
		int[][] histograma = histograma(img, 16);
		int[][] struct = Filters.structuringElement(StructuringElement.CIRCLE7);
		int pixels[] = new int[4];
		int pixelVizinho[] = new int[4];
		//int[] picos = encontraPicos(histograma);
		for (int i = 1; i < img.getWidth()-1; i++) {
			for (int j = 1; j < img.getHeight()-1; j++) {
				raster.getPixel(i, j, pixels);
				raster.getPixel(i, j, pixels);
				boolean manterPixel = true;
				for (int[] pixelStr : struct) {
					if(pixelStr[0] + i >= 0 && pixelStr[0] + i < img.getWidth() &&
							pixelStr[1] + j >= 0 && pixelStr[1] + j < img.getHeight()) {
						raster.getPixel(pixelStr[0] + i, pixelStr[1] + j, pixelVizinho);
						if(isDifferentColor(pixels, pixelVizinho, 100)) {
							manterPixel = false;
						}
					}
				}
				if(!manterPixel) {
					newRaster.setPixel(i, j, pixels);
				}
			}
		}
		try {
			newImg.setData(newRaster);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newImg;
	}
	
	public static BufferedImage segmentarErrado(BufferedImage img, int regioes) throws Exception {
		WritableRaster raster = img.getRaster();
		BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		WritableRaster newRaster = newImg.getRaster();
		int[][] histograma = histograma(img, 16);
		int[][] struct = Filters.structuringElement(StructuringElement.CIRCLE3);
		int pixels[] = new int[4];
		int pixelVizinho[] = new int[4];
		int pixelSaidaUsado[] = new int[4];
		int[] pixelVermelho = {255,0,0,0};
		int[] pixelVerde = {0,255,0,0};
		int[] pixelAzul = {0,0,255,0};
		int[] pixelCinza = {127,127,127,0};
		//int[] picos = encontraPicos(histograma);
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				newRaster.setPixel(i, j, pixelCinza);
			}
		}
		for (int i = 1; i < img.getWidth()-1; i++) {
			for (int j = 1; j < img.getHeight()-1; j++) {
				raster.getPixel(i, j, pixels);
				raster.getPixel(i, j, pixels);
				boolean manterPixel = true;
				int[][] pixelsSaidaUsados = new int[struct.length][4];
				int qualVizinho = 0;
				for (int[] pixelStr : struct) {
					if(pixelStr[0] + i >= 0 && pixelStr[0] + i < img.getWidth() &&
							pixelStr[1] + j >= 0 && pixelStr[1] + j < img.getHeight()) {
						raster.getPixel(pixelStr[0] + i, pixelStr[1] + j, pixelVizinho);
						//ver qual cor de segmento foi usado antes para esse pixel vizinho
						if(isDifferentColor(pixels, pixelVizinho, 100)) {
							manterPixel = false;
						}
						newRaster.getPixel(pixelStr[0] + i, pixelStr[1] + j, pixelSaidaUsado);
						pixelsSaidaUsados[qualVizinho] = pixelSaidaUsado;
						qualVizinho++;
					}
				}
				if(manterPixel) {
					int[] segmentoAtual = new int[4];
					newRaster.getPixel(i-1, j-1, segmentoAtual);
					newRaster.setPixel(i, j, segmentoAtual);
				} else {
					newRaster.setPixel(i, j, novaCor(pixels));
				}
			}
		}
		try {
			newImg.setData(newRaster);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newImg;
	}
	
	public static BufferedImage segmentar(BufferedImage img, int regioes) throws Exception {
		WritableRaster raster = img.getRaster();
		BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		WritableRaster newRaster = newImg.getRaster();
		int pixels[] = new int[4];
		int pixelMedio[] = new int[4];
		for (int i = 1; i < img.getWidth()-1; i++) {
			for (int j = 1; j < img.getHeight()-1; j++) {
				raster.getPixel(i, j, pixels);
				int[][] area = new int[][] {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 0},
					{0, 1}, {1, -1}, {1, 0}, {1, 1}};
				for(int cor = 0; cor < 3; cor++) {
					int[] pixelsVizinhos = new int[9];
					for(int k = 0; k < area.length; k++) {
						int estePixel[] = new int[4];
						raster.getPixel(i + area[k][0], j + area[k][1], estePixel);
						pixelsVizinhos[k] = estePixel[cor];
					}
					pixelMedio[cor] = media(pixelsVizinhos);
				}
				newRaster.setPixel(i, j, novaCor(pixelMedio));
			}
		}
		try {
			newImg.setData(newRaster);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newImg;
	}
	
	private static int[] novaCor(int[] pixel) {
		if(pixel[0] >= pixel[1] && pixel[0] >= pixel[2])
			return new int[] {200,0,0,0};
		if(pixel[1] >= pixel[0] && pixel[1] >= pixel[2])
			return new int[] {0,200,0,0};
		if(pixel[2] >= pixel[0] && pixel[2] >= pixel[1])
			return new int[] {0,0,200,0};
		return new int[] {127,127,127,0};
	}
	
//	private static int[] corMaisPopular(int[] pixel, int[][] pixelsSaidaUsados) {
//		int[] cores = new int[pixelsSaidaUsados.length];
//		for (int i = 0; i < cores.length; i++) {
//			
//		}
//		//se o segmento mais comum nessa area for o preto, criar um novo segmento com a cor atual
//		if(maisPopular[0] == 0 &&
//				maisPopular[1] == 0 &&
//				maisPopular[2] == 0)
//			return pixel;
//	}
	
	private static boolean isDifferentColor(int[] pixel, int[] vizinho, int pontoDeCorte) {
		return (Math.abs(pixel[0] - vizinho[0]) + Math.abs(pixel[1] - vizinho[1]) +
				Math.abs(pixel[2] - vizinho[2])) > pontoDeCorte;
	}
	
	//retorna verdadeiro se um pixel é um tom de cinza perfeitamente igual à cor {i, i, i}
	private static boolean isSameGrayscale(int[] pixels, int i) {
		return pixels[0] == pixels[1] && pixels[1] == pixels[2] &&
				pixels[2] == i;
	}

	//funções auxiliares
	private static int[][] pegaVizinhos (int i, int j, NeighboringMethod nm) {
		if(nm == NeighboringMethod.STAR) {
			return new int[][] {{i-1, j-1}, {i+1, j-1}, {i, j}, {i-1, j+1}, {i+1, j+1}};
		} else if (nm == NeighboringMethod.CROSS) {
			return new int[][] {{i, j-1}, {i-1, j}, {i, j}, {i+1, j}, {i, j+1}};
		} else {
			return new int[][] {{i-1, j-1}, {i, j-1}, {i+1, j-1},
								{i-1, j}, {i, j}, {i+1, j},
								{i-1, j+1}, {i, j+1}, {i+1, j+1}};
		}
	}
	
	//private static int[] encontraPicos (int[][] histograma) {
	//	
	//}
	
	private static int[][] selecao(int i, int j, int largura, int altura) {
		int[][] selecionados = new int[largura*altura][2];
		int posicaoSel = 0;
		for(int x = 0; x < largura; x++) {
			for(int y = 0; y < altura; y++) {
				selecionados[posicaoSel][0] = i+x;
				selecionados[posicaoSel][1] = j+y;
				posicaoSel++;
			}
		}
		return selecionados;
	}

	public static int media(int[] valores) {
		int total = 0;
		for (int i : valores) {
			total += i;
		}
		return total/valores.length;
	}
	
	public static int mediana(int[] valores) {
		int posicaoMediana = valores.length/2;
		int[] ordenada = ordenar(valores);
		if (valores.length % 2 == 0)
			return (ordenada[posicaoMediana] + ordenada[posicaoMediana - 1])/2;
		else
			return ordenada[posicaoMediana];
			//weird effects, try it
			//return ordenada[valores.length-2];
	}
	
	public static int[] ordenar(int[] valores) {
		for (int i = 0; i < valores.length; i++) {
			int posMenor = i;
			int menor = 256;
			int j;
			for (j = i; j < valores.length; j++) {
				if (valores[j] < menor) {
					menor = valores[j];
					posMenor = j;
				}
			}
			int aux = valores[i];
			valores[i] = menor;
			valores[posMenor] = aux;
		}
		return valores;
	}

	public static int maximo(int[] valores) {
		int max = 0;
		for (int x : valores) {
			if (x > max)
				max = x;
		}
		return max;
	}
	
	public static int[][] structuringElement(StructuringElement strEl) {
		if (strEl == StructuringElement.CIRCLE3) {
			return new int[][] {{0, -1}, {-1, 0}, {1, 0}, {0, 1}};
		} else if (strEl == StructuringElement.CIRCLE5) { 
			return new int[][] {
						  {-1, -2}, {0, -2}, {1, -2},
				{-2, -1}, {-1, -1}, {0, -1}, {1, -1}, {2, -1},
				{-2, 0},  {-1, 0},           {1, 0},  {2, 0},
				{-2, 1},  {-1, 1},  {0, 1},  {1, 1},  {2, 1},
						  {-1, 2},  {0, 2},  {1, 2}};
		}
		else {
			return new int[][] {
				                      {0, -3},
				  {-2, -2}, {-1, -2}, {0, -2}, {1, -2}, {2, -2},
		{-3, -1}, {-2, -1}, {-1, -1}, {0, -1}, {1, -1}, {2, -1}, {3, -1},
		{-3, 0},  {-2, 0},  {-1, 0},           {1, 0},  {2, 0},  {3, 0},
		{-3, 1},  {-2, 1},  {-1, 1},  {0, 1},  {1, 1},  {2, 1},  {3, 1},
				  {-2, 2},  {-1, 2},  {0, 2},  {1, 2},  {2, 2},
				                      {0, 3}};
		}
	}
}