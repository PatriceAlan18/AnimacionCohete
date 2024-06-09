package graficas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class TiposLineaCircunferencia15 extends JFrame{

	private BufferedImage buffer;
	private Graphics graPixel;
	
	public TiposLineaCircunferencia15() {
		this.setResizable(false);
		this.setVisible(true);
		this.setTitle("Circunferencia Mascara");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(450,100,500,500);
		buffer = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
		graPixel = (Graphics2D) buffer.createGraphics();
		inicializarGraficos();
	}
	
	public static void main(String[] args) {
		TiposLineaCircunferencia15 ventana = new TiposLineaCircunferencia15();
		ventana.dibujarCircunferencia(250, 250, 200, "1011", Color.BLACK);	
		}
	
	public void dibujarCircunferencia(int x0, int y0, int R, String M, Color c) {
		if(M.length()==0)M="1";
		char[] mascara = M.toCharArray();
		double pk = 3-2*R;
		int y = R;
		int x = 0;
		int i =0;
		if(mascara[i]=='1') {
			ponerPixel(x0,y0+y,c);
			ponerPixel(x0, y0-y,c);
			ponerPixel(x0-R,y0,c);
			ponerPixel(x0+R, y0,c);
		}
		i++;
		while(x<=y) {
			if(pk<=0) {
				pk = pk + 4*x + 6;
			}
			else {
				pk= pk + 4*(x-y) + 10;
				y--;
			}
			x++;
			if(mascara.length==i) i=0;
			if(mascara[i]=='1')dibujarOctantes(x,y, x0, y0,c);
			i++;
		}
	}
	
	private void dibujarOctantes(int x, int y, int x0, int y0,Color c) {
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
