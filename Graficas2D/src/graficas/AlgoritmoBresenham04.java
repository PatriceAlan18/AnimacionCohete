package graficas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class AlgoritmoBresenham04 extends JFrame{

	private BufferedImage buffer;
	private Graphics graPixel;
	
	public AlgoritmoBresenham04() {
		this.setVisible(true);
		this.setBounds(0,0,500,500);
		this.setTitle("AlgoritmoBresenham04");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		buffer = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
		graPixel = (Graphics2D) buffer.createGraphics();
	}
	
	public static void main(String[] args) {
		
		AlgoritmoBresenham04 ventana = new AlgoritmoBresenham04();
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
		//ventana.getGraphics().drawLine(20, 88, 480, 88);



	}
	
	public void ponerPixel(int x, int y, Color c) {
		buffer.setRGB(0, 0, c.getRGB());
		this.getGraphics().drawImage(buffer, x, y,this);

	}
	
	public void dibujarLinea(int x0, int y0, int x1, int y1, Color c) {
		
		
		int dy = y1-y0;
		int dx = x1-x0;
		if(dx==0) {
			dibujarLineaVertical(x0,y0,y1,c);
			return;
		}

		int incYi, incYr, incXr, incXi, k;
		if(dy>=0)incYi = 1;
		else {
			dy= -dy;
			incYi=-1;
		}
		
		if(dx>=0)incXi = 1;
		else {
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
		System.out.println("Inicio: "+x+" , "+y);

		int avR = (2*dy);
		int av = (avR - dx);
		int avI = (av-dx);
		do {
			System.out.println(""+x+" , "+y);
			ponerPixel(x,y, c);
			//System.out.println(av + " ");
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
