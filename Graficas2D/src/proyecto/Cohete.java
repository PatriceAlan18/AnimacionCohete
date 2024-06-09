package proyecto;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Cohete {

	private BufferedImage buffer, cohete, cohete2;
	private Fuego fuego;
	private Graphics g, g2;
	private Ventana ventana;
	private final Color TRAJE = new Color(184,186,188);
	private Color rojo = new Color(234,70,70,255);
	private Color gris = new Color(203,203,203,255);
	private Color grisD = new Color(146,146,146);
	private final Color FUEGO = new Color(239,164,35,210);
	private final Color NEGRO = new Color(0,0,1);
	private final Color CAFE = new Color(117,91,91);
	private final Color MANGAS = new Color(99,112,231);
	private final Color BLANCO = new Color(255,255,255);
	
	public Cohete( ) {
		buffer = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		cohete = new BufferedImage(260,110,BufferedImage.TYPE_INT_ARGB);
		cohete2 = new BufferedImage(260, 110, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D) cohete2.createGraphics();
		g = (Graphics2D) cohete.createGraphics();
		ventana = new Ventana(10,3);
		fuego = new Fuego();
		dibujarCohete();
	}
	
	public void ponerPixel(Graphics g, int x, int y, Color c) {
		buffer.setRGB(0, 0, c.getRGB());
		g.drawImage(buffer, x, y,null);

	}
	
	private void dibujarCohete() {
		//Cuerpo
		dibujarLinea(40, 30, 180, 40, grisD);
		dibujarLinea(40, 80, 180, 70, grisD);
		dibujarLineaVertical(40,30,80,gris);
		dibujarCircunferencia(30,55,50,0,65,NEGRO);
		dibujarCircunferencia(30,55,50,115,180,NEGRO);
		dibujarLineaHorizontal(30,20,5,NEGRO);
		dibujarLineaHorizontal(30,20,105,NEGRO);
		dibujarCircunferencia(25,55,40,30,55,NEGRO);
		dibujarCircunferencia(25,55,40,125,150,NEGRO);
		dibujarLinea(20,5,45,20,NEGRO);
		dibujarLinea(20,105,45,90,NEGRO);
		
		//Frente
		dibujarLineaVertical(180,40,70,rojo);
		dibujarCircunferencia(180, 70, 30, 145, 180, NEGRO);
		dibujarCircunferencia(180, 40, 30, 0, 35, NEGRO);
		dibujarLinea(198,46,210,55,NEGRO);
		dibujarLinea(198,64,210,55,NEGRO);
		pintarInundacion(41,55,4, new Color(cohete.getRGB(41, 55)),gris);
		pintarInundacion(181,55,4,new Color(cohete.getRGB(181, 55)),rojo);
		pintarInundacion(60,30,4,new Color(cohete.getRGB(60, 30)),rojo);
		pintarInundacion(60,79,4,new Color(cohete.getRGB(60, 82)),rojo);
		detalles();
		dibujarAstronauta();
		g2.drawImage(cohete, 0, 0,null);
		g.drawImage(fuego.getFuego(), 0, 40, null);
		g2.drawImage(fuego.getFuego2(), 0, 40, null);
	}
	
	public void detalles() {
		
		dibujarCircunferencia(0,55, 60, 67,113, grisD);
		dibujarCircunferencia(40,55, 60, 70,110, grisD);
		dibujarCircunferencia(100,55, 60, 74,106, grisD);
		//pintarInundacion(120, 55, 0, NEGRO, FUEGO);
		g.drawImage(ventana.getVentana(), 140, 43,null);
		dibujarCircunferencia(152, 62, 10, 145,195,  new Color(235,235,235));
		dibujarCircunferencia(70, 43,2,0,360,NEGRO);
		rectangulo(80, 42, 100, 67, NEGRO);
		dibujarCircunferencia(90, 52, 10, 90, 270, NEGRO);
		dibujarCircunferencia(90, 57, 10, 270, 450, NEGRO);
		pintarInundacionDiferente(82, 44, 0, NEGRO, NEGRO);
		pintarInundacionDiferente(98, 44, 0, NEGRO, NEGRO);
		pintarInundacionDiferente(82, 65, 0, NEGRO, NEGRO);
		pintarInundacionDiferente(98, 65, 0, NEGRO, NEGRO);
		dibujarLinea(92, 60, 96, 60, NEGRO);
		ventana = new Ventana(6, 1);
		g.drawImage(ventana.getVentana(), 80,41, null);
		
		//Cuerda astronauta
		
		ponerPixel(70, 43, BLANCO);		
		ponerPixel(71, 43, BLANCO);
		ponerPixel(72, 43, BLANCO);
		ponerPixel(72, 42, BLANCO);
		ponerPixel(72, 41, BLANCO);
		ponerPixel(73, 41, BLANCO);
		dibujarLinea(74, 41, 77, 21, BLANCO);
		
		dibujarCircunferencia(81, 24, 5, 150, 270, new Color(245,245,245));

		dibujarLinea(82, 18, 89, 20, BLANCO);
		dibujarLinea(89, 20, 96, 18, BLANCO);

		
	}
	
	public void dibujarAstronauta() {
		//Casco
		 
		rectangulo(100, 10, 106, 15, NEGRO);
		pintarInundacion(101, 11, 3, new Color(cohete.getRGB(101, 11)), new Color(68,68,68));
		dibujarLineaHorizontal(101, 105, 16, NEGRO);
		dibujarLineaHorizontal(101, 105, 9, NEGRO);

		//Abajo
		ponerPixel(100, 16, TRAJE);
		ponerPixel(106, 16, TRAJE);
		ponerPixel(107, 16, TRAJE);
		
		//Arriba
		ponerPixel(100, 9, TRAJE);
		dibujarLineaHorizontal(100, 107,8, TRAJE);
		dibujarLineaHorizontal(101, 106,7, TRAJE);
		ponerPixel(106, 9, TRAJE);
		ponerPixel(107, 9, TRAJE);
		
		//Detras
		dibujarLineaVertical(99, 8, 16, TRAJE);
		dibujarLineaVertical(98, 9, 15, TRAJE);
		dibujarLineaVertical(97, 10, 16, TRAJE);
		
		//Cuerpo
		rectangulo(98, 16, 104, 25, TRAJE);
		pintarInundacion(99,17,0,new Color(cohete.getRGB(99, 17)),TRAJE);
		dibujarLineaHorizontal(99, 105,16, new Color(117,117,117));
		dibujarLineaVertical(97, 18, 22, MANGAS);
		
		//Piernas?
		rectangulo(99, 25, 111, 28, TRAJE);
		pintarInundacion(100,27,0,new Color(cohete.getRGB(100, 27)),TRAJE);
		
		rectangulo(108, 28, 112,31,TRAJE);
		pintarInundacion(109,29,0,new Color(cohete.getRGB(109, 29)),TRAJE);
		dibujarLineaVertical(111,32, 35, MANGAS);
		dibujarLineaVertical(110,32, 35, MANGAS);
		dibujarLineaVertical(109,32, 35, MANGAS);
		dibujarLineaVertical(108,32, 35, MANGAS);

		//Caja
		rectangulo(98, 28, 107, 34, new Color(124,154,185));
		pintarInundacion(99, 29, 10, new Color(cohete.getRGB(99, 29)),  new Color(155,201,247));
		
		//Banjo
		rectangulo(106, 20, 109, 23, CAFE);
		pintarInundacion(107, 21, 0, new Color(cohete.getRGB(107, 21)),  new Color(137,121,121));
		dibujarLineaVertical(105, 21, 22, CAFE);
		dibujarLineaVertical(110, 21, 22, CAFE);
		dibujarLineaHorizontal(107, 108, 19, CAFE);
		dibujarLineaHorizontal(107, 108, 24, CAFE);
		dibujarLinea(108, 19, 114, 13, CAFE);
		dibujarLinea(109, 19, 115, 13, CAFE);

		//Mangas
		
		dibujarLineaVertical(103, 18,24, MANGAS);
		dibujarLineaVertical(102, 18,24, MANGAS);
		dibujarLineaVertical(101, 18,23, MANGAS);
		
		dibujarLinea(104, 24, 108, 23, MANGAS);
		dibujarLinea(104, 23, 108, 22, MANGAS);
		dibujarLinea(104, 22, 108, 21, MANGAS);

		dibujarLineaHorizontal(105, 110,16, MANGAS);
		dibujarLineaHorizontal(105, 109,17, MANGAS);

//		ponerPixel(105, 19, MANGAS);

		
	}
	
	public Color degradarColor(Color c, int d){
		int red = c.getRed()-d;
	    int green = c.getGreen()-d;
	    int blue = c.getBlue()-d;
	   return new Color(Math.max(0, red), Math.max(0, green), Math.max(0, blue),255);
	}
	
	public void pintarInundacion(int x, int y, int d, Color c, Color fondo) {
	    ponerPixel(x, y, fondo);
	    if (new Color(cohete.getRGB(x+1,y)).getRGB() == c.getRGB())pintarInundacion(x+1, y, d, c, fondo);
	    if (new Color(cohete.getRGB(x-1,y)).getRGB() == c.getRGB()) pintarInundacion(x-1, y, d, c, fondo);
	    if (new Color(cohete.getRGB(x,y+1)).getRGB() == c.getRGB()) pintarInundacion(x, y + 1, d, c, degradarColor(fondo, d));
	    if (new Color(cohete.getRGB(x, y - 1)).getRGB() == c.getRGB())  pintarInundacion(x,y-1,d, c, degradarColor(fondo, d));
	}
	
	public void pintarInundacionDiferente(int x, int y, int d, Color c, Color fondo) {
	    ponerPixel(x, y, fondo);
	    if (new Color(cohete.getRGB(x+1,y)).getRGB() != c.getRGB())pintarInundacionDiferente(x+1, y, d, c, fondo);
	    if (new Color(cohete.getRGB(x-1,y)).getRGB() != c.getRGB()) pintarInundacionDiferente(x-1, y, d, c, fondo);
	    if (new Color(cohete.getRGB(x,y+1)).getRGB() != c.getRGB()) pintarInundacionDiferente(x, y + 1, d, c, degradarColor(fondo, d));
	    if (new Color(cohete.getRGB(x, y - 1)).getRGB() != c.getRGB())  pintarInundacionDiferente(x,y-1,d, c, degradarColor(fondo, d));
	}
	
	//LLamar a dibujar cohete antes para que no sea "vacio"
	public BufferedImage getCohete() {
		return cohete;
	}
	
	public BufferedImage getCohete2() {
		return cohete2;
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

	public void dibujarCircunferencia(int x0, int y0, int R,int gradosInicio, int gradosFin, Color c) {
		int x=0, y=0;
		double aumento = (double)40/(double)R;
		for(double t = gradosInicio; t <gradosFin; t+=aumento) {
			x = (int) Math.round(x0+(R*Math.sin(Math.toRadians(t))));
			y = (int) Math.round(y0+(R*Math.cos(Math.toRadians(t))));
			ponerPixel(x, y, c);
		}		
	}
	
	public void rectangulo(int x0, int y0, int x1, int y1, Color c) {
		dibujarLineaVertical(x0,y0,y1,c);
		dibujarLineaHorizontal(x0,x1,y1,c);
		dibujarLineaVertical(x1,y0,y1,c);
		dibujarLineaHorizontal(x0,x1,y0,c);
	}

	public void ponerPixel(int x, int y, Color c) {
		buffer.setRGB(0, 0, c.getRGB());
		g.drawImage(buffer, x, y,null);

	}
	
	public int getRandom(int min, int max) {
	    return (int) (Math.random() * (max - min + 1) + min);
	}

	
}
