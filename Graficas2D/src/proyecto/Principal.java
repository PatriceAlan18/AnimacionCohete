package proyecto;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

public class Principal extends JFrame{

	private BufferedImage buffer, pantalla, cohete, bufferAux, meteoro, planeta, musica, planetasB; //Cohete sera un buffer aparte con pixeles no ocupados como invisibles
	private Graphics graPantalla, gAux, gP;
	private final static Color NEGRO = new Color(0,0,1);
	private int dx, dy, sx,sy;
	private int[][] translacion = {{1,0,dx},{0,1,dy},{0,0,1}};
	private double[][] escalacion = {{sx,0,0},{0,sy,0},{0,0,1}};
	private double[][] rotacion = {{-1,-1,0},{-1,-1,0},{0,0,1}};
	private Color[] espacio = {NEGRO,NEGRO,NEGRO,NEGRO,new Color(24,28,34), new Color(15,10,10) };
	private Estrella[] estrellas, tilines;
	private Cohete miCohete;
	private Musica Notas = new Musica();
	private Clip sonido;
	private Meteorito meteorito;
	private Animacion musicaHilo;
	private Planeta planetas;
	private int xCohete, yCohete;
	public static int Width, Height;
	
	public Principal() {
		this.setTitle("Cohete");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setUndecorated(true);
		this.setVisible(true);
		this.setExtendedState(MAXIMIZED_BOTH);
		Principal.Width = getWidth();
		Principal.Height = getHeight();
		pantalla = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		graPantalla = (Graphics2D)pantalla.createGraphics();
		buffer = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		bufferAux = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		gAux = (Graphics2D) bufferAux.createGraphics();
		planetasB = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
		gP = (Graphics2D) planetasB.createGraphics();
		inicializarGraficos();
	}
	
	//Evitar desbordamiento
		//  -Xss128m
	public static void main(String[] args) {
		Principal ventana = new Principal();
		//ventana.rectangulo(100, 100, 150, 150, NEGRO);
		ventana.dibujarCohete();
		ventana.dibujarFondo();
		ventana.dibujarEstrellas();
		ventana.dibujarPlaneta();
		ventana.crearEstrellasHilo();
		ventana.crearHiloMusica();
		ventana.reproducirMusica();
		ventana.crearHilo();

	}
	
	public void dibujarPlaneta() {
		planetas = new Planeta(getRandom(10, 12));
		planeta = planetas.getPlaneta();
		new PlanetasHilo().start();
	}
	
	public void crearHilo() {
		Actualizador hilo = new Actualizador();
		hilo.start();
	}
	
	public void crearHiloMusica() {
		musicaHilo = new Animacion();
		musicaHilo.start();
	}
	
	public void dibujarCohete() {
		xCohete = getWidth()/2-100;
		yCohete = getHeight()/2-50;
		miCohete = new Cohete();
		cohete = miCohete.getCohete();
		meteorito = new Meteorito();
		meteoro = meteorito.getMeteoro();
	}
	
	public void dibujarFondo() {
		rectangulo(0, 0, getWidth()-1, getHeight()-1, NEGRO);
		Color fondo = new Color(pantalla.getRGB(100, 100));		
		pintar(100,100,fondo, espacio);
	}
	
	public void dibujarEstrellas() {
		estrellas = new Estrella[getRandom(400, 550)];
		for(int i = 0; i<estrellas.length; i++) {
			estrellas[i] = new Estrella(getRandom(5, this.getWidth()-5),getRandom(5, this.getHeight()-5));
			estrellas[i].dibujarEstrella(graPantalla);
		}
	}
	
	
	public void pintar(int x, int y, Color c, Color[] fondo) {
		int inicioX = x, inicioY = y;
		int finX = inicioX, finY = inicioY;
		while(new Color(pantalla.getRGB(finX+1, inicioY)).getRGB()==c.getRGB()) finX++;
		while(new Color(pantalla.getRGB(inicioX-1, inicioY)).getRGB()==c.getRGB())inicioX--;
		dibujarLineaHorizontal(inicioX,finX,finY,fondo);
		if(new Color(pantalla.getRGB(x, y+1)).getRGB()==c.getRGB())pintar(x, y+1, c, fondo);
		if(new Color(pantalla.getRGB(x, y-1)).getRGB()==c.getRGB())pintar(x, y-1, c, fondo);
	}
	
	public void escalar(double sx, double sy) {
		sx = sx/100;
		sy = sy/100;
		escalacion[0][0] = sx;
		escalacion[1][1] = sy;
		int [] puntos = {100,100,1};
		verMatriz(multiplicarMatrices(puntos,escalacion));
	}
	
	public void rotar(int angulo) {
		double radianes = Math.toRadians(angulo);
		double seno = Math.sin(radianes);
		double coseno = Math.cos(radianes);
		
		rotacion[0][0] = coseno;
		rotacion[0][1] = seno*(-1);
		rotacion[1][0] = seno;
		rotacion[1][1] = coseno;
		int [] puntos = {200,200,1};

		verMatriz(multiplicarMatrices(puntos,rotacion));
	}
	
	public void transladar(int dx, int dy) {
		translacion[0][2] = dx;
		translacion[1][2] = dy;
		int [] puntos = {100,100,1};
		verMatriz(multiplicarMatrices(puntos,translacion));
		
	}
	
	public void verMatriz(int[] matriz) {
		for(int i = 0; i<matriz.length; i++) {
			System.out.print(matriz[i]+"  ");

		}
	}
	
	public void verMatriz(int[][] matriz) {
		for(int i = 0; i<matriz.length; i++) {
			for(int j = 0; j<matriz[i].length; j++) {
				System.out.print(matriz[i][j]+"  ");
			}
			System.out.print("\n");
		}
	}
	
	public void verMatriz(double[][] matriz) {
		for(int i = 0; i<matriz.length; i++) {
			for(int j = 0; j<matriz[i].length; j++) {
				System.out.print(matriz[i][j]+"  ");
			}
			System.out.print("\n");
		}
	}
	
	public int[] multiplicarMatrices(int[] matriz1,int[][] matriz2) {
		int[] nueva = new int[matriz1.length];
		for(int i = 0; i<matriz1.length; i++) {
			for(int j = 0; j<matriz2[i].length; j++) {
				nueva[i]=nueva[i]+matriz1[j]*matriz2[i][j];
			}
		}
		return nueva;
	}
	
	public int[] multiplicarMatrices(int[] matriz1,double[][] matriz2) {
		int[] nueva = new int[matriz1.length];
		for(int i = 0; i<matriz1.length; i++) {
			for(int j = 0; j<matriz2[i].length; j++) {
				nueva[i]=(int) (nueva[i]+matriz1[j]*matriz2[i][j]);
			}
		}
		return nueva;
	}
	
	public void rectangulo(int x0, int y0, int x1, int y1, Color c) {
		dibujarLineaVertical(x0,y0,y1,c);
		dibujarLineaHorizontal(x0,x1,y1,c);
		dibujarLineaVertical(x1,y0,y1,c);
		dibujarLineaHorizontal(x0,x1,y0,c);
	}
	
	public void dibujarLinea(int x0, int y0, int x1, int y1, Color c) {
		int dy = y1-y0;
		int dx = x1-x0;
		if(dx==0) {
			dibujarLineaVertical(x0,y0,y1,c);
			return;
		}
		
		if(dy==0) {
			dibujarLineaHorizontal(x0,x1,y0,c);
			return;
		}
		
		int incYi = 1, incYr, incXr, incXi=1, k;
		if(dy<=0) {
			dy= -dy;
			incYi=-1;
		}
		if(dx<0) {
			dx= -dx;
			incXi=-1;
		}
		if(dx>=dy) {
			incYr = 0;
			incXr = incXi;
		}
		else {
			incXr=0;
			incYr=incYi;
			
			k = dx;
			dx = dy;
			dy = k;
		}
		int x=x0;
		int y=y0;
		int avR = (2*dy);
		int av = (avR - dx);
		int avI = (av-dx);
		do {
				ponerPixel(x, y, c);
			if(av >=0) {
				x = (x+incXi);
				y = (y+incYi);
				av = (av+avI);
			}
			else {
				x = (x+incXr);
				y = (y+incYr);
				av = (av + avR);
			}
		}while(x!=x1);
		ponerPixel(x, y, c);

	}
	
	
	
	private void dibujarLineaVertical(int x, int y, int yF, Color c[]) {
		if(yF<y) {
			int aux = y;
			y=yF;
			yF=aux;
		}
		for(int i = 0; i <= Math.abs(yF-y); i++)ponerPixel(x, y+i, c[getRandom(0, c.length-1)]);
	}
	
	private void dibujarLineaHorizontal(int x, int xF, int y, Color c[]) {
		if(xF<x) {
			int aux = x;
			x=xF;
			xF=aux;
		}
		for(int i = 0; i <= Math.abs(xF-x); i++)  ponerPixel(x+i, y, c[getRandom(0, c.length-1)]);			
	}

	private void dibujarLineaVertical(int x, int y, int yF, Color c) {
		if(yF<y) {
			int aux = y;
			y=yF;
			yF=aux;
		}
		for(int i = 0; i <= Math.abs(yF-y); i++)ponerPixel(x, y+i, c);
	}
	
	private void dibujarLineaHorizontal(int x, int xF, int y, Color c) {
		if(xF<x) {
			int aux = x;
			x=xF;
			xF=aux;
		}
		for(int i = 0; i <= Math.abs(xF-x); i++)  ponerPixel(x+i, y, c);			
	}
	
	//Siempre inicia desde el fondo a la derecha
	public void dibujarCircunferencia(int x0, int y0, int R,int gradosInicio, int gradosFin, Color c) {
		int x=0, y=0;
		double aumento = (double)40/(double)R;
		for(double t = gradosInicio; t <gradosFin; t+=aumento) {
			x = (int) Math.round(x0+(R*Math.sin(Math.toRadians(t))));
			y = (int) Math.round(y0+(R*Math.cos(Math.toRadians(t))));
			ponerPixel(x, y, c);
		}		
	}
	
	public void finalizar() {
	    gAux.drawImage(pantalla, 0, 0, null);
	    gAux.drawImage(planetasB, 0, 0, this);
	    meteoro = meteorito.getMeteoro();
		gAux.drawImage(planeta, 0, 0,null);

	    gAux.drawImage(meteoro, 0, 0, this);

		gAux.drawImage(musica, getWidth()/3, 0, null);

	    gAux.drawImage(cohete, xCohete,yCohete, this);

	    this.getGraphics().drawImage(bufferAux, 0, 0, null);
	}
	
	public static void inicializarGraficos() {
		try {
		    Thread.sleep(40);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	}
	
	public void ponerPixel(int x, int y, Color c) {
		buffer.setRGB(0, 0, c.getRGB());
		graPantalla.drawImage(buffer, x, y,this);

	}
	
	public int getRandom(int min, int max) {
	    return (int) (Math.random() * (max - min + 1) + min);
	}
	
	public void titileo() {
		
		tilines = new Estrella[getRandom(20, estrellas.length-1)];
		for(int i = 0; i<tilines.length; i++) {
			tilines[i] = estrellas[getRandom(0, estrellas.length-1)];
			tilines[i].titilar(graPantalla);
		}
	}
	
	public void pararTitileo() {
		
		for(int i = 0; i<tilines.length; i++) {
			tilines[i].dejarTitilar(graPantalla);
		}
	}

	public void crearEstrellasHilo() {
		EstrellasHilo miHilo = new EstrellasHilo();
		miHilo.start();
	}
	
	public void animarCohete() {
		cohete = miCohete.getCohete();
	}
	
	public void animarCohete2() {
		cohete = miCohete.getCohete2();

	}
	
	public void reproducirMusica() {
		try {
			sonido = AudioSystem.getClip();
			InputStream inputStream = getClass().getResourceAsStream("/proyecto/fondo2.wav");
			File tempFile = File.createTempFile("fondo2", ".wav");
			tempFile.deleteOnExit();
			Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(tempFile);
			sonido.open(audioInputStream);
			sonido.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		iniciarMusica();
	}
	
	
	public void detenerMusica() {
		sonido.stop();
	}
	
	public void iniciarMusica() {
		sonido.start();
	}
	
	private class PlanetasHilo extends Thread{
		public void run() {
			while(true) {
				try {
				    sleep(400);
				    planeta = planetas.getPlaneta();

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private class EstrellasHilo extends Thread{
		public void run() {
			while(true) {
				try {
					sleep(400);
					titileo();
					animarCohete();
					xCohete++;
					sleep(400);
					pararTitileo();
					animarCohete2();
					xCohete--;

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private class Animacion extends Thread{
		public void run() {
			while(true) {
				try {
					sleep(100);
					musica = Notas.getMusica();
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}
		}
	
	private class Actualizador extends Thread{
		public void run() {
			while(true) {
				try {
					finalizar();
					sleep(10);
					finalizar();
					sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	
	
}
