package graficas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class CircunferenciaGrosor16 extends JFrame{

	private BufferedImage buffer;
	private Graphics graPixel;
	
	public CircunferenciaGrosor16() {
		this.setResizable(false);
		this.setVisible(true);
		this.setTitle("Circunferencia Mascara");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(450,100,500,500);
		buffer = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
		graPixel = (Graphics2D) buffer.createGraphics();
		inicializarGraficos();
	}
	
	public static void main(String[] args) {
		CircunferenciaGrosor16 ventana = new CircunferenciaGrosor16();
		ventana.dibujarCircunferenciaGrosor(250, 250, 200,5,Color.BLACK);	
		}
	
	
	public void dibujarCircunferenciaGrosor(int x0, int y0, int R,int Grosor, Color c) {

		double pk = 3-2*R;
		int y = R;
		int x = 0;
		int i =0;
		int mitad = Grosor/2;
		int mitadFinal = mitad;
		if(Grosor%2==1)mitadFinal++;
			dibujarRectangulo(x0+y-mitad, y0-mitad, x0+y+mitadFinal, y0+mitadFinal, c);
			dibujarRectangulo(x0-mitad,y0+y-mitad, x0+mitadFinal, y0+y+mitadFinal,c);
			dibujarRectangulo(x0-mitad, y0-y-mitad, x0+mitadFinal, y0-y+mitadFinal,c);
			dibujarRectangulo(x0-y-mitad,y0-mitad,x0-y+mitadFinal, y0+mitadFinal,c);

		while(x<=y) {
			if(pk<=0) {
				pk = pk + 4*x + 6;
			}
			else {
				pk= pk + 4*(x-y) + 10;
				y--;
			}
			x++;
			dibujarOctantesCuadrados(x,y, x0, y0, mitad, mitadFinal,c);

		}
		
			
	}
	
	private void dibujarOctantesCuadrados(int x, int y, int x0, int y0, int mitad, int mitadFinal,Color c) {
		
			dibujarRectangulo(x0+x-mitad,y0+y-mitad,x0+x+mitadFinal, y0+y+mitadFinal,c);
			dibujarRectangulo(x0+y-mitad,y0-x-mitad,x0+y+mitadFinal,y0-x+mitadFinal,c);
			dibujarRectangulo(x0+x-mitad,y0-y-mitad,x0+x+mitadFinal,y0-y+mitadFinal,c);
			dibujarRectangulo(x0-y-mitad,y0-x-mitad,x0-y+mitadFinal,y0-x+mitadFinal,c);
			dibujarRectangulo(x0-x-mitad,y0-y-mitad,x0-x+mitadFinal,y0-y+mitadFinal,c);
			dibujarRectangulo(x0+y-mitad,y0+x-mitad,x0+y+mitadFinal,y0+x+mitadFinal,c);
			dibujarRectangulo(x0-y-mitad,y0+x-mitad,x0-y+mitadFinal,y0+x+mitadFinal,c);
			dibujarRectangulo(x0-x-mitad,y0+y-mitad,x0-x+mitadFinal,y0+y+mitadFinal,c);		
	}
	
	
	public void dibujarLineaVertical(double x, double y, double yF, Color c) {
		if(yF<y) {
			double aux = y;
			y=yF;
			yF=aux;
		}
		for(int i = 0; i <= Math.abs(yF-y); i++) ponerPixel((int)x, (int)y+i, c);
		
	}
	
	public void dibujarLineaHorizontal(double x, double xF, double y, Color c) {
		if(xF<x) {
			double aux = x;
			x = xF;
			xF = aux;
		}
		for(int i = 0; i<=Math.abs(xF-x); i++) ponerPixel((int)x+i,  (int)y, c);
	}
	
	public void dibujarRectangulo(int x0, int y0, int x1, int y1, Color c) {
		dibujarLineaVertical(x0,y0,y1,c);
		dibujarLineaHorizontal(x0,x1,y1,c);
		dibujarLineaVertical(x1,y0,y1,c);
		dibujarLineaHorizontal(x0,x1,y0,c);
	}
	
	public static void inicializarGraficos() {
		try {
		    Thread.sleep(10);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	}
	
	public void ponerPixel(int x, int y, Color c) {
		buffer.setRGB(0, 0, c.getRGB());
		this.getGraphics().drawImage(buffer, x, y,this);

	}
	
}
