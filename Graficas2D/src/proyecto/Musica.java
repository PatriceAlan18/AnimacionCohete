package proyecto;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageTranscoder;

public class Musica {

	
	private BufferedImage buffer, musica, nota1, nota2,nota3,nota4, aux;
	private Graphics g, gAux;
	private int[][] translacion = {{1,0,-1},{0,1,-1},{0,0,1}};
	private int antX = 230, antY = 270;
	private int antX2 = 200, antY2 = 180;
	private int antX3 = 170, antY3 = 90;
	private int antX4 = 140, antY4 = 0;

	private final int inicioX = 230, inicioY = 270;
	
	public Musica() {
		buffer = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		musica = new BufferedImage(500, 330,BufferedImage.TYPE_INT_ARGB );
		aux = new BufferedImage(500, 330,BufferedImage.TYPE_INT_ARGB );
		gAux= (Graphics2D) aux.createGraphics();
		g = (Graphics2D) musica.createGraphics();
		nota1 = new Nota1().getNota();
		nota2 = new Nota2().getNota();
		nota3 = new Nota3().getNota();
		nota4 = new Nota4().getNota();
	}
	
	public void dibujarNota1(int x, int y) {
		aux = new BufferedImage(500, 330,BufferedImage.TYPE_INT_ARGB );
		gAux= (Graphics2D) aux.createGraphics();

		gAux.drawImage(musica, 0, 0,null);
		gAux.drawImage(nota1, x, y,null);
	}
	
	public void dibujarNota2(int x, int y) {
		
		gAux.drawImage(nota2, x, y,null);
	}
	
	public void dibujarNota3(int x, int y) {
		
		gAux.drawImage(nota3, x, y,null);
	}

	public void dibujarNota4(int x, int y) {
	
	gAux.drawImage(nota4, x, y,null);
	}
	
	
	public BufferedImage getMusica() {
		
		dibujarNota1(antX, antY);
		dibujarNota2(antX2, antY2);
		dibujarNota3(antX3, antY3);
		dibujarNota4(antX4, antY4);
		transladarNota1(getRandom(-3, 0),-1);
		if(antY<-45) {
			antY=inicioY;
			antX = inicioX;
		}
		transladarNota2(getRandom(-3, 0),-1);
		if(antY2<-45) {
			antY2=inicioY;
			antX2 = inicioX;

		}
		transladarNota3(getRandom(-3, 0),-1);
		if(antY3<-45) {
			antY3=inicioY;
			antX3 = inicioX;

		}
		transladarNota4(getRandom(-3, 0),-1);
		if(antY4<-45) {
			antY4=inicioY;
			antX4 = inicioX;

		}
		return aux;
	}
	
	
	public void transladarNota1(int dx, int dy) {
		translacion[0][2] = dx;
		translacion[1][2] = dy;
		int [] puntos = {antX,antY,1};
		int[] puntosF = multiplicarMatrices(puntos,translacion);
		antX = puntosF[0];
		antY = puntosF[1];

	}
	
	public void transladarNota2(int dx, int dy) {
		translacion[0][2] = dx;
		translacion[1][2] = dy;
		int [] puntos = {antX2,antY2,1};
		int[] puntosF = multiplicarMatrices(puntos,translacion);
		antX2 = puntosF[0];
		antY2 = puntosF[1];

	}
	
	public void transladarNota3(int dx, int dy) {
		translacion[0][2] = dx;
		translacion[1][2] = dy;
		int [] puntos = {antX3,antY3,1};
		int[] puntosF = multiplicarMatrices(puntos,translacion);
		antX3 = puntosF[0];
		antY3 = puntosF[1];

	}
	
	public void transladarNota4(int dx, int dy) {
		translacion[0][2] = dx;
		translacion[1][2] = dy;
		int [] puntos = {antX4,antY4,1};
		int[] puntosF = multiplicarMatrices(puntos,translacion);
		antX4 = puntosF[0];
		antY4 = puntosF[1];

	}
	
	public int getRandom(int min, int max) {
	    return (int) (Math.random() * (max - min + 1) + min);
	}
	
	
	public int[] multiplicarMatrices(int[] matriz1,int[][] matriz2) {
		int[] nueva = new int[matriz1.length];
		for(int i = 0; i<matriz1.length; i++) {
			for(int j = 0; j<matriz2[i].length; j++) {
				nueva[i]=nueva[i]+matriz1[j]*matriz2[i][j];
			}
		}
		return nueva;
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
	
	public void ponerPixel(int x, int y, Color c) {
		buffer.setRGB(0, 0, c.getRGB());
		g.drawImage(buffer, x, y,null);

	}

}
