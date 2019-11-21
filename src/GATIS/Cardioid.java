package GATIS;

import image.Image;
import java.awt.Color;
public class Cardioid {

	int x;
	int y;
	int a;

	Color cor = new Color(255, 0, 0);

	
	
	Cardioid(){
		
		x = 330;
		y = 220;
		a = 100;
		
	}
	
	//(x^2+y^2-ax)^2 = a^2(x^2+y^2)
	//p = a(1+sin(tetha))
	Boolean contains(int x ,int y, double r) {
		
		double rc = Math.sqrt(Math.pow(this.x-x, 2)+Math.pow(this.y-y, 2));
				
		if (rc<=r)
			return true;
		else
			return false;	
		
	}
	
	void drawC(Image img) {
		
		int h = img.getHeight();
		int w = img.getWidth();
		double t,r;
		for(int i = 0 ; i < h ; i ++) {
			for(int j = 0 ; j < w ; j++) {
				
				System.out.print("X :"+(this.x - j));
				System.out.print(" | Y :"+(this.y - i));
				
				t = Math.acos((((-j)^2 + this.x*j^2 + (-i)^2 + this.y*i)/(Math.sqrt((j-this.x)^2 + (i-this.y)^2))));
				
				r = this.a*(1 - Math.sin(Math.toRadians(t)));
				
				System.out.println(" | R = "+r+" | Rc = "+(Math.sqrt(Math.pow(j,2)+Math.pow(i, 2)))+" | t : "+t);
				
				if(contains(j,i,r)) {
					img.setPixel(i, j, cor);
				}
				
			}
		}
		
	}
	
	void draw(Image img) {
		
		int h = img.getHeight();
		int w = img.getWidth();
		int x,y,r;
		for(float i=0; i < Math.PI*2 ; i+=0.01){
			
			r = this.a*(int) (100*(1 - Math.sin(i)));
			x = (int) (r * Math.pow(Math.cos(i),3))+this.x;
			y = (int) (r * Math.sin(i))+this.y;
			
			
			System.out.println("x : "+x+" y: "+y);
			if(x>=0+4 && x<=640-4 && y>=0+4 && y<=480-4) {
				for(int t = 0 ; t < 4 ; t++) {
					img.setPixel(x+t, y+t, 255);
					img.setPixel(x-t, y-t, 255);
				}
			}
		}
		
	}
	
	public static void main (String[]args) throws Exception {
	
		Image img = new Image("C:/Users/Lucas C Mendes/Documents/JAVA/GATIS/src/GATIS/img2.jpg");
		img.convertToRGB();
		Cardioid a = new Cardioid();
		a.drawC(img);
		img.exportImage("C:/Users/Lucas C Mendes/Documents/JAVA/GATIS/src/GATIS/teste.jpg", "jpg");
	
	}

}


