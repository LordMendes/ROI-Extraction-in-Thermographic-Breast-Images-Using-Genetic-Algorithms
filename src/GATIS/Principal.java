package GATIS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import image.Image;


public class Principal {
	
	static int n = 3;
	
	public static void main(String[] args) throws Exception {
		Image img = new Image("C:/Users/Lucas C Mendes/Documents/JAVA/GATIS/src/GATIS/img1.jpg");
		img.convertToRGB();
		
		ArrayList<Thread> lista = new ArrayList<Thread>();
		
		for(int i = 0 ; i < n ; i++) {
			lista.add(new Thread(new MeuRunnable(img)));
			lista.get(i).start();
		}

	}

}
