package proyecto;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Estrella{
	
	private BufferedImage buffer;
	private Graphics graPixel;
	private int x;
	private int y;
	private final Color EXTERIOR = new Color(124,122,121);
	private final Color CENTRO = new Color(218,215,213);
	private final Color UNION = new Color(179,175,174);
	private final static Color NEGRO = new Color(0,0,1);
	
	public Estrella(int CentroX, int CentroY) {
		this.x = CentroX;
		this.y = CentroY;
		buffer = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		graPixel = (Graphics2D) buffer.createGraphics();
	}
	
	
	public void dibujarEstrella(Graphics g) {
		ponerPixel(g, x, y, CENTRO);
		ponerPixel(g, x+1, y, UNION);
		ponerPixel(g, x-1, y, UNION);
		ponerPixel(g, x, y+1, UNION);
		ponerPixel(g, x, y-1, UNION);
		ponerPixel(g, x+1, y+1, EXTERIOR);
		ponerPixel(g, x-1, y+1, EXTERIOR);
		ponerPixel(g, x+1, y-1, EXTERIOR);
		ponerPixel(g, x-1, y-1, EXTERIOR);
		ponerPixel(g, x+2, y, EXTERIOR);
		ponerPixel(g, x-2, y, EXTERIOR);
		ponerPixel(g, x, y+2, EXTERIOR);
		ponerPixel(g, x, y-2, EXTERIOR);
	}
	
	public void titilar(Graphics g) {
		ponerPixel(g, x+3, y, EXTERIOR);
		ponerPixel(g, x-3, y, EXTERIOR);
		ponerPixel(g, x, y+3, EXTERIOR);
		ponerPixel(g, x, y-3, EXTERIOR);
		ponerPixel(g, x+4, y, EXTERIOR);
		ponerPixel(g, x-4, y, EXTERIOR);
		ponerPixel(g, x, y+4, EXTERIOR);
		ponerPixel(g, x, y-4, EXTERIOR);
	}
	
	public void dejarTitilar(Graphics g) {
		ponerPixel(g, x+3, y, NEGRO);
		ponerPixel(g, x-3, y, NEGRO);
		ponerPixel(g, x, y+3, NEGRO);
		ponerPixel(g, x, y-3, NEGRO);
		ponerPixel(g, x+4, y, NEGRO);
		ponerPixel(g, x-4, y, NEGRO);
		ponerPixel(g, x, y+4, NEGRO);
		ponerPixel(g, x, y-4, NEGRO);
	}
	
	public void ponerPixel(Graphics g, int x, int y, Color c) {
		buffer.setRGB(0, 0, c.getRGB());
		g.drawImage(buffer, x, y,null);

	}
	
}
