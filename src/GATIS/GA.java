package GATIS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import image.Image;

public class GA {
	//CONSTANTS
	final int decimalArraySize = 10;
	final static int POP = 20;
	final int GEN = 10;
	final float mR = 0.1f;
	final float cR = 0.7f;
	//ATTRIBUTES
	Random r = new Random();
	
	static ArrayList<Individual> pop = new ArrayList<Individual>();
	
	
	void initPop(Image img) {
		for(int i = 0 ; i < POP ; i++) {
			Individual a = new Individual(img);
			pop.add(a);
		}
		Collections.sort(pop);
	}
	
	void printAll() {
		
		for(int k = 0 ; k < POP ; k++){
			System.out.println("Individuo "+k+" Score : " + pop.get(k).getScore());
		}
		
	}
	
	void printBest() {
		System.out.println(pop.get(POP-1).getScore());
	}

	Individual crossover(Individual a1, Individual a2 ,Image img) {
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
		
		//System.out.print("a1 : "+a1.getScore()+" , a2 : "+a2.getScore());
		
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

		Individual children = new Individual(cl,cr,img);
		
		//System.out.println(" --> Child : "+children.getScore());
		
		return children;
		
	}
	
	Individual mutation(Individual a) {
		
		int point; 
		float rateX = r.nextFloat();
		float rateY = r.nextFloat();
		float rateR = r.nextFloat();
		
		int[]aux = new int[decimalArraySize];
		
		if(rateX > 0.5) {
			point = r.nextInt(decimalArraySize);
			aux = a.getCircle(1).getBx();
			if(aux[point] == 0) {
				aux[point]=1;
			}else {
				aux[point]=0;
			}
		}if(rateY > 0.5) {
			point = r.nextInt(decimalArraySize);
			aux = a.getCircle(1).getBx();
			if(aux[point] == 0) {
				aux[point]=1;
			}else {
				aux[point]=0;
			}
		}if(rateR > 0.5) {
			point = r.nextInt(decimalArraySize);
			aux = a.getCircle(1).getBx();
			if(aux[point] == 0) {
				aux[point]=1;
			}else {
				aux[point]=0;
			}
		}
		
		rateX = r.nextFloat();
		rateY = r.nextFloat();
		rateR = r.nextFloat();
		
		if(rateX > 0.5) {
			point = r.nextInt(decimalArraySize);
			aux = a.getCircle(2).getBx();
			if(aux[point] == 0) {
				aux[point]=1;
			}else {
				aux[point]=0;
			}
		}if(rateY > 0.5) {
			point = r.nextInt(decimalArraySize);
			aux = a.getCircle(2).getBx();
			if(aux[point] == 0) {
				aux[point]=1;
			}else {
				aux[point]=0;
			}
		}if(rateR > 0.5) {
			point = r.nextInt(decimalArraySize);
			aux = a.getCircle(2).getBx();
			if(aux[point] == 0) {
				aux[point]=1;
			}else {
				aux[point]=0;
			}
		}
		
		return a;
	}
	
	Individual tournament() {
		
		int x1 = r.nextInt(POP-1);
		int x2 = r.nextInt(POP-1);
		
		Individual a1 = pop.get(x1);
		Individual a2 = pop.get(x2);
		
		if(a1.getScore() <= a2.getScore())
			return a1;
		else
			return a2;
		
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
	
	void run(Image img) {
		
		initPop(img);
		int n = 0;
		Individual elite = new Individual(img);
		ArrayList<Individual> popAux = new ArrayList<Individual>();
		
		float m;
		float c;
		
		Individual a1 = null;
		Individual a2 = null;
		Individual child = null;
		
		while(n < GEN) {
			
			elite = pop.get(POP-1);
			
			while(popAux.size() <= POP-1) {
				
				m = r.nextFloat();
				c = r.nextFloat();
				a1 = tournament();
				a2 = tournament();
				
				if(c < cR) {
					child = crossover(a1,a2,img);
				}
				if(m < mR) {
					mutation(a1);
				}
				popAux.add(child);
			}/*
			if(popAux.size()<POP) {
				for(int i = 0 ; i < POP-popAux.size()-1;i++) {
					popAux.add(pop.get(POP-i-1));
				}
			}*/
			
			popAux.add(elite);
			pop.clear();
			for(int i = 0 ; i < POP ; i++) {
				pop.add(popAux.get(i));
			}
			popAux.clear();
			System.out.print("Geração "+n+" -> ");
			//printBest();
			printAll();
			n++;
		}
	}
	
	public static void main(String[]args) throws Exception {
		GA a = new GA();
		Image img = new Image("C:/Users/Lucas C Mendes/Documents/JAVA/GATIS/src/GATIS/img1.jpg");
		
		//a.initPop(img);
		a.lessQual(img);
		a.run(img);
		pop.get(POP-1).draw(img);
		img.exportImage("C:/Users/Lucas C Mendes/Documents/JAVA/GATIS/src/GATIS/asd.jpg", "jpg");
	}
	
}
