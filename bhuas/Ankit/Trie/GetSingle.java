import java.io.*;
import java.util.*;

public class GetSingle{

	public static int getSingle(int[] arr){
		int result = 0; 
        int x, sum; 
        
        for(int i=0; i<32; i++){ 
            sum = 0; 
            x = (1 << i); 
            for(int j=0; j<n; j++) 
            { 
                if((arr[j] & x) == x) 
                sum++; 
            }
            if ((sum % 3) == 1) 
            result |= x; 
        } 
        return result; 
	}
/*
	public static int getSingle(int[] arr){
		int ones=0, twos=0;
		int common_bit_mask;
		
		for(int i=0; i<arr.length; i++){
			twos = twos | (ones&arr[i]) ;
			ones = ones^arr[i];
			
			common_bit_mask = ~(ones&twos);
			ones &= common_bit_mask;
			twos &= common_bit_mask;
		}
		
		return twos;
	}
*/	
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		
		int[] arr = new int[n];
		for(int i=0; i<n; i++) arr[i] = in.nextInt();
		int result = getSingle(arr);
		System.out.println("Single element: " + result);
	}
}