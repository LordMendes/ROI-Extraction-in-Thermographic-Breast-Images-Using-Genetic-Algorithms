package GATIS;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import image.Image;

public class GA {
	//CONSTANTS
	final int decimalArraySize = 10;
	final static int POP = 50;
	final int GEN = 50;
	final float mR = 0.02f;
	final float cR = 0.7f;
	//ATTRIBUTES
	Random r = new Random();
	
	static ArrayList<Individual> pop = new ArrayList<Individual>();
	
	
	void initPop(Image img) {
		pop.clear();
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
		System.out.println(pop.get(0).getScore());
	}

	Individual[] crossover(Individual a1, Individual a2 ,Image img) {

		Circle l1 = new Circle(a1.getCircle(1));
		Circle l2 = new Circle(a2.getCircle(1));
		Circle r1 = new Circle(a1.getCircle(2));
		Circle r2 = new Circle(a2.getCircle(2));
		
		
		int[]auxXl = new int[decimalArraySize];
		int[]auxYl = new int[decimalArraySize];
		int[]auxRl = new int[decimalArraySize];
		
		int[]auxXr = new int[decimalArraySize];
		int[]auxYr = new int[decimalArraySize];
		int[]auxRr = new int[decimalArraySize];
		
		int[]auxXl2 = new int[decimalArraySize];
		int[]auxYl2 = new int[decimalArraySize];
		int[]auxRl2 = new int[decimalArraySize];
		
		int[]auxXr2 = new int[decimalArraySize];
		int[]auxYr2 = new int[decimalArraySize];
		int[]auxRr2 = new int[decimalArraySize];
		
		int cutX = r.nextInt(decimalArraySize-1);
		int cutY = r.nextInt(decimalArraySize-1);
		int cutR  = r.nextInt(decimalArraySize-1);
		//LEFT CIRCLE
		for(int i = 0 ; i < decimalArraySize ; i++) {
			if(i < cutX) {
				auxXl[i] = l1.getBx()[i];				
				auxXl2[i] = l2.getBx()[i];
			}else {
				auxXl[i] = l2.getBx()[i];
				auxXl2[i] = l1.getBx()[i];
			}
			if(i < cutY) {
				auxYl[i] = l1.getBy()[i];
				auxYl2[i] = l2.getBy()[i];
			}else {
				auxYl[i] = l2.getBy()[i];
				auxYl2[i] = l1.getBy()[i];
			}
			if(i < cutR) {
				auxRl[i] = l1.getBr()[i];
				auxRl2[i] = l2.getBr()[i];
			}else {
				auxRl[i] = l2.getBr()[i];
				auxRl2[i] = l1.getBr()[i];
			}
		}
		//RIGHT CIRCLE
		for(int i = 0 ; i < decimalArraySize ; i++) {
			if(i < cutX) {
				auxXr[i] = r1.getBx()[i];
				auxXr2[i] = r2.getBx()[i];
			}else {
				auxXr[i] = r2.getBx()[i];
				auxXr2[i] = r1.getBx()[i];
			}
			if(i < cutY) {
				auxYr[i] = r1.getBy()[i];
				auxYr2[i] = r2.getBy()[i];
			}else {
				auxYr[i] = r2.getBy()[i];
				auxYr2[i] = r1.getBy()[i];
			}
			if(i < cutR) {
				auxRr[i] = r1.getBr()[i];
				auxRr2[i] = r2.getBr()[i];
			}else {
				auxRr[i] = r2.getBr()[i];
				auxRr2[i] = r1.getBr()[i];
			}
		}
		//A CHECAR A VALIDADE DESSA LIMITAÇÃO
		if(MyMath.binaryToDecimal(auxXl) > img.getWidth()/2) {
			auxXl = MyMath.decToBinary((img.getWidth()-1)/2);
		}
		if(MyMath.binaryToDecimal(auxXr) < img.getWidth()/2) {
			auxXl = MyMath.decToBinary((img.getWidth()-1)/2);
		}
			
		Circle cl = new Circle(auxXl, auxYl, auxRl);
		Circle cr = new Circle(auxXr, auxYr, auxRr);
		Circle cl2 = new Circle(auxXl2, auxYl2, auxRl2);
		Circle cr2 = new Circle(auxXr2, auxYr2, auxRr2);
		
		Individual child = new Individual(cl,cr,img);
		Individual child2 = new Individual(cl2,cr2,img);
		
		
		Individual[] children = new Individual[2];
		children[0] = child;
		children[1] = child2;
		
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
		
		int x1 ;
		int x2 ;
		Individual a1=null;
		Individual a2=null;
		//while(a1 == null) {
			x1 = r.nextInt(POP-1);
			a1 = pop.get(x1);
		//}
		//while(a2 == null) {
			x2 = r.nextInt(POP-1);
			a2 = pop.get(x2);
		//}

		if(a1.getScore() >= a2.getScore())			
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
		Individual elite;
		
		float m;
		float c;
		
		Individual a1;
		Individual a2;
		Individual[] children = new Individual[2];
		ArrayList<Individual> popAux = new ArrayList<Individual>(POP);
		
		while(n < GEN) {
			popAux.clear();
			
			int t=0;	
			elite = pop.get(0);
			while(t <= (POP/2-1)) {

				
				m = r.nextFloat();
				c = r.nextFloat();
				a1 = tournament();
				a2 = tournament();
				
				children[0] = a1;
				children[1] = a2;
				if(c < cR) {
					children = crossover(a1,a2,img);
				}
				
				
				if(m < mR) {
					mutation(children[0]);
				}
				m = r.nextFloat();
				if(m < mR) {
					mutation(children[1]);
				}
				
				popAux.add(children[0]);
				popAux.add(children[1]);
				t++;
			}
			popAux.add(elite);
			if(popAux.size()<POP) {
				for(int i = 0 ; i < (POP-popAux.size()-1);i++) {
					popAux.add(pop.get(i));
				}
			}
			
			
			
			
			pop.clear();
			pop.addAll(popAux);
			
			
			n++;
			
		}
		
	}
	
	public static void main(String[]args) throws Exception {
		GA a = new GA();
		
		
		
		
		for(int i = 0 ; i < 10 ; i++) {
			Image img = new Image("C:/Users/Lucas C Mendes/Documents/JAVA/GATIS/src/GATIS/img2.jpg");
			a.lessQual(img);
			img.convertToRGB();
			a.run(img);
			pop.get(POP-1).draw(img);
			img.exportImage("C:/Users/Lucas C Mendes/Documents/JAVA/GATIS/src/GATIS/asd"+i+".jpg", "jpg");
			
			System.out.print("Ga : "+i);
			System.out.println(" | Melhor Fitness : "+pop.get(POP-1).getScore());
		}
	
	}
	
}
