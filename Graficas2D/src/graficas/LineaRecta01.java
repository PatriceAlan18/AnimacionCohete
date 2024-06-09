package graficas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.annotation.processing.RoundEnvironment;
import javax.swing.JFrame;

public class LineaRecta01 extends JFrame{

	private BufferedImage buffer;
	private Graphics graPixel;
	
	public LineaRecta01() {
		this.setVisible(true);
		this.setBounds(450,100,500,500);
		this.setTitle("Linea recta");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		buffer = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
		graPixel = (Graphics2D) buffer.createGraphics();
	}
	
	//y = mx+b
	
	public static void main(String[] args) {
		
		LineaRecta01 ventana = new LineaRecta01();
		try {
		    Thread.sleep(10);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		//ventana.dibujarLinea(0,0,500,500, Color.black);
		ventana.dibujarLinea(500, 0, 0, 500, Color.green);
		

	}

	
	public void ponerPixel(int x, int y, Color c) {
		buffer.setRGB(0, 0, c.getRGB());
		this.getGraphics().drawImage(buffer, x, y,this);

	}
	
	//Este algoritmo aunque resulte simple cuenta con varios problemas a la hora de realizarlo, como que solo pinta un cuadrado para cada valor de x,
	//lo que provoca que al tener una gran pendiente se dibujen solo puntos que no son seguidos, ademas de que resulta un proceso un tanto lento
	
	public void dibujarLinea(double x, double y, double xF, double yF, Color c) {


		double m = (yF-y)/(xF-x);
		double b = y-(m*x);
		System.out.print("m: "+m+ "\nb: "+b);
		if(m>0) {
			for(;x<=xF;x++) {
				ponerPixel((int) x,(int) ((m*x)+b), c);
			}
		}
		else {
			for(;xF<=x;xF++) {
				ponerPixel((int) xF,(int) ((m*xF)+b), c);
			}
		}
		
	}
	
	
}
