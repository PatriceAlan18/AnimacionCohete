package graficas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Circunferencia10 extends JFrame{

	private BufferedImage buffer;
	private Graphics graPixel;
	
	public Circunferencia10() {
		this.setResizable(false);
		this.setVisible(true);
		this.setTitle("Circunferencia Punto Medio");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(450,100,500,500);
		buffer = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
		graPixel = (Graphics2D) buffer.createGraphics();
	}
	
	public static void main(String[] args) {
		Circunferencia10 ventana = new Circunferencia10();
		inicializarGraficos();
		ventana.dibujarCircunferencia(200,250,134, Color.BLUE);
	}
	
	public void dibujarCircunferencia(int x0, int y0, int R, Color c) {
		double pk = (5.0/4.0)-R;
		int y = R;
		int x = 0;
		ponerPixel(x0,y0,c);
		ponerPixel(x0, y0-(2*R),c);
		ponerPixel(x0-R,y0-R,c);
		ponerPixel(x0+R, y0-R,c);
		while(x<=y) {
			if(pk<=0) {
				System.out.println((x)+ " , "+y);
				pk = pk + 2*x + 3;
			}
			else {
				System.out.println((x)+" , "+(y-1));
				pk= pk + 2*(x-y) + 5;
				y--;
			}
			System.out.println("pk: "+pk);
			x++;
			dibujarOctantes(x,y, x0, y0,c);
			try {
			    Thread.sleep(50);
			} catch (InterruptedException e) {
			    e.printStackTrace();
			}
		}
	}
	
	public void dibujarOctantes(int x, int y, int x0, int y0, Color c) {
		ponerPixel(x0+x,y0+y,c);
		ponerPixel(x0+y,y0-x,c);
		ponerPixel(x0+x,y0-y,c);
		ponerPixel(x0-y,y0-x,c);
		ponerPixel(x0-x,y0-y,c);
		ponerPixel(x0+y,y0+x,c);
		ponerPixel(x0-y,y0+x,c);
		ponerPixel(x0-x,y0+y,c);

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
