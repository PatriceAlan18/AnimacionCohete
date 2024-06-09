package graficas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class RecorteCircunferencias20 extends JFrame{
	private BufferedImage buffer, pantalla;
	private Graphics graPixel, graPantalla;
	private final static Color NEGRO = new Color(0,0,1);
	
	public RecorteCircunferencias20() {
		this.setResizable(false);
		this.setVisible(true);
		this.setTitle("Recorte circunferencias");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(450,100,500,500);
		pantalla = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		graPantalla = (Graphics2D)pantalla.createGraphics();
		buffer = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		graPixel = (Graphics2D) buffer.createGraphics();
		inicializarGraficos();
	}
	
	public static void main(String[] args) {
		RecorteCircunferencias20 ventana = new RecorteCircunferencias20();
		ventana.rectangulo(100, 100, 400, 300, NEGRO);
		ventana.recortarCircunferencia(250, 250, 100, NEGRO);
		ventana.recortarCircunferencia(100, 100, 100,NEGRO);
		ventana.recortarCircunferencia(250, 200, 150, NEGRO);

		ventana.finalizar();
	}
	
	public void finalizar() {
		this.getGraphics().drawImage(pantalla,0,0,null);
	}
	
	//ventana.rectangulo(100, 100, 400, 300, NEGRO);
	public void recortarCircunferencia(int x, int y, int R, Color c) {
	    int yMinima = 100;
	    int yMaxima = 300;
	    int xMinima = 100;
	    int xMaxima = 400;


	    int x1 = x - R - 1; 
	    int x2 = x + R + 1; 
	    int y1 = y - R - 1; 
	    int y2 = y + R + 1; 

	    if(x1 < xMinima) x1 = xMinima;
	    if(x2 > xMaxima) x2 = xMaxima;
	    if(y1 < yMinima) y1 = yMinima;
	    if(y2 > yMaxima) y2 = yMaxima;

	    for(int i = x1; i < x2; i++) {
	        for(int j = y1; j < y2; j++) {
	            double distancia = Math.sqrt(Math.pow(i-x, 2) + Math.pow(j-y, 2));
	            if(distancia >= R - 0.5 && distancia <= R + 0.5) { // margen de error de 0.5 píxeles
	                ponerPixel(i, j, c);
	            }
	        }
	    }
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
		int contador = 0;
		for(double t = gradosInicio; t <gradosFin; t+=aumento) {
			x = (int) Math.round(x0+(R*Math.sin(Math.toRadians(t))));
			y = (int) Math.round(y0+(R*Math.cos(Math.toRadians(t))));
			ponerPixel(x, y, c);
		}		
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
}
