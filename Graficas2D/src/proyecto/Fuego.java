package proyecto;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Fuego {

	private  BufferedImage fuego, buffer, fuego2;
	private Graphics2D g, g2;
	private final Color NEGRO = new Color(0,0,1);
	private int bandera = 0;
	
	public Fuego() {
		buffer = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		fuego = new BufferedImage(45,35,BufferedImage.TYPE_INT_ARGB);
		g = (Graphics2D) fuego.createGraphics();
		fuego2 = new BufferedImage(45,35,BufferedImage.TYPE_INT_ARGB);
		dibujarFuego();
		dibujarFuego2();
	}
	
	public void ponerPixel(int x, int y, Color c) {
		buffer.setRGB(0, 0, c.getRGB());
		g.drawImage(buffer, x, y,null);

	}
	
	public void dibujarFuego() {
		dibujarLinea(40,0,20,5,new Color(239,164,35,210));
		dibujarLinea(40,30,20,25,new Color(239,164,35,210));
		dibujarLinea(20,5,0,15,new Color(239,164,35,210));
		dibujarLinea(20,25,0,15,new Color(239,164,35,210));
		dibujarLinea(40,10,20,15,new Color(239,215,35,210));
		dibujarLinea(40,20,20,15,new Color(239,215,35,210));
		dibujarLineaVertical(40,0, 30, new Color(239,164,35,210));
		pintarInundacion(5,15,2, new Color(fuego.getRGB(5,15)),new Color(239,164,35,210));
		pintarInundacion(35,15,2,new Color(fuego.getRGB(0,0)),new Color(239,215,35,210));
		g = (Graphics2D) fuego2.createGraphics();
	}
	
	public void dibujarFuego2() {
		dibujarLinea(40,5,15,10,new Color(239,164,35,210));
		dibujarLinea(40,25,15,20,new Color(239,164,35,210));
		dibujarLinea(15,10,5,15,new Color(239,164,35,210));
		dibujarLinea(15,20,5,15,new Color(239,164,35,210));
		dibujarLinea(40,12,25,15,new Color(239,215,35,210));
		dibujarLinea(40,18,25,15,new Color(239,215,35,210));
		dibujarLineaVertical(40,0, 25, new Color(239,164,35,210));
		pintarInundacion2(38,7,2, new Color(fuego2.getRGB(0,0)),new Color(239,164,35,210));
		pintarInundacion2(35,15,2,new Color(fuego2.getRGB(0,0)),new Color(239,215,35,210));

	}
	
	public BufferedImage getFuego() {
		return fuego;
	}
	
	public BufferedImage getFuego2() {
		return fuego2;
	}
	
	
	public Color degradarColor(Color c, int d){
		int red = c.getRed()-d;
	    int green = c.getGreen()-d;
	    int blue = c.getBlue()-d;
	   return new Color(Math.max(0, red), Math.max(0, green), Math.max(0, blue),255);
	}
	
	public void pintarInundacion(int x, int y, int d, Color c, Color fondo) {
	    ponerPixel(x, y, fondo);
	    if (new Color(fuego.getRGB(x+1,y)).getRGB() == c.getRGB())pintarInundacion(x+1, y, d, c, fondo);
	    if (new Color(fuego.getRGB(x-1,y)).getRGB() == c.getRGB()) pintarInundacion(x-1, y, d, c, fondo);
	    if (new Color(fuego.getRGB(x,y+1)).getRGB() == c.getRGB()) pintarInundacion(x, y + 1, d, c, degradarColor(fondo, d));
	    if (new Color(fuego.getRGB(x, y - 1)).getRGB() == c.getRGB())  pintarInundacion(x,y-1,d, c, degradarColor(fondo, d));
	}
	
	public void pintarInundacion2(int x, int y, int d, Color c, Color fondo) {
	    ponerPixel(x, y, fondo);
	    if (new Color(fuego2.getRGB(x+1,y)).getRGB() == c.getRGB())pintarInundacion2(x+1, y, d, c, fondo);
	    if (new Color(fuego2.getRGB(x-1,y)).getRGB() == c.getRGB()) pintarInundacion2(x-1, y, d, c, fondo);
	    if (new Color(fuego2.getRGB(x,y+1)).getRGB() == c.getRGB()) pintarInundacion2(x, y + 1, d, c, degradarColor(fondo, d));
	    if (new Color(fuego2.getRGB(x, y - 1)).getRGB() == c.getRGB())  pintarInundacion2(x,y-1,d, c, degradarColor(fondo, d));
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
	
}
