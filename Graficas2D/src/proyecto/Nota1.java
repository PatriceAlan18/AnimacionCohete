package proyecto;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Nota1 {

	private BufferedImage buffer, nota1;
	private Graphics g;
	private Color borde = new Color(255,0,0);
	private Color rojo = new Color( 255, 0, 0);
	
	public Nota1() {
		int R = 3;
		int x=10;
		int y = 40;
		buffer = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		nota1 = new BufferedImage(50, 50,BufferedImage.TYPE_INT_ARGB );
		g = (Graphics2D) nota1.createGraphics();
		
		dibujarCircunferencia(x+R, y+R, R, 165, 455, borde);
		dibujarCircunferencia(x+R+12, y+R-3, R, 165, 455, borde);
		
		dibujarLineaVertical(x+R+R, y-R, y+R, borde);
		dibujarLineaVertical(x+R, y, y-R-R, borde);
		
		dibujarLineaVertical(x+R+12, y-3, y-R-3, borde);
		dibujarLineaVertical(x+R+R+12, y+R-3, y-R-R-3, borde);
		
		dibujarLinea(x+R, y-R-R, x+R+R+12, y-R-R-3, borde);
		dibujarLinea(x+R+R, y-R, x+R+12, y-R-3, borde);
		
		pintarInundacion(x+R, y+R, 1, new Color(nota1.getRGB(x+R, y+R)), rojo);
	}
	
	public BufferedImage getNota() {
		return nota1;
	}
	
	public void dibujarLinea(int x0, int y0, int x1, int y1, Color c) {
		int dy = y1-y0;
		int dx = x1-x0;
		if(dx==0) {
			dibujarLineaVertical(x0,y0,y1,c);
			return;
		}
		
		if(dy==0) {
			dibujarLineaHorizontal(x0,x1,y0,c);
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
				ponerPixel(x, y, c);
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
		ponerPixel(x, y, c);

	}

	private void dibujarLineaVertical(int x, int y, int yF, Color c) {
		if(yF<y) {
			int aux = y;
			y=yF;
			yF=aux;
		}
		for(int i = 0; i <= Math.abs(yF-y); i++)ponerPixel(x, y+i, c);
	}
	
	private void dibujarLineaHorizontal(int x, int xF, int y, Color c) {
		if(xF<x) {
			int aux = x;
			x=xF;
			xF=aux;
		}
		for(int i = 0; i <= Math.abs(xF-x); i++)  ponerPixel(x+i, y, c);			
	}
	
	public void dibujarCircunferencia(int x0, int y0, int R,int gradosInicio, int gradosFin, Color c) {
		int x=0, y=0;
		double aumento = (double)40/(double)R;
		for(double t = gradosInicio; t <gradosFin; t+=aumento) {
			x = (int) Math.round(x0+(R*Math.sin(Math.toRadians(t))));
			y = (int) Math.round(y0+(R*Math.cos(Math.toRadians(t))));
			ponerPixel(x, y, c);
		}		
	}
	
	public Color degradarColor(Color c, int d){
		int red = c.getRed()-d;
	    int green = c.getGreen()-d;
	    int blue = c.getBlue()-d;
	   return new Color(Math.max(0, red), Math.max(0, green), Math.max(0, blue),255);
	}
	
	public void pintarInundacion(int x, int y, int d, Color c, Color fondo) {
	    ponerPixel(x, y, fondo);
	    if (new Color(nota1.getRGB(x+1,y)).getRGB() == c.getRGB())pintarInundacion(x+1, y, d, c, fondo);
	    if (new Color(nota1.getRGB(x-1,y)).getRGB() == c.getRGB()) pintarInundacion(x-1, y, d, c, fondo);
	    if (new Color(nota1.getRGB(x,y+1)).getRGB() == c.getRGB()) pintarInundacion(x, y + 1, d, c, degradarColor(fondo, d));
	    if (new Color(nota1.getRGB(x, y - 1)).getRGB() == c.getRGB())  pintarInundacion(x,y-1,d, c, degradarColor(fondo, d));
	}
	
	public void ponerPixel(int x, int y, Color c) {
		buffer.setRGB(0, 0, c.getRGB());
		g.drawImage(buffer, x, y,null);

	}
}
