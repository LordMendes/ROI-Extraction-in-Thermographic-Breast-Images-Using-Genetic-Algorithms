package GATIS;

import java.util.concurrent.CountDownLatch;

import image.Image;

public class MeuRunnable implements Runnable{
	public GA a;
	Image img;
	

	public  MeuRunnable(Image img) {
		this.img = new Image(img);
	}
	
	
	@Override
	public void run() {
		a = new GA();
		a.run(img);
		try {
			img.exportImage("C:/Users/Lucas C Mendes/Documents/JAVA/GATIS/src/GATIS/XXXXXXX.jpg", "jpg");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
