package graficas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Circunferencia07 extends JFrame{

	private BufferedImage buffer;
	private Graphics graPixel;
	
	public Circunferencia07() {
		this.setResizable(false);
		this.setVisible(true);
		this.setTitle("Circunferencia");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(450,100,500,500);
		buffer = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
		graPixel = (Graphics2D) buffer.createGraphics();
	}
	
	public static void main(String[] args) {
		Circunferencia07 ventana = new Circunferencia07();
		inicializarGraficos();
		ventana.dibujarCircunferencia(200,250,134, Color.BLUE);
	}
	
	public void dibujarCircunferencia(int x0, int y0, int R, Color c) {
		int y, operacion;
		for(int x = x0-R; x<=x0+R; x++) {
			operacion = (int) Math.sqrt((R*R)-(Math.pow(x-x0, 2)));
			y = y0 + operacion;
			ponerPixel(x, y, c);
			y = y0 - operacion;
			ponerPixel(x, y, c);

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
