import java.util.*;
import java.lang.*;
import java.io.*;

class GFG{
	
	static int[][] sumArr;
	static int[][] solArr;
	
	static int sum(int[] arr, int f, int t){
		if(sumArr[f][t] != 0) return sumArr[f][t];
		int sum = 0;
		for(int i=f; i<=t; i++) sum += arr[i];
		return (sumArr[f][t] = sum);
	}
    
    static int solve(int[] arr, int n, int k){
		if(solArr[n-1][k-1] != 0) return solArr[n-1][k-1];
		if(k==1) return sum(arr, 0, n-1);
		if(n==1) return arr[0];
		
		int best = Integer.MAX_VALUE;
		for(int i=1; i<=n; i++){
			int ans = Math.max(solve(arr,i,k-1), sum(arr,i,n-1));
			best = Math.min(best,ans);
		}
		
		return (solArr[n-1][k-1] = best);
    }
    
    public static void main(String[] lonar){
	    Scanner in = new Scanner(System.in);
	    int t = in.nextInt();
	    
	    while(t-- > 0){
	        int m = in.nextInt();
	        int n = in.nextInt();
			sumArr = new int[n+1][n+1];
			solArr = new int[n+1][m+1];
	        int[] arr = new int[n];
	        for(int i=0; i<n; i++) arr[i] = in.nextInt();
	        
	        int res = solve(arr, n, m);
	        System.out.println(res);
	    }
    }
}