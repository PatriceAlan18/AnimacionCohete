package proyecto;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Planeta {
	
	private Color BLANCO = new Color(255,255,254);
	private BufferedImage buffer, planeta, aux;
	private Graphics gAux;
	private BufferedImage[] planetas;
	private Graphics[] gP;
	private final Color NEGRO = new Color(0,0,1);
	private Color[] anillos = {new Color(255, 105, 180,200), new Color(218, 112, 214,200), new Color(0, 255, 127,200), new Color(255, 215, 0,200), new Color(30, 144, 255,200)};
	private Color[] colores = {
            new Color(194, 187, 176),   // Mercurio
            new Color(197, 137, 16),    // Venus
            new Color(25, 118, 210),    // Tierra
            new Color(204, 85, 0),      // Marte
            new Color(216, 189, 104),   // Júpiter
            new Color(229, 204, 153),   // Saturno
            new Color(0, 170, 204),     // Urano
            new Color(0, 73, 170)       // Neptuno
        };
	private int[] R, x,y;
	int n;
	
	public Planeta(int n) {
		aux = new BufferedImage(Principal.Width,Principal.Height,BufferedImage.TYPE_INT_ARGB);
		gAux = (Graphics2D) aux.createGraphics();
		buffer = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		planeta = new BufferedImage(Principal.Width,Principal.Height, BufferedImage.TYPE_INT_ARGB);
		gAux = (Graphics2D) planeta.createGraphics();
		this.n = n;
		inicializarTodos();
	}
	
	public void inicializarTodos() {
		R = new int[n];
		planetas = new BufferedImage[n];
		gP = new Graphics[n];
		x = new int[n];
		y = new int[n];
		for(int i = 0; i<n; i++) {
			x[i] = getRandom(100, Principal.Width+100);
			y[i] = getRandom(40, Principal.Height);
			R[i] = getRandom(8, 15);
			planetas[i] = new BufferedImage(80, 80, BufferedImage.TYPE_INT_ARGB);
			gP[i] = (Graphics2D) planetas[i].createGraphics();
			dibujarPlaneta(R[i], colores[getRandom(0, colores.length-1)], planetas[i], gP[i]);
		}
	}
	
	public void mover() {
		for(int i = 0; i<n; i++) {
			x[i]=x[i]-1;
			if(x[i]<-60) {
				x[i] = getRandom(550, Principal.Width+100);
				y[i] = getRandom(40, Principal.Height);
				R[i] = getRandom(8, 15);
				planetas[i] = new BufferedImage(80,80,BufferedImage.TYPE_INT_ARGB);
				gP[i] = (Graphics2D) planetas[i].createGraphics();
				dibujarPlaneta(R[i], colores[getRandom(0, colores.length-1)], planetas[i], gP[i]);

			}
		}
	}
	
	
	private void dibujarPlaneta(int R, Color c, BufferedImage img, Graphics g) {
		//rectangulo(0, 0, 49, 49, BLANCO,img,g);
		Color borde = new Color(c.getRed(), c.getGreen(), c.getBlue(), 80);
		dibujarCircunferencia(25,25, R, borde,img,g);
		pintarInundacion(25, 25, new Color(img.getRGB(25, 25)), new Color(0,0,1),img,g);
		pintarInundacion(25, 25, new Color(img.getRGB(25, 25)), new Color(c.getRed(), c.getGreen(), c.getBlue(), 100),img,g);
		if(getRandom(0, 5)>0)dibujarAnillo( R,img, g);
	}
	
	public void dibujarAnillo(int R,BufferedImage img, Graphics g) {
		int color = getRandom(0, anillos.length-1);
		dibujarLinea(25-R-1, 25+getRandom(-4, 4), 25, 25, 4, anillos[color],img,g);
		dibujarLinea(25+R+1, 25+getRandom(-4, 4), 25, 25, 4, anillos[color],img,g);
	}
	
	public void dibujarPlanetas() {
		gAux.drawImage(planeta, 0, 0,null);
		for(int i = 0; i<n; i++) {
			gAux.drawImage(planetas[i], x[i], y[i],null);
		}
	}
	
	public BufferedImage getPlaneta() {
		aux = new BufferedImage(Principal.Width,Principal.Height,BufferedImage.TYPE_INT_ARGB);
		gAux = (Graphics) aux.createGraphics();
		mover();
		dibujarPlanetas();
		return aux;
	}
	
	public void rectangulo(int x0, int y0, int x1, int y1, Color c, BufferedImage img, Graphics g) {
		dibujarLineaVertical(x0,y0,y1,c,img,g);
		dibujarLineaHorizontal(x0,x1,y1,c,img,g);
		dibujarLineaVertical(x1,y0,y1,c,img,g);
		dibujarLineaHorizontal(x0,x1,y0,c,img,g);
	}
	
	public void dibujarLinea(int x0, int y0, int x1, int y1, int G, Color c, BufferedImage img, Graphics g) {
		int dy = y1-y0;
		int dx = x1-x0;
		int grosorH = 0, grosorV=0;
		if((double)dy/(double)dx>1)grosorH=G;
		else grosorV=G;

		if(dx==0) {
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
		int i = 0;
		int avR = (2*dy);
		int av = (avR - dx);
		int avI = (av-dx);
		do {
				dibujarGrosor(x,y,grosorV, grosorH, c,img,g);
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
			i++;
		}while(x!=x1);
		dibujarGrosor(x,y,grosorV, grosorH, c,img,g);

	}
	
	private void dibujarGrosor(int x, int y, int GV, int GH, Color c, BufferedImage img, Graphics g) {
		if(GV>0)y = y - GV/2;
		else x = x-GH/2;
		for(int i = 0; i<GV; i++) ponerPixel(x,y+i, c,img,g);
		for(int i = 0; i<GH; i++)ponerPixel(x+i,y, c,img,g);

		
		
	}

	private void dibujarLineaVertical(int x, int y, int yF, Color c,BufferedImage img, Graphics g) {
		if(yF<y) {
			int aux = y;
			y=yF;
			yF=aux;
		}
		for(int i = 0; i <= Math.abs(yF-y); i++)ponerPixel(x, y+i, c,img,g);
	}
	
	private void dibujarLineaHorizontal(int x, int xF, int y, Color c, BufferedImage img, Graphics g) {
		if(xF<x) {
			int aux = x;
			x=xF;
			xF=aux;
		}
		for(int i = 0; i <= Math.abs(xF-x); i++)  ponerPixel(x+i, y, c,img,g);			
	}

	
	public void pintarInundacion(int x, int y, Color c, Color fondo, BufferedImage img, Graphics g) {
	    ponerPixel(x, y, fondo,img,g);
	    fondo = new Color(fondo.getRed(), fondo.getGreen(), fondo.getBlue(), getRandom(180, 255));
	    if (new Color(img.getRGB(x-1,y)).getRGB() == c.getRGB()) pintarInundacion(x-1, y, c, fondo,img,g);
	    if (new Color(img.getRGB(x+1,y)).getRGB() == c.getRGB())pintarInundacion(x+1, y, c, fondo,img,g);
	    if (new Color(img.getRGB(x, y - 1)).getRGB() == c.getRGB())  pintarInundacion(x,y-1, c, fondo,img,g);
	    if (new Color(img.getRGB(x,y+1)).getRGB() == c.getRGB()) pintarInundacion(x, y + 1, c,fondo,img,g);

	}

	public Color degradarColor(Color c, int d){
		int red = c.getRed()-d;
	    int green = c.getGreen()-d;
	    int blue = c.getBlue()-d;
	   return new Color(Math.max(0, red), Math.max(0, green), Math.max(0, blue),255);
	}
		
	public void dibujarCircunferencia(int x0, int y0, int R, Color c, BufferedImage img, Graphics g) {
		double pk = 3-2*R;
		int y = R;
		int x = 0;
		dibujarOctantes(x,y, x0, y0,c,img,g);
		while(x<=y) {
			if(pk<=0) {
				pk = pk + 4*x + 6;
			}
			else {
				pk= pk + 4*(x-y) + 10;
				y--;
			}
			x++;
			dibujarOctantes(x,y, x0, y0,c,img,g);
			
		}
		x++;
	}
	
	public void dibujarOctantes(int x, int y, int x0, int y0, Color c, BufferedImage img, Graphics g) {
		ponerPixel(x0+x,y0+y,c,img,g);
		ponerPixel(x0+y,y0-x,c,img,g);
		ponerPixel(x0+x,y0-y,c,img,g);
		ponerPixel(x0-y,y0-x,c,img,g);
		ponerPixel(x0-x,y0-y,c,img,g);
		ponerPixel(x0+y,y0+x,c,img,g);
		ponerPixel(x0-y,y0+x,c,img,g);
		ponerPixel(x0-x,y0+y,c,img,g);

	}
	
	public void dibujarCircunferencia(int x0, int y0, int R,int gradosInicio, int gradosFin, Color c, BufferedImage img, Graphics g) {
		int x=0, y=0;
		double aumento = (double)40/(double)R;
		int contador = 0;
		for(double t = gradosInicio; t <gradosFin; t+=aumento) {
			x = (int) Math.round(x0+(R*Math.sin(Math.toRadians(t))));
			y = (int) Math.round(y0+(R*Math.cos(Math.toRadians(t))));
			ponerPixel(x, y, c,img,g);
		}		
	}
	
	
	public int getRandom(int min, int max) {
	    return (int) (Math.random() * (max - min + 1) + min);
	}
	
	public void ponerPixel(int x, int y, Color c, BufferedImage img, Graphics g) {
		buffer.setRGB(0, 0, c.getRGB());
		g.drawImage(buffer, x, y,null);

	}
}
