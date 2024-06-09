package graficas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Inundacion18 extends JFrame implements java.awt.event.MouseListener{

	private BufferedImage buffer, pantalla;
	private Graphics graPixel, graPantalla;
	private Actualizador hilo;
	private final static Color NEGRO = new Color(0,0,1);
	
	public Inundacion18() {
		this.setResizable(false);
		this.setVisible(true);
		this.setTitle("Inundacion");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(450,100,500,500);
		pantalla = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		graPantalla = (Graphics2D)pantalla.createGraphics();
		buffer = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		graPixel = (Graphics2D) buffer.createGraphics();
		inicializarGraficos();
		hilo = new Actualizador();
		hilo.start();
	}
	
	public static void main(String[] args) {
		Inundacion18 ventana = new Inundacion18();
		
		ventana.dibujarLinea(100,100,200,100,1,NEGRO);
		ventana.dibujarLinea(100, 100, 70, 200, 1, NEGRO);
		ventana.dibujarLinea(70, 200, 130,200,1,NEGRO);
		ventana.dibujarLinea(130,200,150,160,1,NEGRO);
		ventana.dibujarLinea(150,160,200,300,1,NEGRO);
		ventana.dibujarLinea(200,300,200,100,1,NEGRO);
		ventana.addMouseListener(ventana);
		ventana.finalizar();
	}
	
	public void finalizar() {
		this.getGraphics().drawImage(pantalla,0,0,null);
	}
	
	//Evitar desbordamiento
	//  -Xss4m
	
	public void pintar(int x, int y, Color c, Color fondo) {
		ponerPixel(x,y,fondo);
		
		if((new Color(pantalla.getRGB(x+1, y)).getRGB()==c.getRGB() || new Color(pantalla.getRGB(x, y+1)).getRGB()==c.getRGB()) && 
				new Color(pantalla.getRGB(x+1, y+1)).getRGB()==c.getRGB())pintar(x+1, y+1, c,fondo);		
		if((new Color(pantalla.getRGB(x+1, y)).getRGB()==c.getRGB() || new Color(pantalla.getRGB(x, y-1)).getRGB()==c.getRGB()) && 
				new Color(pantalla.getRGB(x+1, y-1)).getRGB()==c.getRGB())pintar(x+1, y-1, c,fondo);		
		if((new Color(pantalla.getRGB(x-1, y)).getRGB()==c.getRGB() || new Color(pantalla.getRGB(x, y+1)).getRGB()==c.getRGB()) && 
				new Color(pantalla.getRGB(x-1, y+1)).getRGB()==c.getRGB())pintar(x-1, y+1, c,fondo);
		if((new Color(pantalla.getRGB(x-1, y)).getRGB()==c.getRGB() || new Color(pantalla.getRGB(x, y-1)).getRGB()==c.getRGB()) && 
				new Color(pantalla.getRGB(x-1, y-1)).getRGB()==c.getRGB())pintar(x-1, y-1, c,fondo);
		if(new Color(pantalla.getRGB(x+1, y)).getRGB()==c.getRGB())pintar(x+1,y,c,fondo) ;
		if(new Color(pantalla.getRGB(x, y+1)).getRGB()==c.getRGB())pintar(x,y+1,c,fondo) ;			
		if(new Color(pantalla.getRGB(x-1, y)).getRGB()==c.getRGB())pintar(x-1,y,c,fondo) ;
		if(new Color(pantalla.getRGB(x, y-1)).getRGB()==c.getRGB())pintar(x,y-1,c,fondo) ;
	}
	
	public void dibujarLinea(int x0, int y0, int x1, int y1, int G, Color c) {
		int dy = y1-y0;
		int dx = x1-x0;
		int grosorH = 0, grosorV=0;
		if((double)dy/(double)dx>1)grosorH=G;
		else grosorV=G;

		if(dx==0) {
			dibujarLineaVertical(x0,y0,y1, grosorV, grosorH,c);
			return;
		}
		
		if(dy==0) {
			dibujarLineaHorizontal(x0,x1,y0,grosorV,grosorH,c);
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
				dibujarGrosor(x,y,grosorV, grosorH, c);
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
		dibujarGrosor(x,y,grosorV, grosorH, c);
	}
	
	private void dibujarGrosor(int x, int y, int GV, int GH, Color c) {
		if(GV>0)y = y - GV/2;
		else x = x-GH/2;
		for(int i = 0; i<GV; i++)ponerPixel(x,y+i, c);
		for(int i = 0; i<GH; i++)ponerPixel(x+i,y, c);
	}

	private void dibujarLineaVertical(int x, int y, int yF, int GV, int GH, Color c) {
		if(yF<y) {
			int aux = y;
			y=yF;
			yF=aux;
		}
		for(int i = 0; i <= Math.abs(yF-y); i++)dibujarGrosor(x,y+i,GV, GH, c);
	}
	
	private void dibujarLineaHorizontal(int x, int xF, int y, int GV, int GH, Color c) {
		if(xF<x) {
			int aux = x;
			x=xF;
			xF=aux;
		}
		for(int i = 0; i <= Math.abs(xF-x); i++)  dibujarGrosor(x+i,y,GV, GH, c);			
	}

	public static void inicializarGraficos() {
		try {
		    Thread.sleep(40);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	}
	
	public void ponerPixel(int x, int y, Color c) {
		buffer.setRGB(0, 0, c.getRGB());
		graPantalla.drawImage(buffer, x, y,this);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Color fondo = new Color(pantalla.getRGB(e.getX(), e.getY()));

		pintar(e.getX(),e.getY(),fondo, Color.blue);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private class Actualizador extends Thread{
		public void run() {
			while(true) {
				try {
					sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finalizar();
			}
		}
	}
	
}


