package graficas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Circunferencia09 extends JFrame{
	
	private BufferedImage buffer;
	private Graphics graPixel;
	
	public Circunferencia09() {
		this.setResizable(false);
		this.setVisible(true);
		this.setTitle("Circunferencia Simetria de 8 lados");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(450,100,500,500);
		buffer = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
		graPixel = (Graphics2D) buffer.createGraphics();
	}
	
	public static void main(String[] args) {
		Circunferencia09 ventana = new Circunferencia09();
		inicializarGraficos();
		ventana.dibujarCircunferencia(200,250,134, Color.BLUE);
	}
	
	public void dibujarCircunferencia(int x0, int y0, int R, Color c) {
		int x=0, y=0, distanciaX, distanciaY;
		double aux = y0;
		for(double t = 180; t <=270.4; t+=.4) {
			x = (int) Math.round(x0+(R*Math.sin(Math.toRadians(t))));
			y = (int) Math.round(y0+(R*Math.cos(Math.toRadians(t))));
			distanciaX = Math.abs(x0-x);
			distanciaY = Math.abs(y0-y);
			System.out.println(x+" , "+y+ "    con t: "+t);
			ponerPixel(x, y, c);
			ponerPixel(x0+distanciaX, y, c);
			ponerPixel(x, y0+distanciaY, c);
			ponerPixel(x0+distanciaX, y0+distanciaY, c);
			
			
		}
		
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
