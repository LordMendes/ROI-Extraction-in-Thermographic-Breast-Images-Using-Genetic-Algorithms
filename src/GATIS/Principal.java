package GATIS;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import image.Image;


public class Principal {
	
	static int n = 12;
	
	
	public static void main(String[] args) throws Exception {
		ArrayList<Individual> top = new ArrayList<Individual>();
		GA a = new GA();
		Image img = new Image("C:/Users/Lucas C Mendes/Documents/JAVA/GATIS/src/GATIS/img5.jpg");
		CountDownLatch latch = new CountDownLatch(n);
		
		ArrayList<MinhaThread> lista = new ArrayList<MinhaThread>();
		
		for(int i = 0 ; i < n ; i++) {
			lista.add(new MinhaThread(img,i,latch));
			lista.get(i).start();			
		}
		try {
			latch.await();//wait for n*2 threads to signal the latch
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(int i = 0 ; i < n ; i++) {
			lista.get(i).a.getTop5(top);
		}
		a.initPop(top);
		a.run(img);
		top.get(a.POP-1).draw(img);
		img.exportImage("C:/Users/Lucas C Mendes/Documents/JAVA/GATIS/src/GATIS/resultado.jpg", "jpg");
	}

}
