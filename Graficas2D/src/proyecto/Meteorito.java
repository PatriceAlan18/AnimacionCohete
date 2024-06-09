package proyecto;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Meteorito {
	
	private BufferedImage buffer, meteoro, aux;
	private BufferedImage[] meteoros;
	private Graphics[] graficos;
	private Graphics  gAux;
	private final Color CAFE = new Color(94,73,72);
	private final Color NEGRO = new Color(0,0,1);
	private int[] radio,  y, x, v;


	public Meteorito()
	{
		buffer = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		meteoro = new BufferedImage(Principal.Width,Principal.Height,BufferedImage.TYPE_INT_ARGB);
		aux = new BufferedImage(Principal.Width,Principal.Height,BufferedImage.TYPE_INT_ARGB);
		gAux = (Graphics2D) aux.createGraphics();
		crear();
		
	}
	
	public void crear() {
		radio = new int[getRandom(24, 30)];
		meteoros = new BufferedImage[radio.length];
		graficos = new Graphics[radio.length];
		y = new int[radio.length];
		x = new int[radio.length];
		v = new int[radio.length];
		for(int i = 0; i<radio.length; i++) {
			radio[i]=getRandom(4, 15);
			y[i] = getRandom(10, Principal.Height-10);
			v[i] = getRandom(3,8);
			x[i] = getRandom(0, Principal.Width+200);
			meteoros[i] = new BufferedImage(44,44,BufferedImage.TYPE_INT_ARGB);
			graficos[i] = meteoros[i].createGraphics();
			dibujarCircunferencia(22, 22, radio[i],0, 360, NEGRO,graficos[i]);
			pintar(22, 22, getRandom(3, 5), new Color(meteoros[i].getRGB(22,22)), CAFE, meteoros[i], graficos[i]);
		}
	}
	
	public Color degradarColor(Color c, int d){
		int red = c.getRed()-d;
	    int green = c.getGreen()-d;
	    int blue = c.getBlue()-d;
	    int alpha = c.getAlpha();
	   return new Color(Math.max(0, red), Math.max(0, green), Math.max(0, blue), Math.max(0, alpha));
	}
	
	public void ponerPixel(int x, int y, Color c, Graphics g2) {
		buffer.setRGB(0, 0, c.getRGB());
		g2.drawImage(buffer, x, y,null);

	}
	
	public void dibujarMeteoros() {
		gAux.drawImage(meteoro, 0, 0,null);
		for(int i = 0; i<radio.length; i++) {
			gAux.drawImage(meteoros[i], x[i], y[i],null);
		}
		
	}
	
	public void mover() {
		for(int i = 0; i<radio.length; i++) {
			x[i]=x[i]-v[i];
			if(x[i]<-30) {
				radio[i]=getRandom(4, 15);
				y[i] = getRandom(10, Principal.Height-10);
				v[i] = getRandom(3,8);
				x[i] = getRandom(Principal.Width+50, Principal.Width+200);
				meteoros[i] = new BufferedImage(44,44,BufferedImage.TYPE_INT_ARGB);
				graficos[i] = meteoros[i].createGraphics();
				dibujarCircunferencia(22, 22, radio[i],0, 360, NEGRO,graficos[i]);
				pintar(22, 22,getRandom(3 ,5), new Color(meteoros[i].getRGB(22,22)), CAFE, meteoros[i], graficos[i]);

			}
		}
	}

	
	public void pintar(int x, int y, int d, Color c, Color fondo, BufferedImage img, Graphics g2) {
		ponerPixel(x, y, fondo,g2);
		fondo = new Color(fondo.getRed(), fondo.getGreen(), fondo.getBlue(), getRandom(180, 255));
	    if (new Color(img.getRGB(x+1,y)).getRGB() == c.getRGB())pintar(x+1, y, d, c, fondo, img,g2);
	    if (new Color(img.getRGB(x-1,y)).getRGB() == c.getRGB()) pintar(x-1, y, d, c, fondo,img,g2);
	    if (new Color(img.getRGB(x,y+1)).getRGB() == c.getRGB()) pintar(x, y + 1, d, c, degradarColor(fondo, d),img,g2);
	    if (new Color(img.getRGB(x, y - 1)).getRGB() == c.getRGB())  pintar(x,y-1,d, c, degradarColor(fondo, d),img,g2);
	}
	

	public BufferedImage getMeteoro() {
		aux = new BufferedImage(Principal.Width,Principal.Height,BufferedImage.TYPE_INT_ARGB);
		gAux = (Graphics) aux.createGraphics();
		mover();
		dibujarMeteoros();
		return aux;
	}

	public void dibujarCircunferencia(int x0, int y0, int R,int gradosInicio, int gradosFin, Color c, Graphics g2) {
		int x=0, y=0;
		double aumento = (double)40/(double)R;
		for(double t = gradosInicio; t <gradosFin; t+=aumento) {
			x = (int) Math.round(x0+(R*Math.sin(Math.toRadians(t))));
			y = (int) Math.round(y0+(R*Math.cos(Math.toRadians(t))));
			ponerPixel(x, y, c,g2);
		}		
	}
	
	public int getRandom(int min, int max) {
	    return (int) (Math.random() * (max - min + 1) + min);
	}
	
}
