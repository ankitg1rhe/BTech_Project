import java.util.*;
import java.io.*;
import java.lang.*;
import java.math.*;

public class Ankit {
	
	BigInteger eularian(int n, int m){
	int count = 0;
		if(m >= n) return BigInteger.valueOf(0);
		BigInteger[][] eu = new BigInteger[n+1][n];
		for(int i=1; i<=n; i++)
			eu[i][0] = eu[i][i-1] = BigInteger.valueOf(1);
		
		for(int i=3; i<=n; i++){
			for(int j=1; j<i-1 && j<=m; j++, count++) eu[i][j] = BigInteger.valueOf((i-j)).multiply(eu[i-1][j-1]).
								add(BigInteger.valueOf((j+1)).multiply(eu[i-1][j]));
		}
		System.out.println("No of loops : " + count);
		return eu[n][m];
	}
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s = br.readLine();
		int n = Integer.parseInt(s.split("\\s+")[0]);
		int m = Integer.parseInt(s.split("\\s+")[1]);
		
		BigInteger ans = new Ankit().eularian(n, m);
		System.out.println(ans);
	}
}
