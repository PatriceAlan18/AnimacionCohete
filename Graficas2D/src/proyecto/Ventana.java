package proyecto;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Ventana {

	private BufferedImage buffer, ventana;
	private Graphics g;
	private final Color NEGRO = new Color(0,0,1);

	public Ventana(int R, int grosor) {
		buffer = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		ventana = new BufferedImage((R*2)+6, (R*2)+6,BufferedImage.TYPE_INT_ARGB );
		g = (Graphics2D) ventana.createGraphics();
		dibujarCircunferenciaGrosor(R+3, R+3, R, grosor, NEGRO);
		pintarInundacion(R+3, R+3, new Color(ventana.getRGB(R+4,R+4)), new Color(146,150,154));
	}
	
	public void pintarInundacion(int x, int y, Color c, Color fondo) {
	    ponerPixel(x, y, fondo);
	    if (new Color(ventana.getRGB(x+1,y)).getRGB() == c.getRGB())pintarInundacion(x+1, y, c, fondo);
	    if (new Color(ventana.getRGB(x-1,y)).getRGB() == c.getRGB()) pintarInundacion(x-1, y, c, fondo);
	    if (new Color(ventana.getRGB(x,y+1)).getRGB() == c.getRGB()) pintarInundacion(x, y + 1, c, fondo);
	    if (new Color(ventana.getRGB(x, y - 1)).getRGB() == c.getRGB())  pintarInundacion(x,y-1, c, fondo);
	}
	
	public BufferedImage getVentana() {
		return ventana;
	}
	
	public void ponerPixel(int x, int y, Color c) {
		buffer.setRGB(0, 0, c.getRGB());
		g.drawImage(buffer, x, y,null);

	}
	
	public void dibujarCircunferenciaGrosor(int x0, int y0, int R,int Grosor, Color c) {

		double pk = 3-2*R;
		int y = R;
		int x = 0;
		int i =0;
		int mitad = Grosor/2;
		int mitadFinal = mitad;
		if(Grosor%2==1)mitadFinal++;
			dibujarRectangulo(x0+y-mitad, y0-mitad, x0+y+mitadFinal, y0+mitadFinal, c);
			dibujarRectangulo(x0-mitad,y0+y-mitad, x0+mitadFinal, y0+y+mitadFinal,c);
			dibujarRectangulo(x0-mitad, y0-y-mitad, x0+mitadFinal, y0-y+mitadFinal,c);
			dibujarRectangulo(x0-y-mitad,y0-mitad,x0-y+mitadFinal, y0+mitadFinal,c);

		while(x<=y) {
			if(pk<=0) {
				pk = pk + 4*x + 6;
			}
			else {
				pk= pk + 4*(x-y) + 10;
				y--;
			}
			x++;
			dibujarOctantesCuadrados(x,y, x0, y0, mitad, mitadFinal,c);

		}
		
			
	}
	
	private void dibujarOctantesCuadrados(int x, int y, int x0, int y0, int mitad, int mitadFinal,Color c) {
		
			dibujarRectangulo(x0+x-mitad,y0+y-mitad,x0+x+mitadFinal, y0+y+mitadFinal,c);
			dibujarRectangulo(x0+y-mitad,y0-x-mitad,x0+y+mitadFinal,y0-x+mitadFinal,c);
			dibujarRectangulo(x0+x-mitad,y0-y-mitad,x0+x+mitadFinal,y0-y+mitadFinal,c);
			dibujarRectangulo(x0-y-mitad,y0-x-mitad,x0-y+mitadFinal,y0-x+mitadFinal,c);
			dibujarRectangulo(x0-x-mitad,y0-y-mitad,x0-x+mitadFinal,y0-y+mitadFinal,c);
			dibujarRectangulo(x0+y-mitad,y0+x-mitad,x0+y+mitadFinal,y0+x+mitadFinal,c);
			dibujarRectangulo(x0-y-mitad,y0+x-mitad,x0-y+mitadFinal,y0+x+mitadFinal,c);
			dibujarRectangulo(x0-x-mitad,y0+y-mitad,x0-x+mitadFinal,y0+y+mitadFinal,c);		
	}
	
	public void dibujarCircunferencia(int x0, int y0, int R, Color c) {
		double pk = 3-2*R;
		int y = R;
		int x = 0;
		dibujarOctantes(x,y, x0, y0,c);
		while(x<=y) {
			if(pk<=0) {
				pk = pk + 4*x + 6;
			}
			else {
				pk= pk + 4*(x-y) + 10;
				y--;
			}
			x++;
			dibujarOctantes(x,y, x0, y0,c);
			
		}
	}
	
	public void dibujarOctantes(int x, int y, int x0, int y0, Color c) {
		ponerPixel(x0+x,y0+y,c);
		ponerPixel(x0+y,y0-x,c);
		ponerPixel(x0+x,y0-y,c);
		ponerPixel(x0-y,y0-x,c);
		ponerPixel(x0-x,y0-y,c);
		ponerPixel(x0+y,y0+x,c);
		ponerPixel(x0-y,y0+x,c);
		ponerPixel(x0-x,y0+y,c);

	}
	
	public void dibujarRectangulo(int x0, int y0, int x1, int y1, Color c) {
		dibujarLineaVertical(x0,y0,y1,c);
		dibujarLineaHorizontal(x0,x1,y1,c);
		dibujarLineaVertical(x1,y0,y1,c);
		dibujarLineaHorizontal(x0,x1,y0,c);
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
