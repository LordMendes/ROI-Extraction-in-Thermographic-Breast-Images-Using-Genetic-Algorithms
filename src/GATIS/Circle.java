package GATIS;

public class Circle {
	//CONSTANTS
	final int decimalArraySize = 10;
	
	//ATTRIBUTES
	int cX;
	int[] bX = new int[decimalArraySize];
	int cY;
	int[] bY = new int[decimalArraySize];
	int radius;
	int[] bR = new int[decimalArraySize];
	
	
	
	
	Circle(int x, int y, int r){
		
		cX = x;
		bX = Math.decToBinary(cX);
		cY = y;
		bY = Math.decToBinary(cY);
		radius = r;			
		bR = Math.decToBinary(radius);
	}
	
	Circle(int[] x, int[] y, int[] r){
		
		cX = Math.binaryToDecimal(x);
		bX = x;
		cY = Math.binaryToDecimal(y);
		bY = y;
		radius = Math.binaryToDecimal(r);			
		bR = r;
	}
	
	Circle(Circle c){
		setX(c.getBx());
		setY(c.getBy());
		radius = c.getRadius();
		bX = c.getBx();
		bY = c.getBy();
		bR = c.getBr();
		
	}
	
	int getX() {
		return cX;
	}
	
	int getY() {
		return cY;
	}
	
	int getRadius() {
		return radius;
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

	void setX(int[] x) {
		bX = x;
		cX = Math.binaryToDecimal(x);
	}
	
	void setY(int[] y) {
		bY = y;
		cY = Math.binaryToDecimal(y);
	}
	
	void setR(int[] r) {
		bR = r;
		radius = Math.binaryToDecimal(r);
	}
	
	void setPosition(int x , int y) {
		cX = x;
		cY = y;
	}
	
	void setRadius(int r) {
		radius = r;
	}
	

}
