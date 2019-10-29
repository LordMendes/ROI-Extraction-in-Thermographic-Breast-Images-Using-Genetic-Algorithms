package GATIS;

public class Circle {
	//CONSTANTS
	final int decimalArraySize = 10;
	
	//ATTRIBUTES
	int cX;									//X - coordinate form the circle center
	int[] bX = new int[decimalArraySize];	// conversion the cX variable to binary
	int cY;									//Y - coordinate form the circle center
	int[] bY = new int[decimalArraySize];	// conversion the cY variable to binary
	
	int radius;								// circules's radius length 
	int[] bR = new int[decimalArraySize];	// conversion radius to binary
	
	
	
	
	Circle(int x, int y, int r){			//constructor with decimal numbers parameters 
		
		cX = x;
		bX = MyMath.decToBinary(cX);
		cY = y;
		bY = MyMath.decToBinary(cY);
		radius = r;			
		bR = MyMath.decToBinary(radius);

	}
	
	Circle(int[] x, int[] y, int[] r){		//constructor with binary numbers parameters 
		

		if(MyMath.binaryToDecimal(r) > 175) {
			r = MyMath.decToBinary(200);
		}
		if(MyMath.binaryToDecimal(r) < 50) {
			r = MyMath.decToBinary(100);
		}
		if(MyMath.binaryToDecimal(y) < 125) {
			y = MyMath.decToBinary(125);
		}
		if(MyMath.binaryToDecimal(y) > 420) {
			y = MyMath.decToBinary(420);
		}
		
		cX = MyMath.binaryToDecimal(x);
		bX = x;
		cY = MyMath.binaryToDecimal(y);
		bY = y;
		radius = MyMath.binaryToDecimal(r);			
		bR = r;
	}
	
	Circle(Circle c){				//constructor with Circle's object parameter 
		setX(c.getBx());
		setY(c.getBy());
		radius = c.getRadius();
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

	//setters
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
