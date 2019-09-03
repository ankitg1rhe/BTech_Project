import java.io.*;
import java.util.*;

public class Ankit{
	
	public static int matrixChain(int[] arr){
		int n = arr.length;
		int[][] matrix = new int[n][n];
		int i, j, k, l, q;
		
		for(i=0; i<n; i++) matrix[i][i] = 0;
		
		for(l=2; l<n; l++){
			for(i=1; i<n-l+1; i++){
				j = i+l-1;
				if(j == n) continue;
				matrix[i][j] = Integer.MAX_VALUE;
				
				for(k=i; k<j; k++){
					q = matrix[i][k] + matrix[k+1][j] + arr[i-1]*arr[k]*arr[j];
					if(q < matrix[i][j]) 
						matrix[i][j] = q;
				}
			}
		}
		return matrix[1][n-1];
	}
	
	public static void main(String[] args){
		int[] arr = new int[]{1, 2, 3, 4, 3};
		
		int result = matrixChain(arr);
		System.out.println(result);
	}
}