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
		bX = MyMath.decToBinary(cX);
		cY = y;
		bY = MyMath.decToBinary(cY);
		radius = r;			
		bR = MyMath.decToBinary(radius);

	}
	
	Circle(int[] x, int[] y, int[] r){
		
		
		if(MyMath.binaryToDecimal(r) > 100) {
			r = MyMath.decToBinary(100);
		}if(MyMath.binaryToDecimal(y) < 75) {
			y = MyMath.decToBinary(75);
		}if(MyMath.binaryToDecimal(y) > 420) {
			y = MyMath.decToBinary(420);
		}
		
		cX = MyMath.binaryToDecimal(x);
		bX = x;
		cY = MyMath.binaryToDecimal(y);
		bY = y;
		radius = MyMath.binaryToDecimal(r);			
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
		cX = MyMath.binaryToDecimal(x);
	}
	
	void setY(int[] y) {
		bY = y;
		cY = MyMath.binaryToDecimal(y);
	}
	
	void setR(int[] r) {
		bR = r;
		radius = MyMath.binaryToDecimal(r);
	}
	
	void setPosition(int x , int y) {
		cX = x;
		cY = y;
	}
	
	void setRadius(int r) {
		radius = r;
		bR = MyMath.decToBinary(r);
	}
	
	public static void main(String[]args) {
		Circle a = new Circle(100,100,100);
		int[]c = a.getBx();
		for(int i = 0 ; i <10;i++) {
			System.out.print(c[i]);
		}
	}

}
