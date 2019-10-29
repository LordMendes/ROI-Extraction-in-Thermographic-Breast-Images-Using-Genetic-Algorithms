package GATIS;

public class MyMath {								//Class of math's operations that we will use


	final static int decimalArraySize = 10;			//number of bits 
	
	static int binaryToDecimal(int[] n) 			//transforms a binary number into decimal
    { 
		
		int[] num = new int[10];
		
		num = n; 
	    int dec_value = 0; 
	  
	    // Initializing base value to 1, i.e 2^0 
	    int base = 1; 
	  
	    int len = decimalArraySize; 
	    for (int i = len-1; i >= 0; i--) { 
	        if (num[i] == 1) 
	            dec_value += base; 
	        base = base * 2; 
	    } 
		//System.out.println(dec_value);	  
	    return (dec_value); 
	} 
	
	static float pow(int base, int pot) {
		float r=base;
		/*
		for(int i = 0 ; i < pot-1 ; i++) {
			r=r*r;
		}*/
		return (base^pot);
	}
	
	static double converte_gray_dec(int[]gray)
	{
	    int[]bin = new int[decimalArraySize];
	    int i= decimalArraySize-1;
	    while(i>0)
	    {
	        if(i==(decimalArraySize-1))
	        {
	            bin[i]=gray[i];
	        }
	        else
	        {
	            bin[i]=(int) pow(gray[i],bin[i+1]);
	        }
	        i--;
	    }
	    bin[0] = gray[0];
	    double dec =0;
	    for(int h =0, j=(decimalArraySize-1); j>0; h++,j--)
	    {
	        dec += bin[j]*pow(2,h);
	    }
	    dec = dec/10000;
	        return dec;

	}
	
	static int[] converte_dec_gray(double x)
	{
	    float decf = (float) (x*1000);
	    int dec = (int) decf;
	    int[]bin = new int[decimalArraySize];
	   /* if(dec>0)
	        bin[0] =0;
	    else
	    {
	        bin[0] = 1;
	        dec = -dec;
	    }*/
	    for(int i =(decimalArraySize-1); i>0; i--)
	    {
	        bin[i] = dec%2;
	        dec = dec/2;
	    }

	    int[]gray = new int[decimalArraySize];
	    int i= decimalArraySize-1;
	    while(i>0)
	    {
	        if(i==(decimalArraySize-1))
	        {
	            gray[i]=bin[i];
	        }
	        else
	        {
	            gray[i]=bin[i]^bin[i+1];
	        }
	        i--;
	    }
	    gray[0] = bin[0];


	    return gray;
	}
	
	static int[] decToBinary(int n) 				//transforms a decimal number into binary 
    { 
        // array to store binary number 
        int[] binaryNum = new int[decimalArraySize]; 
        
        // counter for binary array 
        int i = 0; 
        while (n > 0) { 
            // storing remainder in binary array 
            binaryNum[i] = n % 2; 
            n = n / 2; 
            i++; 
        } 
        int[] binaryArray = new int[decimalArraySize];
        // printing binary array in reverse order 
        for (int j = 0; j < decimalArraySize; j++) {
        	if(binaryNum[decimalArraySize-j-1] == 1) {
        		binaryArray[j] =  binaryNum[decimalArraySize-j-1];
        	}else {
        		binaryArray[j] = 0;
        	}
        }
        return binaryArray;
    }
	
	public static void main(String[] args){ 
        int[] s = {0,0,0,1,1,0,0,1,0,0};
        double g = converte_gray_dec(s);
        int[] a = converte_dec_gray(100);
        
        System.out.println(g);
        
        for(int i = 0 ; i < decimalArraySize ; i++) {
        	System.out.print(a[i]);
        }
        
    }
	
	
}
