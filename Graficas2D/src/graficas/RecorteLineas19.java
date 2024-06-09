package graficas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class RecorteLineas19 extends JFrame{

	private BufferedImage buffer, pantalla;
	private Graphics graPixel, graPantalla;
	private final static Color NEGRO = new Color(0,0,1);
	
	public RecorteLineas19() {
		this.setResizable(false);
		this.setVisible(true);
		this.setTitle("Recorte lineas");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(450,100,500,500);
		pantalla = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		graPantalla = (Graphics2D)pantalla.createGraphics();
		buffer = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		graPixel = (Graphics2D) buffer.createGraphics();
		inicializarGraficos();
	}
	
	public static void main(String[] args) {
		RecorteLineas19 ventana = new RecorteLineas19();
		ventana.rectangulo(100, 100, 400, 300, NEGRO);
		ventana.recortarLinea(200, 450, 200, 50, NEGRO);
		ventana.recortarLinea(150, 60, 280, 440, NEGRO);
		ventana.recortarLinea(50, 50, 200, 400, NEGRO);
		ventana.recortarLinea(50, 200, 450, 250, NEGRO);
		ventana.recortarLinea(450,200,50,280, NEGRO);
		ventana.recortarLinea(350,200,450,50, NEGRO);
		ventana.recortarLinea(150, 200, 250, 200, NEGRO);
		ventana.recortarLinea(450, 50, 200, 200, NEGRO);
		ventana.recortarLinea(450, 50, 50, 350, NEGRO);


		ventana.finalizar();
	}
	
	public void finalizar() {
		this.getGraphics().drawImage(pantalla,0,0,null);
	}
	
	//ventana.rectangulo(100, 100, 400, 300, NEGRO);
	public void recortarLinea(int x0, int y0, int x1, int y1, Color c) {
		int[] codigo0 = new int[4];
		int[] codigo1 = new int[4];
		int yMinima = 100;
		int yMaxima = 300;
		int xMinima = 100;
		int xMaxima = 400;
		int bandera = 0;
		
	    double m = (double)(y1 - y0) / (double)(x1 - x0);
	    if(x0<xMinima)codigo0[3]=1;
		else codigo0[3]=0;
		if(x1<xMinima)codigo1[3]=1;
		else codigo1[3]=0;
		//Derecha
		if(x0>xMaxima)codigo0[2]=1;
		else codigo0[2]=0;
		if(x1>xMaxima)codigo1[2]=1;
		else codigo1[2]=0;
		//Arriba
		if(y0<yMinima)codigo0[1]=1;
		else codigo0[1]=0;
		if(y1<yMinima)codigo1[1]=1;
		else codigo1[1]=0;
		//Abajo
		if(y0>yMaxima)codigo0[0]=1;
		else codigo0[0]=0;
		if(y1>xMaxima)codigo1[0]=1;
		else codigo1[0]=0;	
		for(int i = 0; i<4; i++)
			if(codigo0[i]==1 && codigo1[i]==1)return;
	    
		if(codigo0[0]==0 && codigo0[1]==0 && codigo0[2]==0 && codigo0[3]==0) {
			if(codigo1[0]==0 && codigo1[1]==0 && codigo1[2]==0 && codigo1[3]==0) {
				dibujarLinea(x0,y0,x1,y1,c);
				return;
			}
			//P1 se debe encontrar afuera
			int aux = x0;
			x0 = x1;
			x1 = aux;
			aux = y0;
			y0 = y1;
			y1 = aux;	

			
		}
		
	    if (x0 < xMinima) {
	        y0 = (int)(m * (xMinima - x0)) + y0;
	        x0 = xMinima;
	    }
	    if (x0 > xMaxima) {
	        y0 = (int)(m * (xMaxima - x0)) + y0;
	        x0 = xMaxima;
	    }
	    if (y0 < yMinima) {
	        x0 = (int)((yMinima - y0) / m) + x0;
	        y0 = yMinima;
	    }
	    if (y0 > yMaxima) {
	        x0 = (int)((yMaxima - y0) / m) + x0;
	        y0 = yMaxima;
	    }

	    if (x1 < xMinima) {
	        y1 = (int)(m * (xMinima - x1)) + y1;
	        x1 = xMinima;
	    }
	    if (x1 > xMaxima) {
	        y1 = (int)(m * (xMaxima - x1)) + y1;
	        x1 = xMaxima;
	    }
	    if (y1 < yMinima) {
	        x1 = (int)((yMinima - y1) / m) + x1;
	        y1 = yMinima;
	    }
	    if (y1 > yMaxima) {
	        x1 = (int)((yMaxima - y1) / m) + x1;
	        y1 = yMaxima;
	    }
	    dibujarLinea(x0, y0, x1, y1, c);

		
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
