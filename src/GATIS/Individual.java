package GATIS;


import java.util.Random;
import java.awt.Color;

import image.Image;

public class Individual implements Comparable<Object>{
	//CONSTANTS
	final int decimalArraySize = 10;
	final float[] interval = {0,45,140,200,256};
	
	//PROPERTIES
	Circle c1;
	Circle c2;
	double score;
	
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
		
		c1 = new Circle(x,y,rr);
		x=(int)(r.nextFloat()*img.getWidth());
		y=(int)(r.nextFloat()*img.getHeight());
		rr=(int)(r.nextFloat()*img.getWidth()*1/2);
		
		//System.out.println("Raio 2 : "+rr);
		c2 = new Circle(x,y,rr);
		//System.out.print("raio no c : "+c2.getRadius());
		fitness(img);

	}
	
	Individual(Circle l , Circle r, Image img){ // constructor using circles as parameters
		c1 = l;
		c2 = r;
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
	
	Circle getCircle(int n){
		if(n == 1)
			return c1;
		else
			return c2;
	}
	//------------------------
	
	void fitness(Image img) {
		//COLORS: 0 15 30 | 45 60 75 90 105 | 120 135 150 165 180 195	| 210 225 240 255
		//WEIGHT:  -100          50                     100                   -100
		
		int bw = -2500;
		int sw = 135;
		int hw = 500;
		int ww = -475;

		int inter = interceptVol(img);
		int interw;		
		if(inter > 50) 
			interw = -1000;
		else
			interw = 100;
		int wt = bw+sw+hw+ww+interw;
		int[] vol = new int[4];
		
		vol = getPixelVol(img);

		

		int b = vol[0]; 
		int s = vol[1];
		int h = vol[2];
		int w = vol[3];
		int total = vol[0]+vol[1]+vol[2]+vol[3];
		
		score = (bw*b+sw*s+hw*h+ww*w+interw*inter)/Math.abs(wt+total);	
		
		
		
	}
	
	Boolean intercept(int x, int y) {
		
		int rad1 = c1.getRadius()*c1.getRadius();
		int cir1 = (x - c1.getX())*(x - c1.getX()) + (y - c1.getY())*(y - c1.getY());
		int rad2 = c2.getRadius()*c2.getRadius();
		int cir2 = (x - c2.getX())*(x - c2.getX()) + (y - c2.getY())*(y - c2.getY());
		
		if(cir1 <= rad1 && cir2 <=rad2)
			return true;
		else
			return false;
		
	}
	
	int interceptVol(Image img) {
			
		int sum = 0 ;
		
		for(int i = 0 ; i < img.getHeight(); i++) {  //using the "for" structures to run by the image as 
													//"i" and "j" being the pixel coordinates.
			for(int j = 0 ; j < img.getWidth(); j++) {
				
				if(intercept(j,i))					//verify if the coordinates are intercepted inside the two circles 
					sum++;
			}
		}
		return sum;
	}
	
	int[] getPixelVol(Image img) {
		
		int[] sum= new int[4];
		double pix;
		for(int i = 0; i < img.getHeight()-1; i++) {        //using the "for" structures to run by the image as 
															//"i" and "j" being the pixel coordinates.
			for(int j = 0 ; j < img.getWidth()-1; j++) {
				if(contains(j,i)) {									//verify if the coordinates are inside one of the circles 
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
  
	Boolean contains(int x, int y) {
		
		int rad1 = c1.getRadius()*c1.getRadius();									   // calculate the r^2 from the circle (limit of the circle)
		int cir1 = (x - c1.getX())*(x - c1.getX()) + (y - c1.getY())*(y - c1.getY());  // calculate of the center to the coordinates 
		int rad2 = c2.getRadius()*c2.getRadius();
		int cir2 = (x - c2.getX())*(x - c2.getX()) + (y - c2.getY())*(y - c2.getY());
		
		
		if(cir1<=rad1 || cir2 <= rad2)	// if the distance of the coordinates to the the center is smaller or equal they are 
			return true;				// inside one of the circles, if the distance are greater
		else
			return false;
	}
	
	Boolean containsL(int x, int y) {
		
		int rad1 = c1.getRadius()*c1.getRadius();									   // calculate the r^2 from the circle (limit of the circle)
		int cir1 = (x - c1.getX())*(x - c1.getX()) + (y - c1.getY())*(y - c1.getY());  // calculate of the center to the coordinates 		
		
		if(cir1<=rad1)	// if the distance of the coordinates to the the center is smaller or equal they are 
			return true;				// inside one of the circles, if the distance are greater
		else
			return false;
	}
	
	Boolean containsR(int x, int y) {
		
		int rad2 = c2.getRadius()*c2.getRadius();
		int cir2 = (x - c2.getX())*(x - c2.getX()) + (y - c2.getY())*(y - c2.getY());
		
		
		if(cir2 <= rad2)	// if the distance of the coordinates to the the center is smaller or equal they are 
			return true;				// inside one of the circles, if the distance are greater
		else
			return false;
	}
		
	void draw(Image img ) {
		
		int h = img.getHeight();
		int w = img.getWidth();
		
		for(int i = 0 ; i < h-1; i++) {
			for(int j = 0 ; j < w-1 ; j++) {
				if(this.containsL(j,i))
					img.setPixel(j,i,RED);
				if(this.containsR(j,i))
					img.setPixel(j,i,GREEN);
			}
		}
	}
	
	public static void main(String[]args) throws Exception{
		
		Image img = new Image("C:/Users/Lucas C Mendes/Documents/JAVA/GATIS/src/GATIS/img2.jpg");
		Image test = new Image(img);

		Individual a = new Individual(img);
		a.draw(test);
		a.fitness(img);
		
		//a.lessQual(test);
		
		test.exportImage("C:/Users/Lucas C Mendes/Documents/JAVA/GATIS/src/GATIS/asd.jpg", "jpg");
		
		
	}
}
