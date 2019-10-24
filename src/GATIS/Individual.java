package GATIS;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import image.Image;

public class Individual implements Comparable{
	//CONSTANTS
	final int decimalArraySize = 10;
	
	//PROPERTIES
	Circle c1;
	Circle c2;
	float score;
	
	//UTILITIES
	Random r = new Random();
	
	
	
	//METHODS
	Individual(Image img){
		c1 = new Circle((int)(r.nextFloat()*img.getWidth()),(int)(r.nextFloat()*img.getHeight()),(int)(r.nextFloat()*img.getWidth()*1/2));
		c2 = new Circle((int)(r.nextFloat()*img.getWidth()),(int)(r.nextFloat()*img.getHeight()),(int)(r.nextFloat()*img.getWidth()*1/2));
	}
	Individual(Circle l , Circle r){
		c1 = l;
		c2 = r;
		//COLOCAR CALCULO DO SCORE
	}
	
	public float getScore() {
		return score;
	}
	
	Circle getCircle(int n){
		if(n == 1)
			return c1;
		else
			return c2;
	}
	
	void fitness() {
		
	}
	
	@Override
	public int compareTo(Object o) {
		return (this.getScore() < ((Individual) o).getScore() ? -1 : (this.getScore() == ((Individual) o).getScore() ? 0 : 1));
	}  

	Boolean contains(int x, int y) {
		
		int rad1 = c1.getRadius()*c1.getRadius();
		int cir1 = (x - c1.getX())*(x - c1.getX()) + (y - c1.getY())*(y - c1.getY());
		int rad2 = c2.getRadius()*c2.getRadius();
		int cir2 = (x - c2.getX())*(x - c2.getX()) + (y - c2.getY())*(y - c2.getY());
		
		
		if(cir1<=rad1 || cir2 <= rad2)
			return true;
		else
			return false;
	}
	
	void lessQual(Image img) {
		

		
		int h = img.getHeight();
		int w = img.getWidth();
		int qntInter = 17; //1,3,5,17, 51, 85 
		
		ArrayList<Integer> interval = new ArrayList<Integer>();
		for(int i = 0 ; i < qntInter ; i++) {
			interval.add(277/qntInter*i);
		}
		for(int i = 0 ; i < h-1; i++) {
			for(int j = 0 ; j < w-1 ; j++) {
				for(int z = 0 ; z < qntInter-1 ; z++) {
					if(img.getPixel(j, i)>=interval.get(z) && (img.getPixel(j, i) <= interval.get(z+1))) {
						img.setPixelAllBands(j, i, interval.get(z));
					}						
				}					
			}
		}
		
	}
	

	
	void draw(Image img) {
		
		int h = img.getHeight();
		int w = img.getWidth();
		
		for(int i = 0 ; i < h-1; i++) {
			for(int j = 0 ; j < w-1 ; j++) {
				if(this.contains(j,i))
					img.setPixelAllBands(j, i, 255);
			}
		}
	}
	
	public static void main(String[]args) throws Exception{
		
		Image img = new Image("C:/Users/Lucas C Mendes/Documents/JAVA/GATIS/src/GATIS/img1.jpg");
		Image test = new Image(img);

		Individual a = new Individual(img);
		a.draw(test);
		
		//a.lessQual(test);
		
		test.exportImage("C:/Users/Lucas C Mendes/Documents/JAVA/GATIS/src/GATIS/asd.jpg", "jpg");
		
		
	}
}
