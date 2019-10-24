package GATIS;

public class Math {

	
	
	static int binaryToDecimal(int[] n) 
    { 
        int[] num = n; 
        int dec_value = 0; 
  
        // Initializing base value to 1, 
        // i.e 2^0 
        int base = 1; 
  
        int len = num.length; 
        for (int i = len - 1; i >= 0; i--) { 
            if (num[i] == 1) 
                dec_value += base; 
            base = base * 2; 
        } 
  
        return dec_value; 
    }  
	
	static int[] decToBinary(int n) 
    { 
        // array to store binary number 
        int[] binaryNum = new int[32]; 
        
        // counter for binary array 
        int i = 0; 
        while (n > 0) { 
            // storing remainder in binary array 
            binaryNum[i] = n % 2; 
            n = n / 2; 
            i++; 
        } 
        int[] binaryArray = new int[i];
        // printing binary array in reverse order 
        for (int j = 0; j < i; j++) {
            binaryArray[j] =  binaryNum[i-j-1];
            System.out.print(binaryArray[j]);
        }
        return binaryArray;
    }
	
	public static void main(String[] args){ 
        int n = 17; 
        decToBinary(n); 
        int[] num = {1,0,0,0,1};
        System.out.println(binaryToDecimal(num));
        
    }
	
	
}
