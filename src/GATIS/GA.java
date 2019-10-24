package GATIS;

import java.util.Random;

public class GA {
	//CONSTANTS
	final int decimalArraySize = 10;
	
	
	
	Individual crossover(Individual a1, Individual a2 ) {
		Random r = new Random();
		Circle e1 = new Circle(a1.getCircle(1));
		Circle e2 = new Circle(a2.getCircle(1));
		Circle d1 = new Circle(a1.getCircle(2));
		Circle d2 = new Circle(a2.getCircle(2));
		
		
		int[]auxXl = new int[decimalArraySize];
		int[]auxYl = new int[decimalArraySize];
		int[]auxRl = new int[decimalArraySize];
		
		int[]auxXr = new int[decimalArraySize];
		int[]auxYr = new int[decimalArraySize];
		int[]auxRr = new int[decimalArraySize];
		
		int cutX = r.nextInt(decimalArraySize-1);
		int cutY = r.nextInt(decimalArraySize-1);
		int cutR = r.nextInt(decimalArraySize-1);
		
		for(int i = 0 ; i < decimalArraySize-1; i++) {
			//CROSSOVER OF TEM LEFT CIRCLE e1 AND e2
			//CROSSOVER OF X POSITION
			if(i <= cutX ) {
				auxXl[i] = e1.getBx()[i];
			}else {
				auxXl[i] = e2.getBx()[i];
			}
			//CROSSOVER OF Y POSITION
			if(i <= cutY) {
				auxYl[i] = e1.getBy()[i];
			}else{
				auxYl[i] = e2.getBy()[i];
			}
			//CROSSOVER OF RADIUS SIZE
			if(i <= cutR) {
				auxRl[i] = e1.getBr()[i];
			}else {
				auxRl[i] = e2.getBr()[i];
			}
			//CROSSOVER OF TEM RIGHT CIRCLE d1 AND d2
			//CROSSOVER OF X POSITION
			if(i <= cutX ) {
				auxXr[i] = d1.getBx()[i];
			}else {
				auxXr[i] = d2.getBx()[i];
			}
			//CROSSOVER OF Y POSITION
			if(i <= cutY) {
				auxYr[i] = d1.getBy()[i];
			}else{
				auxYr[i] = d2.getBy()[i];
			}
			//CROSSOVER OF RADIUS SIZE
			if(i <= cutR) {
				auxRr[i] = d1.getBr()[i];
			}else {
				auxRr[i] = d2.getBr()[i];
			}			
		}
		Circle cl = new Circle(auxXl, auxYl, auxRl);
		Circle cr = new Circle(auxXr, auxYr, auxRr);
		Individual children = new Individual(cl,cr);
		
		return children;
		
	}
	
	Individual tournament(Individual a1, Individual a2) {
		
		if(a1.getScore()>a2.getScore())
			return a1;
		else
			return a2;
		
	}
	
}
