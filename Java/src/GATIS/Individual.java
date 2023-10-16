package GATIS;


import java.util.Random;
import java.awt.Color;

import image.Image;

public class Individual implements Comparable<Object>{
	//CONSTANTS
	final int decimalArraySize = 10;
	final float[] interval = {0,45,140,200,256};
	
	//PROPERTIES
	Cardioid c1;
	double score;
	int[] vol = new int[4];
	
	//UTILITIES
	Random r = new Random();
	Color RED = new Color(255, 0, 0);
	Color GREEN = new Color(0, 255, 0);
	
	//METHODS
	
	Individual(Image img){
		
		int x,y,rr;
		
		x=(int)(r.nextFloat()*img.getWidth());
		y=(int)(r.nextFloat()*img.getHeight());
		rr=(int)(r.nextFloat()*img.getWidth()*1/2);
		//System.out.println("Raio 1 : "+rr);
		
		c1 = new Cardioid(x,y,rr);
		
		fitness(img);

	}
	
	Individual(Cardioid l , Image img){ // constructor using Cardioids as parameters
		c1 = l;
		fitness(img);
	}
	
	@Override
	public int compareTo(Object o) { // method compare, made to help sort the individuals grups
		return (this.getScore() < ((Individual) o).getScore() ? -1 : (this.getScore() == ((Individual) o).getScore() ? 0 : 1));
	}
	 
	// geters-----------------
	public double getScore() { 
		return score;
	}
	
	Cardioid getCardioid(){
			return c1;
	}
	//------------------------
	
	void fitness(Image img) {
		//COLORS: 0 15 30 | 45 60 75 90 105 | 120 135 150 165 180 195	| 210 225 240 255
				//WEIGHT-C:  -100          50                     100                   -100
				//WEIGHT-R:
				int bw = -250;
				int sw = 50;
				int hw = 250;
				int ww = -275;
				int wt = bw+sw+hw+ww;
					
				int[] vol = new int[4];
				
				vol = getPixelVol(img);
				this.vol = vol;

				int b = vol[0]; //A
				int s = vol[1]; //B
				int h = vol[2]; //C
				int w = vol[3]; //D
							
				
				double totalC = (bw*b+sw*s+hw*h+ww*w);
				
				//score = (totalC)/Math.abs(wt);
				score = totalC/Math.abs(wt*10000);
		
	}
	
			
	int[] getPixelVol(Image img) {
		
		int[] sum= new int[4];
		double pix;
		for(int i = 1; i < img.getHeight(); i++) {        //using the "for" structures to run by the image as 
															//"i" and "j" being the pixel coordinates.
			for(int j = 1 ; j < img.getWidth(); j++) {
				if(contains(j,i)) {									//verify if the coordinates are inside one of the Cardioids 
					pix = img.getPixel(j, i);
					if(pix >= interval[0] && pix < interval[1]) {
						sum[0]++;
					}else if (pix >= interval[1] && pix < interval[2]) {
						sum[1]++;
					}else if(pix >= interval[2] &&  pix < interval[3]) {
						sum[2]++;
					}else {
						sum[3]++;
					}
				}
			}
		}
		
		return sum;
	}
	
 
	// ESSE CONTAINS É PRO PIXELVOL
	Boolean contains(int x, int y) {
		double t;
		if((x - c1.getX()) == 0)
			t = 0;
		else		
			t = Math.atan2((c1.getY() - y) , (x - c1.getX()));
		
		double rC =  c1.getSize() * (1 + Math.sin(t));
		
		double rad = MyMath.EuclideanDist(x, c1.getX(), c1.getY(), y);
		
		
		if(rad <= rC) 			
			return true;				
		else
			return false;
	}
	//ESSE CONTAINS É PRO DESENHO
		
	Boolean containsL(int x, int y) {
		
		double t;
		int line = 5;
		
		if((x - c1.getX()) == 0)
			t = 0;
		else		
			t = Math.atan2((c1.getY() - y) , (x - c1.getX()));
		
		double rC =  c1.getSize() * (1 + Math.sin(t));
		
		double rad = MyMath.EuclideanDist(x, c1.getX(), c1.getY(), y);
		
		
		if(rad <= rC && rad>= rC-line)	
			return true;				
		else
			return false;
	}

//ADAPTAR O DRAW A CARDIODE
	void draw(Image img ) {
		
		int h = img.getHeight();
		int w = img.getWidth();
		int x,y,r;
		for(int i = 0 ; i < h-1; i++) {
			for(int j = 0 ; j < w-1 ; j++) {
				if(this.containsL(j,i))
					img.setPixel(j,i,GREEN);
			}
		}/*
		for(float i=0; i <= Math.PI*2 ; i+=0.001){
			
			r = (int) (this.getCardioid().getSize() * (1 - Math.sin(i)));
			x = (int) (r * (Math.cos(i)* Math.cos(i)* Math.cos(i)) + this.getCardioid().getX());
			y = (int) (r * Math.sin(i) + this.getCardioid().getY()) ;
			
			
			
			for(int j = 0 ; j < 4 ; j++) {
				if((x+j)<640 && (y+j)<480 && (x-j)>0 && (y-j)>0 && contains(x,y)) {
					img.setPixel(x+j, y+j, GREEN);
					img.setPixel(x-j, y-j, GREEN);
				}
			}
		}*/
	}
	
	void setX(int[]a) {
		this.getCardioid().setX(a);
	}
	
	void setY(int[]a) {
		this.getCardioid().setY(a);
	}
	
	void setS(int[]a) {
		this.getCardioid().setS(a);;
	}
	public static void main(String[]args) throws Exception{
		
		Image img = new Image("C:/Users/Lucas C Mendes/Documents/JAVA/GATIS/src/GATIS/img2.jpg");
		Image test = new Image(img);
		img.convertToRGB();
		
		Individual a = new Individual(img);
		a.getCardioid().setPosition(200, 300);
		a.getCardioid().setSize(1);
		a.draw(test);
		
		test.exportImage("C:/Users/Lucas C Mendes/Documents/JAVA/GATIS/src/GATIS/asd.jpg", "jpg");
		
		
	}
}
