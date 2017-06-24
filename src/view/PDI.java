package view;
//materiais = https://goo.gl/NnqCVQ

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import filters.AveragingMethod;
import filters.Filters;
import filters.NeighboringMethod;
import filters.StructuringElement;

public class PDI extends Shell {
	private Label labelR;
	private Label labelG;
	private Label labelB;
	
	private FileDialog fileDialog;
	private Image image1, image2, image3;
	private String pathImg1, pathImg2, pathImg3;
	private CLabel label1;
	private CLabel label2;
	private CLabel label3;
	private Text textCinzaR;
	private Text textCinzaG;
	private Text textCinzaB;
	private Text textCinzaZebras;
	private Button btnLimiarCinza;
	private Scale scaleLimiarCinza;
	private Button btnLimiarCores1;
	private Scale scaleLimiarCores1;
	private Button btnEstrela;
	private Button btnCruz;
	private Button btnQuadrado;
	private Button btnMedia;
	private Button btnMediana;
	private Button btnInverterR;
	private Button btnInverterG;
	private Button btnInverterB;
	private Button btn90;
	private Button btn180;
	private Button btn270;
	private int selectedX1;
	private int selectedY1;
	private int selectedX2;
	private int selectedY2;
	private Spinner spinnerA1;
	private Spinner spinnerA2;
	private Button btnEqualizarR;
	private Button btnEqualizarG;
	private Button btnEqualizarB;
	private Button btn3x3;
	private Button btn5x5;
	private Button btn7x7;
	private Button btnPreto;
	private Button btnBranco;
	private Spinner spinnerSegmentos;
	private Button btnBinaria;
	private Button btnBinariaInvertida;
	private Button btnAte0;
	private Button btnAte0Invertida;
	private Button btnTruncada;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			PDI shell = new PDI(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public PDI(Display display) {
		super(display, SWT.SHELL_TRIM);
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME);
		
		Menu menu = new Menu(this, SWT.BAR);
		setMenuBar(menu);
		
		MenuItem mntmimagem = new MenuItem(menu, SWT.CASCADE);
		mntmimagem.setText("&Imagem");
		
		Menu menu_1 = new Menu(mntmimagem);
		mntmimagem.setMenu(menu_1);
		
		MenuItem mntmHistograma = new MenuItem(menu_1, SWT.NONE);
		mntmHistograma.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					int[][] dadosHistograma = Filters.histograma(ImageIO.read(new File(pathImg1)));
					BufferedImage histograma = Filters.mostraHistograma(dadosHistograma[0],
							dadosHistograma[1], dadosHistograma[2]);
					pathImg3 = "temp/_histograma.bmp";
					ImageIO.write(histograma, "bmp", new File("temp/_histograma.bmp"));
					image3 = new Image(null, "temp/_histograma.bmp");
					carregaImagem(label3, image3);
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		mntmHistograma.setText("&Histograma");
		
		MenuItem mntmHistogramaCinza = new MenuItem(menu_1, SWT.NONE);
		mntmHistogramaCinza.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					int[][] dadosHistograma = Filters.histograma(ImageIO.read(new File(pathImg1)));
					BufferedImage histogramaCinza = Filters.mostraHistogramaCinza(dadosHistograma[0],
							dadosHistograma[1], dadosHistograma[2]);
					pathImg3 = "temp/_histogramaCinza.bmp";
					ImageIO.write(histogramaCinza, "bmp", new File("temp/_histogramaCinza.bmp"));
					image3 = new Image(null, "temp/_histogramaCinza.bmp");
					carregaImagem(label3, image3);
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		mntmHistogramaCinza.setText("Histograma (Tons de &Cinza)");
		
		MenuItem mntmHistogramaAcumulado = new MenuItem(menu_1, SWT.NONE);
		mntmHistogramaAcumulado.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					int[][] dadosHistograma = Filters.histogramaAcumulado(Filters.histograma(ImageIO.read(new File(pathImg1))));
					BufferedImage histogramaAcumulado = Filters.mostraHistograma(dadosHistograma[0],
							dadosHistograma[1], dadosHistograma[2]);
					pathImg3 = "temp/_histogramaAcumulado.bmp";
					ImageIO.write(histogramaAcumulado, "bmp", new File("temp/_histogramaAcumulado.bmp"));
					image3 = new Image(null, "temp/_histogramaAcumulado.bmp");
					carregaImagem(label3, image3);
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		mntmHistogramaAcumulado.setText("Histograma (&Acumulado)");
		
		MenuItem mntmHistogramaAcumuladoCinza = new MenuItem(menu_1, SWT.NONE);
		mntmHistogramaAcumuladoCinza.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					int[][] dadosHistograma = Filters.histogramaAcumulado(Filters.histograma(ImageIO.read(new File(pathImg1))));
					BufferedImage histogramaAcumuladoCinza = Filters.mostraHistogramaCinza(dadosHistograma[0],
							dadosHistograma[1], dadosHistograma[2]);
					pathImg3 = "temp/_histogramaAcumuladoCinza.bmp";
					ImageIO.write(histogramaAcumuladoCinza, "bmp", new File("temp/_histogramaAcumuladoCinza.bmp"));
					image3 = new Image(null, "temp/_histogramaAcumuladoCinza.bmp");
					carregaImagem(label3, image3);
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		mntmHistogramaAcumuladoCinza.setText("Histograma (Acumulado, Cin&za)");
		
		new MenuItem(menu_1, SWT.SEPARATOR);
		
		MenuItem mntmequalizar = new MenuItem(menu_1, SWT.NONE);
		mntmequalizar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					BufferedImage equalizada = Filters.equalizar(ImageIO.read(new File(pathImg1)));
					pathImg3 = "temp/_equalizada.bmp";
					ImageIO.write(equalizada, "bmp", new File("temp/_equalizada.bmp"));
					image3 = new Image(null, "temp/_equalizada.bmp");
					carregaImagem(label3, image3);
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		mntmequalizar.setText("&Equalizar");
		
		labelR = new Label(this, SWT.NONE);
		labelR.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		labelR.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD));
		labelR.setBounds(10, 10, 90, 30);
		labelR.setText("R:");
		
		labelG = new Label(this, SWT.NONE);
		labelG.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD));
		labelG.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		labelG.setText("G:");
		labelG.setBounds(10, 46, 90, 30);
		
		labelB = new Label(this, SWT.NONE);
		labelB.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD));
		labelB.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
		labelB.setText("B:");
		labelB.setBounds(10, 82, 90, 30);
		
		Button btnAbreImagem1 = new Button(this, SWT.NONE);
		btnAbreImagem1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				pathImg1 = fileDialog.open();
				abreImagem(1);
			}
		});
		btnAbreImagem1.setBounds(10, 167, 90, 25);
		btnAbreImagem1.setText("Abre Imagem 1");
		
		Button btnAbreImagem2 = new Button(this, SWT.NONE);
		btnAbreImagem2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				pathImg2 = fileDialog.open();
				abreImagem(2);
			}
		});
		btnAbreImagem2.setText("Abre Imagem 2");
		btnAbreImagem2.setBounds(316, 167, 90, 25);
		
		Button btnCopiarDeImagem31 = new Button(this, SWT.NONE);
		btnCopiarDeImagem31.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				image1 = new Image(null, pathImg3);
				pathImg1 = pathImg3;
				carregaImagem(label1, image1);
			}
		});
		btnCopiarDeImagem31.setText("Copiar de Imagem 3");
		btnCopiarDeImagem31.setBounds(106, 167, 119, 25);
		
		Button btnCopiarDeImagem32 = new Button(this, SWT.NONE);
		btnCopiarDeImagem32.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				image2 = new Image(null, pathImg3);
				pathImg2 = pathImg3;
				carregaImagem(label2, image2);				
			}
		});
		btnCopiarDeImagem32.setText("Copiar de Imagem 3");
		btnCopiarDeImagem32.setBounds(412, 167, 119, 25);
		
		ScrolledComposite scrolledComposite1 = new ScrolledComposite(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite1.setBounds(10, 198, 300, 300);
		
		label1 = new CLabel(scrolledComposite1, SWT.BORDER | SWT.CENTER);
		label1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				selectedX1 = e.x;
				selectedY1 = e.y;
			}
			@Override
			public void mouseUp(MouseEvent e) {
				selectedX2 = e.x;
				selectedY2 = e.y;
				ImageData imd = image1.getImageData();
				if(selectedX2 < 0)
					selectedX2 = 0;
				if(selectedY2 < 0)
					selectedY2 = 0;
				if(selectedX2 > imd.width)
					selectedX2 = imd.width - 1;
				if(selectedY2 > imd.height)
					selectedY2 = imd.height - 1;
				try {
					BufferedImage selecionada = Filters.selecionar(ImageIO.read(new File(pathImg1)),
							selectedX1, selectedY1, selectedX2, selectedY2);
					pathImg3 = "temp/_selecionada.bmp";
					ImageIO.write(selecionada, "bmp", new File(pathImg3));
					image3 = new Image(null, pathImg3);
					carregaImagem(label3, image3);
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		label1.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				encontraPixel(e.x, e.y, 1);
			}
		});
		label1.setText("");
		scrolledComposite1.setContent(label1);
		scrolledComposite1.setMinSize(label1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		ScrolledComposite scrolledComposite2 = new ScrolledComposite(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite2.setBounds(316, 198, 300, 300);
		
		label2 = new CLabel(scrolledComposite2, SWT.BORDER | SWT.CENTER);
		label2.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				encontraPixel(e.x, e.y, 2);
			}
		});
		label2.setText("");
		scrolledComposite2.setContent(label2);
		scrolledComposite2.setMinSize(label2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		ScrolledComposite scrolledComposite3 = new ScrolledComposite(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite3.setBounds(622, 198, 600, 500);
		
		label3 = new CLabel(scrolledComposite3, SWT.BORDER | SWT.CENTER);
		label3.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				encontraPixel(e.x, e.y, 3);
			}
		});
		label3.setText("");
		scrolledComposite3.setContent(label3);
		scrolledComposite3.setMinSize(label3.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		CTabFolder tabFolder = new CTabFolder(this, SWT.BORDER);
		tabFolder.setTabPosition(SWT.BOTTOM);
		tabFolder.setBounds(106, 10, 1016, 150);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		CTabItem tbtmTonsDecinza = new CTabItem(tabFolder, SWT.NONE);
		tbtmTonsDecinza.setText("Tons de &Cinza");
		tabFolder.setSelection(0);
		
		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmTonsDecinza.setControl(composite);
		
		Button btnCinzaArit = new Button(composite, SWT.NONE);
		btnCinzaArit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					BufferedImage cinzaSimples = Filters.cinza(ImageIO.read(new File(pathImg1)));
					pathImg3 = "temp/_cinzaSimples.bmp";
					ImageIO.write(cinzaSimples, "bmp", new File("temp/_cinzaSimples.bmp"));
					image3 = new Image(null, "temp/_cinzaSimples.bmp");
					carregaImagem(label3, image3);
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		btnCinzaArit.setBounds(10, 10, 124, 25);
		btnCinzaArit.setText("Tons de Cinza");
		
		Button btnCinzaPond = new Button(composite, SWT.NONE);
		btnCinzaPond.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					int r = Integer.parseInt(textCinzaR.getText());
					int g = Integer.parseInt(textCinzaG.getText());
					int b = Integer.parseInt(textCinzaB.getText());
					BufferedImage cinzaPonderado = Filters.cinza(ImageIO.read(new File(pathImg1)), r, g, b);
					pathImg3 = "temp/_cinzaPonderado.bmp";
					ImageIO.write(cinzaPonderado, "bmp", new File("temp/_cinzaPonderado.bmp"));
					image3 = new Image(null, "temp/_cinzaPonderado.bmp");
					carregaImagem(label3, image3);
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		btnCinzaPond.setText("Cinza Ponderado");
		btnCinzaPond.setBounds(168, 10, 124, 25);
		
		textCinzaR = new Text(composite, SWT.BORDER);
		textCinzaR.setBounds(168, 69, 36, 21);
		
		textCinzaG = new Text(composite, SWT.BORDER);
		textCinzaG.setBounds(213, 69, 36, 21);
		
		textCinzaB = new Text(composite, SWT.BORDER);
		textCinzaB.setBounds(256, 69, 36, 21);
		
		Label lblR = new Label(composite, SWT.NONE);
		lblR.setAlignment(SWT.CENTER);
		lblR.setText("R");
		lblR.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		lblR.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.BOLD));
		lblR.setBounds(168, 41, 36, 22);
		
		Label lblG = new Label(composite, SWT.NONE);
		lblG.setAlignment(SWT.CENTER);
		lblG.setText("G");
		lblG.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		lblG.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.BOLD));
		lblG.setBounds(213, 41, 36, 22);
		
		Label lblB = new Label(composite, SWT.NONE);
		lblB.setAlignment(SWT.CENTER);
		lblB.setText("B");
		lblB.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
		lblB.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.BOLD));
		lblB.setBounds(256, 41, 36, 22);
		
		Button btnCinzaZebrado = new Button(composite, SWT.NONE);
		btnCinzaZebrado.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					int colunas = Integer.parseInt(textCinzaZebras.getText());
					BufferedImage cinzaZebrado = Filters.cinzaZebrado(ImageIO.read(new File(pathImg1)), colunas);
					pathImg3 = "temp/_cinzaZebrado.bmp";
					ImageIO.write(cinzaZebrado, "bmp", new File("temp/_cinzaZebrado.bmp"));
					image3 = new Image(null, "temp/_cinzaZebrado.bmp");
					carregaImagem(label3, image3);
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		btnCinzaZebrado.setText("Cinza Zebrado");
		btnCinzaZebrado.setBounds(326, 10, 124, 25);
		
		Label lblCinzaColunas = new Label(composite, SWT.NONE);
		lblCinzaColunas.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		lblCinzaColunas.setAlignment(SWT.CENTER);
		lblCinzaColunas.setBounds(326, 41, 124, 22);
		lblCinzaColunas.setText("Colunas");
		
		textCinzaZebras = new Text(composite, SWT.BORDER);
		textCinzaZebras.setBounds(326, 69, 124, 21);
		
		CTabItem tbtmLimiarizacao = new CTabItem(tabFolder, SWT.NONE);
		tbtmLimiarizacao.setText("&Limiariza\u00E7\u00E3o");
		
		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmLimiarizacao.setControl(composite_1);
		
		scaleLimiarCinza = new Scale(composite_1, SWT.NONE);
		scaleLimiarCinza.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnLimiarCinza.getSelection()) {
					try {
						BufferedImage limiarCinza = Filters.limiarizarCinza(ImageIO.read(new File(pathImg1)), scaleLimiarCinza.getSelection());
						pathImg3 = "temp/_limiarCinza.bmp";
						ImageIO.write(limiarCinza, "bmp", new File("temp/_limiarCinza.bmp"));
						image3 = new Image(null, "temp/_limiarCinza.bmp");
						carregaImagem(label3, image3);
					} catch (Exception f) {
						f.printStackTrace();
					}
				}
			}
		});
		scaleLimiarCinza.setPageIncrement(16);
		scaleLimiarCinza.setMaximum(255);
		scaleLimiarCinza.setSelection(128);
		scaleLimiarCinza.setBounds(10, 48, 170, 42);
		
		btnLimiarCinza = new Button(composite_1, SWT.CHECK);
		btnLimiarCinza.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnLimiarCinza.getSelection()) {
					try {
						BufferedImage limiarCinza = Filters.limiarizarCinza(ImageIO.read(new File(pathImg1)), scaleLimiarCinza.getSelection());
						pathImg3 = "temp/_limiarCinza.bmp";
						ImageIO.write(limiarCinza, "bmp", new File("temp/_limiarCinza.bmp"));
						image3 = new Image(null, "temp/_limiarCinza.bmp");
						carregaImagem(label3, image3);
					} catch (Exception f) {
						f.printStackTrace();
					}
				}
			}
		});
		btnLimiarCinza.setBounds(10, 10, 170, 16);
		btnLimiarCinza.setText("Limiarizar (Tons de Cinza)");
		
		btnLimiarCores1 = new Button(composite_1, SWT.CHECK);
		btnLimiarCores1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnLimiarCores1.getSelection()) {
					try {
						BufferedImage limiarCores1 = Filters.limiarizarCores(ImageIO.read(new File(pathImg1)), scaleLimiarCores1.getSelection());
						pathImg3 = "temp/_limiarCores1.bmp";
						ImageIO.write(limiarCores1, "bmp", new File(pathImg3));
						image3 = new Image(null, pathImg3);
						carregaImagem(label3, image3);
					} catch (Exception f) {
						f.printStackTrace();
					}
				}
			}
		});
		btnLimiarCores1.setText("Limiarizar (Cores, 1 Limiar)");
		btnLimiarCores1.setBounds(186, 10, 170, 16);
		
		scaleLimiarCores1 = new Scale(composite_1, SWT.NONE);
		scaleLimiarCores1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnLimiarCores1.getSelection()) {
					try {
						BufferedImage limiarCores1 = Filters.limiarizarCores(ImageIO.read(new File(pathImg1)), scaleLimiarCores1.getSelection());
						pathImg3 = "temp/_limiarCores1.bmp";
						ImageIO.write(limiarCores1, "bmp", new File(pathImg3));
						image3 = new Image(null, pathImg3);
						carregaImagem(label3, image3);
					} catch (Exception f) {
						f.printStackTrace();
					}
				}
			}
		});
		scaleLimiarCores1.setPageIncrement(16);
		scaleLimiarCores1.setMaximum(255);
		scaleLimiarCores1.setSelection(128);
		scaleLimiarCores1.setBounds(186, 48, 170, 42);
		
		CTabItem tbtmNegativa = new CTabItem(tabFolder, SWT.NONE);
		tbtmNegativa.setText("&Negativa");
		
		Composite composite_2 = new Composite(tabFolder, SWT.NONE);
		tbtmNegativa.setControl(composite_2);
		
		Button btnNegativa = new Button(composite_2, SWT.NONE);
		btnNegativa.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					BufferedImage negativa = Filters.negativa(ImageIO.read(new File(pathImg1)),
							btnInverterR.getSelection(), btnInverterG.getSelection(), btnInverterB.getSelection());
					pathImg3 = "temp/_negativa.bmp";
					ImageIO.write(negativa, "bmp", new File(pathImg3));
					image3 = new Image(null, pathImg3);
					carregaImagem(label3, image3);
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		btnNegativa.setBounds(10, 10, 75, 25);
		btnNegativa.setText("Negativa");
		
		Group group = new Group(composite_2, SWT.NONE);
		group.setBounds(91, 10, 118, 105);
		
		btnInverterR = new Button(group, SWT.CHECK);
		btnInverterR.setSelection(true);
		btnInverterR.setBounds(10, 10, 93, 16);
		btnInverterR.setText("Inverter R");
		
		btnInverterG = new Button(group, SWT.CHECK);
		btnInverterG.setSelection(true);
		btnInverterG.setBounds(10, 32, 93, 16);
		btnInverterG.setText("Inverter G");
		
		btnInverterB = new Button(group, SWT.CHECK);
		btnInverterB.setSelection(true);
		btnInverterB.setBounds(10, 54, 93, 16);
		btnInverterB.setText("Inverter B");
		
		CTabItem tbtmRemoverRuido = new CTabItem(tabFolder, SWT.NONE);
		tbtmRemoverRuido.setText("&Remover Ru\u00EDdo");
		
		Composite composite_3 = new Composite(tabFolder, SWT.NONE);
		tbtmRemoverRuido.setControl(composite_3);
		
		Button btnRemoverRuido = new Button(composite_3, SWT.NONE);
		btnRemoverRuido.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				NeighboringMethod nm;
				AveragingMethod am;
				if (btnEstrela.getSelection())
					nm = NeighboringMethod.STAR;
				else if (btnCruz.getSelection())
					nm = NeighboringMethod.CROSS;
				else
					nm = NeighboringMethod.SQUARE;
				
				if (btnMedia.getSelection())
					am = AveragingMethod.MEAN;
				else
					am = AveragingMethod.MEDIAN;
				try {
					BufferedImage removidoRuido = Filters.remocaoRuido(ImageIO.read(new File(pathImg1)), nm, am);
					pathImg3 = "temp/_removidoRuido.bmp";
					ImageIO.write(removidoRuido, "bmp", new File("temp/_removidoRuido.bmp"));
					image3 = new Image(null, "temp/_removidoRuido.bmp");
					carregaImagem(label3, image3);
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		btnRemoverRuido.setBounds(10, 10, 93, 25);
		btnRemoverRuido.setText("Remover Ru\u00EDdo");
		
		Group grpVizinhana = new Group(composite_3, SWT.NONE);
		grpVizinhana.setText("Vizinhan\u00E7a");
		grpVizinhana.setBounds(109, 10, 118, 82);
		
		btnQuadrado = new Button(grpVizinhana, SWT.RADIO);
		btnQuadrado.setSelection(true);
		btnQuadrado.setBounds(10, 56, 98, 16);
		btnQuadrado.setText("Quadrado (3x3)");
		
		btnCruz = new Button(grpVizinhana, SWT.RADIO);
		btnCruz.setText("Cruz (+)");
		btnCruz.setBounds(10, 34, 90, 16);
		
		btnEstrela = new Button(grpVizinhana, SWT.RADIO);
		btnEstrela.setText("Estrela (X)");
		btnEstrela.setBounds(10, 12, 90, 16);
		
		Group grpMtodo = new Group(composite_3, SWT.NONE);
		grpMtodo.setText("M\u00E9todo");
		grpMtodo.setBounds(233, 10, 93, 82);
		
		btnMediana = new Button(grpMtodo, SWT.RADIO);
		btnMediana.setSelection(true);
		btnMediana.setBounds(10, 56, 90, 16);
		btnMediana.setText("Mediana");
		
		btnMedia = new Button(grpMtodo, SWT.RADIO);
		btnMedia.setText("M\u00E9dia");
		btnMedia.setBounds(10, 34, 90, 16);
		
		CTabItem tbtmRedimensionar = new CTabItem(tabFolder, SWT.NONE);
		tbtmRedimensionar.setText("R&edimensionar");
		
		Composite composite_4 = new Composite(tabFolder, SWT.NONE);
		tbtmRedimensionar.setControl(composite_4);
		
		Button btnAmpliar = new Button(composite_4, SWT.NONE);
		btnAmpliar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					BufferedImage ampliar = Filters.ampliar(ImageIO.read(new File(pathImg1)));
					pathImg3 = "temp/_ampliada.bmp";
					ImageIO.write(ampliar, "bmp", new File("temp/_ampliada.bmp"));
					image3 = new Image(null, "temp/_ampliada.bmp");
					carregaImagem(label3, image3);
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		btnAmpliar.setBounds(10, 10, 124, 25);
		btnAmpliar.setText("Ampliar");
		
		Button btnReduzir = new Button(composite_4, SWT.NONE);
		btnReduzir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					BufferedImage reduzir = Filters.reduzir(ImageIO.read(new File(pathImg1)));
					pathImg3 = "temp/_reduzida.bmp";
					ImageIO.write(reduzir, "bmp", new File("temp/_reduzida.bmp"));
					image3 = new Image(null, "temp/_reduzida.bmp");
					carregaImagem(label3, image3);
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		btnReduzir.setBounds(168, 10, 124, 25);
		btnReduzir.setText("Reduzir");
		
		CTabItem tbtmRotacionar = new CTabItem(tabFolder, SWT.NONE);
		tbtmRotacionar.setText("R&otacionar");
		
		Composite composite_5 = new Composite(tabFolder, SWT.NONE);
		tbtmRotacionar.setControl(composite_5);
		
		Button btnRotacionar = new Button(composite_5, SWT.NONE);
		btnRotacionar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int graus = 180;
				if (btn90.getSelection())
					graus = 90;
				else if (btn180.getSelection())
					graus = 180;
				else
					graus = 270;
				try {
					BufferedImage rotacionada = Filters.rotacionar(ImageIO.read(new File(pathImg1)), graus);
					pathImg3 = "temp/_rotacionada.bmp";
					ImageIO.write(rotacionada, "bmp", new File("temp/_rotacionada.bmp"));
					image3 = new Image(null, "temp/_rotacionada.bmp");
					carregaImagem(label3, image3);
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		btnRotacionar.setBounds(10, 10, 124, 25);
		btnRotacionar.setText("Rotacionar");
		
		Group grpRotacao = new Group(composite_5, SWT.NONE);
		grpRotacao.setText("Rotação");
		grpRotacao.setBounds(149, 10, 118, 82);
		
		btn90 = new Button(grpRotacao, SWT.RADIO);
		btn90.setBounds(10, 12, 98, 16);
		btn90.setText("90°");
		
		btn180 = new Button(grpRotacao, SWT.RADIO);
		btn180.setSelection(true);
		btn180.setText("180°");
		btn180.setBounds(10, 34, 90, 16);
		
		btn270 = new Button(grpRotacao, SWT.RADIO);
		btn270.setText("270°");
		btn270.setBounds(10, 56, 90, 16);
		
		CTabItem tbtmAdicao = new CTabItem(tabFolder, SWT.NONE);
		tbtmAdicao.setText("&Adição/Subtração");
		
		Composite composite_6 = new Composite(tabFolder, SWT.NONE);
		tbtmAdicao.setControl(composite_6);
		
		Button btnAdicao = new Button(composite_6, SWT.NONE);
		btnAdicao.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					BufferedImage adicionada = Filters.adicionar(ImageIO.read(new File(pathImg1)),
							spinnerA1.getSelection(),
							ImageIO.read(new File(pathImg2)),
							spinnerA2.getSelection());
					pathImg3 = "temp/_adicionada.bmp";
					ImageIO.write(adicionada, "bmp", new File("temp/_adicionada.bmp"));
					image3 = new Image(null, "temp/_adicionada.bmp");
					carregaImagem(label3, image3);
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		btnAdicao.setText("Adição");
		btnAdicao.setBounds(35, 10, 124, 25);
		
		Button btnSubtracao = new Button(composite_6, SWT.NONE);
		btnSubtracao.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					BufferedImage subtraida = Filters.subtrair(ImageIO.read(new File(pathImg1)),
							ImageIO.read(new File(pathImg2)));
					pathImg3 = "temp/_subtraida.bmp";
					ImageIO.write(subtraida, "bmp", new File("temp/_subtraida.bmp"));
					image3 = new Image(null, "temp/_subtraida.bmp");
					carregaImagem(label3, image3);
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		btnSubtracao.setText("Subtração");
		btnSubtracao.setBounds(235, 10, 124, 25);
		
		Group grpProporcao = new Group(composite_6, SWT.NONE);
		grpProporcao.setText("Proporção");
		grpProporcao.setBounds(10, 41, 219, 51);
		
		Label labelA1 = new Label(grpProporcao, SWT.NONE);
		labelA1.setText("1:");
		labelA1.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		labelA1.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		labelA1.setBounds(10, 19, 19, 21);
		
		Label labelA2 = new Label(grpProporcao, SWT.NONE);
		labelA2.setText("2:");
		labelA2.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		labelA2.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		labelA2.setBounds(107, 19, 19, 21);
		
		spinnerA1 = new Spinner(grpProporcao, SWT.BORDER);
		spinnerA1.setIncrement(5);
		spinnerA1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				spinnerA2.setSelection(100 - spinnerA1.getSelection());
			}
		});
		spinnerA1.setSelection(50);
		spinnerA1.setBounds(35, 19, 47, 22);
		
		spinnerA2 = new Spinner(grpProporcao, SWT.BORDER);
		spinnerA2.setIncrement(5);
		spinnerA2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				spinnerA1.setSelection(100 - spinnerA2.getSelection());
			}
		});
		spinnerA2.setSelection(50);
		spinnerA2.setBounds(132, 18, 47, 22);
		
		CTabItem tbtmEqualizar = new CTabItem(tabFolder, SWT.NONE);
		tbtmEqualizar.setText("E&qualizar");
		
		Composite composite_7 = new Composite(tabFolder, SWT.NONE);
		tbtmEqualizar.setControl(composite_7);
		
		Group groupEq = new Group(composite_7, SWT.NONE);
		groupEq.setBounds(140, 10, 118, 82);
		
		btnEqualizarR = new Button(groupEq, SWT.CHECK);
		btnEqualizarR.setText("Equalizar R");
		btnEqualizarR.setSelection(true);
		btnEqualizarR.setBounds(10, 10, 93, 16);
		
		btnEqualizarG = new Button(groupEq, SWT.CHECK);
		btnEqualizarG.setText("Equalizar G");
		btnEqualizarG.setSelection(true);
		btnEqualizarG.setBounds(10, 32, 93, 16);
		
		btnEqualizarB = new Button(groupEq, SWT.CHECK);
		btnEqualizarB.setText("Equalizar B");
		btnEqualizarB.setSelection(true);
		btnEqualizarB.setBounds(10, 54, 93, 16);
		
		Button btnEqualizacao = new Button(composite_7, SWT.NONE);
		btnEqualizacao.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					BufferedImage equalizada = Filters.equalizar(ImageIO.read(new File(pathImg1)),
							btnEqualizarR.getSelection(),
							btnEqualizarG.getSelection(),
							btnEqualizarB.getSelection());
					pathImg3 = "temp/_equalizada.bmp";
					ImageIO.write(equalizada, "bmp", new File("temp/_equalizada.bmp"));
					image3 = new Image(null, "temp/_equalizada.bmp");
					carregaImagem(label3, image3);
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		btnEqualizacao.setText("Equalização");
		btnEqualizacao.setBounds(10, 10, 124, 25);
		
		CTabItem tbtmDilatar = new CTabItem(tabFolder, SWT.NONE);
		tbtmDilatar.setText("&Dilatação/Erosão");
		
		Composite composite_8 = new Composite(tabFolder, SWT.NONE);
		tbtmDilatar.setControl(composite_8);
		
		Button btnDilatacao = new Button(composite_8, SWT.NONE);
		btnDilatacao.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					int corValida = 0;
					int[][] struct;
					if(btn3x3.getSelection())
						struct = Filters.structuringElement(StructuringElement.CIRCLE3);
					else if(btn5x5.getSelection())
						struct = Filters.structuringElement(StructuringElement.CIRCLE5);
					else
						struct = Filters.structuringElement(StructuringElement.CIRCLE7);
					if(btnPreto.getSelection())
						corValida = 0;
					else
						corValida = 255;
					BufferedImage dilatada = Filters.dilatar(ImageIO.read(new File(pathImg1)), struct, corValida);
					pathImg3 = "temp/_dilatada.bmp";
					ImageIO.write(dilatada, "bmp", new File("temp/_dilatada.bmp"));
					image3 = new Image(null, "temp/_dilatada.bmp");
					carregaImagem(label3, image3);
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		btnDilatacao.setText("Dilatação");
		btnDilatacao.setBounds(10, 10, 124, 25);
		
		Group grpEstruturante = new Group(composite_8, SWT.NONE);
		grpEstruturante.setText("Estruturante");
		grpEstruturante.setBounds(140, 10, 124, 82);
		
		btn7x7 = new Button(grpEstruturante, SWT.RADIO);
		btn7x7.setBounds(10, 56, 90, 16);
		btn7x7.setText("7x7");
		
		btn5x5 = new Button(grpEstruturante, SWT.RADIO);
		btn5x5.setText("5x5");
		btn5x5.setBounds(10, 34, 90, 16);
		
		btn3x3 = new Button(grpEstruturante, SWT.RADIO);
		btn3x3.setSelection(true);
		btn3x3.setBounds(10, 12, 90, 16);
		btn3x3.setText("3x3");
		
		Group grpPixelValido = new Group(composite_8, SWT.NONE);
		grpPixelValido.setText("Pixel Válido");
		grpPixelValido.setBounds(270, 10, 96, 82);
		
		btnPreto = new Button(grpPixelValido, SWT.RADIO);
		btnPreto.setSelection(true);
		btnPreto.setBounds(10, 25, 90, 16);
		btnPreto.setText("Preto");
		
		btnBranco = new Button(grpPixelValido, SWT.RADIO);
		btnBranco.setText("Branco");
		btnBranco.setBounds(10, 47, 90, 16);
		
		Button btnErosao = new Button(composite_8, SWT.NONE);
		btnErosao.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {	
					int corValida = 0;
					int[][] struct;
					if(btn3x3.getSelection())
						struct = Filters.structuringElement(StructuringElement.CIRCLE3);
					else if(btn5x5.getSelection())
						struct = Filters.structuringElement(StructuringElement.CIRCLE5);
					else
						struct = Filters.structuringElement(StructuringElement.CIRCLE7);
					if(btnPreto.getSelection())
						corValida = 0;
					else
						corValida = 255;
					BufferedImage erodida = Filters.erodir(ImageIO.read(new File(pathImg1)), struct, corValida);
					pathImg3 = "temp/_erodida.bmp";
					ImageIO.write(erodida, "bmp", new File(pathImg3));
					image3 = new Image(null, pathImg3);
					carregaImagem(label3, image3);
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		btnErosao.setText("Erosão");
		btnErosao.setBounds(372, 10, 124, 25);
		
		CTabItem tbtmsegmentacao = new CTabItem(tabFolder, SWT.NONE);
		tbtmsegmentacao.setText("&Segmentação");
		
		Composite composite_9 = new Composite(tabFolder, SWT.NONE);
		tbtmsegmentacao.setControl(composite_9);
		
		Button btnSegmentacao = new Button(composite_9, SWT.NONE);
		btnSegmentacao.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					BufferedImage segmentada = Filters.segmentar(ImageIO.read(new File(pathImg1)), spinnerSegmentos.getSelection());
					pathImg3 = "temp/_segmentada.bmp";
					ImageIO.write(segmentada, "bmp", new File(pathImg3));
					image3 = new Image(null, pathImg3);
					carregaImagem(label3, image3);
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		btnSegmentacao.setText("Segmentação");
		btnSegmentacao.setBounds(10, 10, 124, 25);
		
		spinnerSegmentos = new Spinner(composite_9, SWT.BORDER);
		spinnerSegmentos.setMaximum(20);
		spinnerSegmentos.setPageIncrement(5);
		spinnerSegmentos.setSelection(5);
		spinnerSegmentos.setBounds(48, 68, 47, 22);
		
		Label lblRegioes = new Label(composite_9, SWT.NONE);
		lblRegioes.setText("Regiões");
		lblRegioes.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		lblRegioes.setAlignment(SWT.CENTER);
		lblRegioes.setBounds(10, 41, 124, 22);
		
		Button btnSegmentacaoCV = new Button(composite_9, SWT.NONE);
		btnSegmentacaoCV.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int tipo = 0;
				if(btnTruncada.getSelection())
					tipo = Imgproc.THRESH_TRUNC;
				if(btnAte0.getSelection())
					tipo = Imgproc.THRESH_TOZERO;
				if(btnAte0Invertida.getSelection())
					tipo = Imgproc.THRESH_TOZERO_INV;
				if(btnBinaria.getSelection())
					tipo = Imgproc.THRESH_BINARY;
				if(btnBinariaInvertida.getSelection())
					tipo = Imgproc.THRESH_BINARY_INV;
				Mat origem = Imgcodecs.imread(pathImg1,  Imgcodecs.CV_LOAD_IMAGE_COLOR);
				Mat segmentada = new Mat(origem.rows(), origem.cols(), origem.type());
				segmentada = origem;
				Imgproc.threshold(origem, segmentada, 127, 255, tipo);
				pathImg3 = "temp/_segmentadaCV.bmp";
				Imgcodecs.imwrite(pathImg3, segmentada);
								
				image3 = new Image(null, pathImg3);
				carregaImagem(label3, image3);
			}
		});
		btnSegmentacaoCV.setText("Segmentação OpenCV");
		btnSegmentacaoCV.setBounds(140, 10, 131, 25);
		
		Group grpTipoDeSegmentao = new Group(composite_9, SWT.NONE);
		grpTipoDeSegmentao.setText("Tipo de Segmentação");
		grpTipoDeSegmentao.setBounds(380, 10, 228, 105);
		
		btnBinaria = new Button(grpTipoDeSegmentao, SWT.RADIO);
		btnBinaria.setSelection(true);
		btnBinaria.setBounds(10, 79, 90, 16);
		btnBinaria.setText("Binária");
		
		btnBinariaInvertida = new Button(grpTipoDeSegmentao, SWT.RADIO);
		btnBinariaInvertida.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnBinariaInvertida.setText("Binária Invertida");
		btnBinariaInvertida.setBounds(106, 79, 112, 16);
		
		btnAte0 = new Button(grpTipoDeSegmentao, SWT.RADIO);
		btnAte0.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnAte0.setText("Até 0");
		btnAte0.setBounds(10, 57, 90, 16);
		
		btnAte0Invertida = new Button(grpTipoDeSegmentao, SWT.RADIO);
		btnAte0Invertida.setText("Até 0 Invertida");
		btnAte0Invertida.setBounds(106, 57, 97, 16);
		
		btnTruncada = new Button(grpTipoDeSegmentao, SWT.RADIO);
		btnTruncada.setText("Truncada");
		btnTruncada.setBounds(10, 35, 90, 16);
		
		createContents();
		criaFileDialog();
	}

	private void abreImagem(int indice) {
		if(indice==1) {
			if(pathImg1!=null && !pathImg1.equals("")) {
				image1 = new Image(null, pathImg1);
				carregaImagem(label1, image1);
			}
		} else {
			if(pathImg2!=null && !pathImg2.equals("")) {
				image2 = new Image(null, pathImg2);
				carregaImagem(label2, image2);
			}
		}
	}
	
	private void carregaImagem(CLabel label, Image img) {
		label.setBackground(img);
		label.setBounds(
				label.getBounds().x,
				label.getBounds().y,
				img.getImageData().width,
				img.getImageData().height
				);
	}
	
	private void criaFileDialog(){
		fileDialog = new FileDialog(this, SWT.OPEN);
		fileDialog.setText("Abrir");
		fileDialog.setFilterPath("imgs");
		String[] filterExt = {"*.*", "*.png", "*.jpg", "*.bmp"};
		fileDialog.setFilterExtensions(filterExt);
	}
	
	private void encontraPixel(int x, int y, int indice) {
		ImageData imageData = null;
		PaletteData palette = null;
		if(indice == 1 && image1!= null)
			imageData = image1.getImageData();
		if(indice == 2 && image2!= null)
			imageData = image2.getImageData();
		if(indice == 3 && image3!= null)
			imageData = image3.getImageData();
		palette = imageData.palette;
		
		if(palette != null && x > -1 && y > -1 && x < imageData.width && y < imageData.height) {
			int pixel = imageData.getPixel(x, y);
			RGB rgb = palette.getRGB(pixel);
			labelR.setText("R: "+rgb.red);
			labelG.setText("G: "+rgb.green);
			labelB.setText("B: "+rgb.blue);
		}
		
	}
	
	private void mensagem(String texto) {
		MessageBox mb = new MessageBox(getShell(), SWT.ICON_WARNING);
		mb.setMessage(texto);
		mb.setText("Aviso");
		mb.open();
	}
	
	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(1248, 800);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
