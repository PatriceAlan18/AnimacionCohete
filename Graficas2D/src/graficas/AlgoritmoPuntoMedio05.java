package graficas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class AlgoritmoPuntoMedio05 extends JFrame{
	
	private BufferedImage buffer;
	private Graphics graPixel;
	
	public AlgoritmoPuntoMedio05() {
		this.setVisible(true);
		this.setBounds(450,100,500,500);
		this.setTitle("AlgoritmoPuntoMedio");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		buffer = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
		graPixel = (Graphics2D) buffer.createGraphics();
	}
	
	public static void main(String[] args) {
		AlgoritmoPuntoMedio05 ventana = new AlgoritmoPuntoMedio05();
		try {
		    Thread.sleep(10);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		ventana.dibujarLinea(400, 300, 0, 400, Color.blue);
	}
	
	public void dibujarLinea(int x1, int y1, int x2, int y2, Color c) {
		
		int dx = Math.abs(x2 - x1);
	    int dy = Math.abs(y2 - y1);
	    int d = 2 * dy - dx;
	    int incrE = 2 * dy;
	    int incrNE = 2 * (dy - dx);
	    int x = x1;
	    int y = y1;
	    int xIncr = (x2 > x1) ? 1 : -1; 
	    int yIncr = (y2 > y1) ? 1 : -1; 
	    
	    ponerPixel(x, y,c); 
	    
	    while (x != x2 || y != y2) {
	        if (d <= 0) {
	            d += incrE;
	        } else {
	            d += incrNE;
	            y += yIncr;
	        }
	        x += xIncr;
	        ponerPixel(x, y,c); 
	    }
		
	}

	public void ponerPixel(int x, int y, Color c) {
		buffer.setRGB(0, 0, c.getRGB());
		this.getGraphics().drawImage(buffer, x, y,this);

	}
	

}
