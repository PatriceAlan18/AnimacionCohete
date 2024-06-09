package graficas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Elipse extends JFrame{

	private BufferedImage buffer;
	private Graphics graPixel;
	
	public Elipse() {
		this.setResizable(false);
		this.setVisible(true);
		this.setTitle("Elipse");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(450,100,500,500);
		buffer = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
		graPixel = (Graphics2D) buffer.createGraphics();
	}
	
	public static void main(String[] args) {
		Elipse ventana = new Elipse();
		inicializarGraficos();
		ventana.dibujarElipse(250,250,134, 180, Color.BLUE);
	}
	
	public void dibujarElipse(int x0, int y0, int R, int R2, Color c) {
		int y1 = R;
		int y2 = R2;
		ponerPixel(x0+y2,y0,c);
		ponerPixel(x0-y2, y0,c);
		ponerPixel(x0,y0-y1,c);
		ponerPixel(x0, y0+y1,c);
		for(int x = 0; x <= R2; x++) {
			int y = (int) Math.round(R * Math.sqrt(1 - ((double) x * x) / (R2 * R2)));
			ponerPixel(x0+x,y0+y,c);
			ponerPixel(x0-x,y0+y,c);
			ponerPixel(x0+x,y0-y,c);
			ponerPixel(x0-x,y0-y,c);
	
		}
		
		for(int y = 0; y <= R; y++) {
			int x = (int) Math.round(R2 * Math.sqrt(1 - ((double) y * y) / (R * R)));
			ponerPixel(x0+x,y0+y,c);
			ponerPixel(x0-x,y0+y,c);
			ponerPixel(x0+x,y0-y,c);
			ponerPixel(x0-x,y0-y,c);
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
