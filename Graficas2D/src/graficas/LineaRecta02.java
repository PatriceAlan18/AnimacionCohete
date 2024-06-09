package graficas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.annotation.processing.RoundEnvironment;
import javax.swing.JFrame;

public class LineaRecta02 extends JFrame{

	private BufferedImage buffer;
	private Graphics graPixel;
	
	public LineaRecta02() {
		this.setVisible(true);
		this.setBounds(450,100,500,500);
		this.setTitle("Linea recta");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		buffer = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
		graPixel = (Graphics2D) buffer.createGraphics();
	}
	
	public static void main(String[] args) {
		
		LineaRecta02 ventana = new LineaRecta02();
		try {
		    Thread.sleep(10);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		ventana.dibujarLinea(20, 78, 230, 78, Color.green);
		ventana.getGraphics().drawLine(20, 88, 480, 88);
		

	}
	
	public void ponerPixel(int x, int y, Color c) {
		buffer.setRGB(0, 0, c.getRGB());
		this.getGraphics().drawImage(buffer, x, y,this);

	}
	
	public void dibujarLinea(double x, double y, double xF, double yF, Color c) {
		
		if((xF-x)==0) {
			dibujarLineaVertical(x,y,yF,c);
			return;
		}
		
		System.out.println("Antes del cambio "+x+" , "+ y +" hacia "+xF+" , "+yF);

		
		if(xF<x) {
			double aux = x;
			x=xF;
			xF=aux;
			
			aux = y;
			y=yF;
			yF=aux;
			
			System.out.println("Despues del cambio "+x+" , "+ y +" hacia "+xF+" , "+yF);
			
		}
		
		double m = (yF-y)/(xF-x);
		double b = y-(m*x);
		double aux = 0;
		System.out.println("m: "+m+ "\nb: "+b);
		
		for(; x <= xF; x++) {
			aux = y;
			y = (int) ((m*x)+b);
			dibujarLineaVertical(x, y+1, aux, c);
			System.out.println("Normal: "+x+" , "+ y);
			ponerPixel((int)x, (int)y, c);
		}

		
		
	}
	
	public void dibujarLineaVertical(double x, double y, double yF, Color c) {
		if(yF<y) {
			double aux = y;
			y=yF;
			yF=aux;
		}
		for(int i = 0; i <= Math.abs(yF-y); i++) {
			System.out.println("Vertical: "+x+" , "+ (y+i));
			ponerPixel((int)x, (int)y+i, c);
		}
	}
	
}
