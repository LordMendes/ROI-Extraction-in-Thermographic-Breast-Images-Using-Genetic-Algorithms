package GATIS;

import image.Image;

public class Cardioid {

	int radius;
	int x;
	int y;
	int tetha;
	int a;

	
	
	Cardioid(){
		
		x = 330;
		y = 220;
		a = 2;
		
	}
	
	//(x^2+y^2-ax)^2 = a^2(x^2+y^2)
	//p = a(1+sin(tetha))
	Boolean contains(int x ,int y) {
		int e = (this.x - x)^2+(this.y - y)^2;
		int d = 2*a*((this.x-x)^2 + (this.y-y)^2)^(1/2)-4*a*(this.x-x);
		
		if (e>=d)
			return true;
		else
			return false;	
		
	}
	void draw(Image img) {
		
		int h = img.getHeight();
		int w = img.getWidth();
		int x,y,r;
		for(float i=0; i < Math.PI*2 ; i+=0.01){
			
			r = (int) (100*(1 - Math.sin(i)));
			x = (int) (r * Math.cos(i)+w/2);
			y = (int) (r * Math.sin(i)+h/2);
			
			for(int j = 0 ; j < 4 ; j++) {
				img.setPixel(x+j, y+j, 255);
				img.setPixel(x-j, y-j, 255);
			}
			
			
		}
		
	}
	
	public static void main (String[]args) throws Exception {
	
		Image img = new Image("C:/Users/Lucas C Mendes/Documents/JAVA/GATIS/src/GATIS/img2.jpg");
		img.convertToRGB();
		Cardioid a = new Cardioid();
		a.draw(img);
		img.exportImage("C:/Users/Lucas C Mendes/Documents/JAVA/GATIS/src/GATIS/teste.jpg", "jpg");
	
	}

}


