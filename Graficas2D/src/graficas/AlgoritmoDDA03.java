package graficas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import javax.swing.JFrame;

public class AlgoritmoDDA03 extends JFrame{

	private BufferedImage buffer;
	private Graphics graPixel;
	
	public AlgoritmoDDA03() {
		this.setVisible(true);
		this.setBounds(450,100,500,500);
		this.setTitle("AlgoritmoDDA03");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		buffer = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
		graPixel = (Graphics2D) buffer.createGraphics();
	}
	
	
	public static void main(String[] args) {
		
		AlgoritmoDDA03 ventana = new AlgoritmoDDA03();
		try {
		    Thread.sleep(10);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		Color c = Color.black;
		ventana.dibujarLinea(163, 166, 162, 214, c);
		ventana.dibujarLinea(162, 214, 214, 214, c);
		ventana.dibujarLinea(214, 214, 215, 166, c);
		ventana.dibujarLinea(215, 166, 163, 166, c);

		ventana.dibujarLinea(148, 148, 147, 192, c);
		ventana.dibujarLinea(147, 192, 195, 192, c);
		ventana.dibujarLinea(195, 192, 195, 148, c);
		ventana.dibujarLinea(195, 148, 148, 148, c);
		
		ventana.dibujarLinea(163, 166, 148, 148, c);
		ventana.dibujarLinea(162, 214, 147, 192, c);
		ventana.dibujarLinea(214, 214, 195, 192, c);
		ventana.dibujarLinea(215, 166, 195, 148, c);

	}
	
	public void ponerPixel(int x, int y, Color c) {
		buffer.setRGB(0, 0, c.getRGB());
		this.getGraphics().drawImage(buffer, x, y,this);

	}
	
	public void dibujarLinea(int x0, int y0, int x1, int y1, Color c) {
		double dx = x1-x0;
		double dy = y1-y0;
		int steps = (int) dy;
		if(Math.abs(dx)>Math.abs(dy)) steps = (int) dx;
		double xinc = dx/steps;
		double yinc = dy/steps;
		double x = x0;
		double y = y0;;
		ponerPixel((int)Math.round(x), (int)Math.round(y), c);
		for(int i = 1; i<=steps; i++) {
			x = x+xinc;
			y = y+yinc;
			ponerPixel((int)Math.round(x), (int)Math.round(y), c);

		}
	}
	
}


