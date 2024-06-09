package graficas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class MascaraLinea13 extends JFrame{

	private BufferedImage buffer;
	private Graphics graPixel;
	
	public MascaraLinea13() {
		this.setResizable(false);
		this.setVisible(true);
		this.setTitle("Mascara linea");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(450,100,500,500);
		buffer = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
		graPixel = (Graphics2D) buffer.createGraphics();
	}

	public void ponerPixel(int x, int y, Color c) {
		buffer.setRGB(0, 0, c.getRGB());
		this.getGraphics().drawImage(buffer, x, y,this);

	}
	
	public static void main(String[] args) {
		
		MascaraLinea13 ventana = new MascaraLinea13();
		inicializarGraficos();
		ventana.dibujarLinea(20, 78, 120, 308,"1", Color.BLUE);
		
	}

	public void dibujarLinea(int x0, int y0, int x1, int y1, String M, Color c) {
		if(M.length()==0)M="1";
		char[] mascara = M.toCharArray();
		int dy = y1-y0;
		int dx = x1-x0;
		if(dx==0) {
			dibujarLineaVertical(x0,y0,y1,M,c);
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
		int i = 0;
		int avR = (2*dy);
		int av = (avR - dx);
		int avI = (av-dx);
		do {
			if(mascara.length==i) i=0;
			if(mascara[i]=='1') ponerPixel(x,y, c);
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

			i++;
		}while(x!=x1);
	}

	public void dibujarLineaVertical(double x, double y, double yF,String M, Color c) {
		if(M.length()==0)M="1";
		char[] mascara = M.toCharArray();
		if(yF<y) {
			double aux = y;
			y=yF;
			yF=aux;
		}
		int t = 0;
		for(int i = 0; i <= Math.abs(yF-y); i++) {
			if(mascara.length==t) t=0;
			if(mascara[t]=='1') {
				ponerPixel((int)x, (int)y+i, c);
			}
			t++;
		}
		
	}
	
	public static void inicializarGraficos() {
		try {
		    Thread.sleep(10);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	}
	
}
