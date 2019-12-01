package GATIS;

import image.Image;

public class MeuRunnable implements Runnable{
	public GA a;
	Image img;
	

	public  MeuRunnable(Image img) throws Exception {
		this.img = new Image(img);
		this.img.convertToRGB();
	}
	
	
	@Override
	public void run() {
		a = new GA();
		a.run(this.img);
		a.pop.get(a.(POP-1)).draw(img);
		try {
			img.exportImage("C:/Users/Lucas C Mendes/Documents/JAVA/GATIS/src/GATIS/XXXXXXX.jpg", "jpg");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
