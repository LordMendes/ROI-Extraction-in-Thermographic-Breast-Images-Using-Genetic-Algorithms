package GATIS;

import image.Image;
import java.awt.Color;
public class Cardioid {
	final int decimalArraySize = 10;
	
	int cX;									//X - coordinate form the circle center
	int[] bX = new int[decimalArraySize];	// conversion the cX variable to binary
	int cY;									//Y - coordinate form the circle center
	int[] bY = new int[decimalArraySize];	// conversion the cY variable to binary	
	int size;								// circules's size length 
	int[] bR = new int[decimalArraySize];	// conversion size to binary

	
	Cardioid(int x, int y, int s){			//constructor with decimal numbers parameters 
		
		cX = x;
		bX = MyMath.DectoGray(cX);
		cY = y;
		bY = MyMath.DectoGray(cY);
		size = s;			
		bR = MyMath.DectoGray(size);

	}
	
	Cardioid(int[] x, int[] y, int[] s){		//constructor with binary numbers parameters 
		
		
		cX = MyMath.GraytoDec(x);
		bX = x;
		cY = MyMath.GraytoDec(y);
		bY = y;
		size = MyMath.GraytoDec(s);			
		bR = s;
	}
	
	Cardioid (Cardioid c){				//constructor with Cardiod's object parameter 
		setX(c.getBx());
		setY(c.getBy());
		size = c.getSize();
		bX = c.getBx();
		bY = c.getBy();
		bR = c.getBr();
		
	}
	
	//getters
		int getX() {
			return cX;
		}
		
		int getY() {
			return cY;
		}
		
		int getSize() {
			return size;
		}
		
		int[] getBx() {
			return bX;
		}
		
		int[] getBy() {
			return bY;
		}
		
		int[] getBr() {
			return bR;
		}

		//setters
		void setX(int[] x) {
			bX = x;
			cX = MyMath.GraytoDec(x);
		}
		
		void setY(int[] y) {
			bY = y;
			cY = MyMath.GraytoDec(y);
		}
		
		void setS(int[] s) {
			bR = s;
			size = MyMath.GraytoDec(s);
		}
		
		void setPosition(int x , int y) {
			cX = x;
			cY = y;
		}
		
		void setSize(int s) {
			size = s;
			bR = MyMath.DectoGray(s);
		}

		public static void main(String[]args) {
			Cardioid a = new Cardioid(100,100,100);
			int[]c = a.getBx();
			for(int i = 0 ; i <10;i++) {
				System.out.print(c[i]);
			}
		}		

}


