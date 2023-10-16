package GATIS;

import java.util.concurrent.CountDownLatch;

import image.Image;

public class MinhaThread extends Thread{
	private final CountDownLatch doneSignal;
	private int threadID;
	
	public GA a;
	Image img;
	
	
	MinhaThread(Image img, int id, CountDownLatch doneSignal){
		this.img = new Image(img);
		this.doneSignal = doneSignal;
		threadID = id;
	}
	
	public void run() {
		
		a = new GA();
		a.initPop(img);
		a.run(this.img);
		doneSignal.countDown();
		System.out.println("id : "+this.threadID);
				
	}
}
