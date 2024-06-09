package graficas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class FigurasBasicas12 extends JFrame{

	private BufferedImage buffer;
	private Graphics graPixel;
	
	public FigurasBasicas12() {
		this.setResizable(false);
		this.setVisible(true);
		this.setTitle("Figuras Basicas");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(450,100,500,500);
		buffer = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
		graPixel = (Graphics2D) buffer.createGraphics();
	}
	
	public static void main(String[] args) {
		FigurasBasicas12 ventana = new FigurasBasicas12();
		inicializarGraficos();
		ventana.dibujarLinea(40, 40, 80, 80, Color.blue);
		ventana.dibujarLinea(100, 60, 200, 60, Color.blue);
		ventana.dibujarLinea(280, 40, 240, 80, Color.blue);
		ventana.dibujarLinea(400, 60, 300, 60, Color.blue);
		ventana.dibujarCircunferencia(100, 200, 80, Color.blue);
		ventana.dibujarCircunferencia(100, 200, 60, Color.blue);
		ventana.dibujarCircunferencia(100, 200, 40, Color.blue);
		ventana.dibujarCircunferencia(100, 200, 20, Color.blue);
		ventana.dibujarRectangulo(220, 120, 340, 180, Color.blue);
		ventana.dibujarRectangulo(360, 210, 200, 90, Color.blue);
		ventana.dibujarElipse(300, 350, 80, 120, Color.blue);
		ventana.dibujarElipse(300, 350, 60, 100, Color.blue);
		ventana.dibujarElipse(300, 350, 40, 80, Color.blue);
		ventana.dibujarElipse(300, 350, 20, 60, Color.blue);


	}
	
	public void dibujarLinea(int x0, int y0, int x1, int y1, Color c) {
		
		int dy = y1-y0;
		int dx = x1-x0;
		
		if(dx==0) {
			dibujarLineaVertical(x0,y0,y1,c);
			return;
		}
		
		int incYi = 1, incYr, incXr, incXi=1, k;
		if(dy<=0) {
			dy= -dy;
			incYi=-1;
		}
		if(dx<0) {
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
		int avR = (2*dy);
		int av = (avR - dx);
		int avI = (av-dx);
		do {
			ponerPixel(x,y, c);
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
	
	public void dibujarRectangulo(int x0, int y0, int x1, int y1, Color c) {
		dibujarLinea(x0,y0,x0,y1,c);
		dibujarLinea(x0,y1,x1,y1,c);
		dibujarLinea(x1,y0,x1,y1,c);
		dibujarLinea(x0,y0,x1,y0,c);
	}
	
	public void dibujarCircunferencia(int x0, int y0, int R, Color c) {
		double pk = 3-2*R;
		int y = R;
		int x = 0;
		ponerPixel(x0,y0,c);
		ponerPixel(x0, y0-(2*R),c);
		ponerPixel(x0-R,y0-R,c);
		ponerPixel(x0+R, y0-R,c);
		while(x<=y) {
			if(pk<=0) {
				System.out.println((x)+ " , "+y);
				pk = pk + 4*x + 6;
			}
			else {
				System.out.println((x)+" , "+(y-1));
				pk= pk + 4*(x-y) + 10;
				y--;
			}
			System.out.println("pk: "+pk);
			x++;
			dibujarOctantes(x,y, x0, y0,c);
			
		}
	}
	
	private void dibujarOctantes(int x, int y, int x0, int y0, Color c) {
		ponerPixel(x0+x,y0+y,c);
		ponerPixel(x0+y,y0-x,c);
		ponerPixel(x0+x,y0-y,c);
		ponerPixel(x0-y,y0-x,c);
		ponerPixel(x0-x,y0-y,c);
		ponerPixel(x0+y,y0+x,c);
		ponerPixel(x0-y,y0+x,c);
		ponerPixel(x0-x,y0+y,c);

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
