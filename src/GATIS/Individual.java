package GATIS;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import image.Image;

public class Individual implements Comparable{
	//PROPERTIES
	float cX;
	float cY;
	float radius;
	float score;
	//UTILITIES
	Random r = new Random();
	
	Individual(){
		
	}
	
	public float getScore() {
		return score;
	}
	
	void fitness() {
		
	}
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return (this.getScore() < ((Individual) o).getScore() ? -1 : (this.getScore() == ((Individual) o).getScore() ? 0 : 1));
	}  

	void generateCircle(int x, int y) {
		cX = r.nextFloat()*x;
		cY = r.nextFloat()*y;
		radius = r.nextFloat()*x/2;//porque o y sempre é menor 
	}

	//(X - cX)^2 + (Y - cY)^2 < r^2
	Boolean contains(int x, int y) {
		
		double rad = radius*radius;
		double cir = (x-cX)*(x-cX) + (y-cY)*(y-cY);
		
		if(cir<=rad)
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
		generateCircle(w,h);
		
		for(int i = 0 ; i < h-1; i++) {
			for(int j = 0 ; j < w-1 ; j++) {
				if(contains(j,i))
					img.setPixelAllBands(j, i, 255);
				//else
				//	img.setPixelAllBands(j, i, 0);
			}
		}
	}
	
	public static void main(String[]args) throws Exception{
		
		Image img = new Image("C:/Users/Lucas C Mendes/Documents/JAVA/GATIS/src/GATIS/img1.jpg");
		Image test = new Image(img);

		Individual a = new Individual();
		a.draw(test);
		//a.lessQual(test);
		
		test.exportImage("C:/Users/Lucas C Mendes/Documents/JAVA/GATIS/src/GATIS/asd.jpg", "jpg");
		
		
	}
}
