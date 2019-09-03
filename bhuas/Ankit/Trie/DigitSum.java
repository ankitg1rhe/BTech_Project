import java.util.*;
import java.io.*;
import java.lang.*;

public class Ankit{
	static int[][][] dp;
	
	static int solve(int a, int b, int ad, int bd){
		int[] digitA = new int[30];
		getDigits(a-1, digitA);
		int ans1 = digitSum(ad-1, 0, 1, digitA);
		
		int[] digitB = new int[30];
		getDigits(b, digitB);
		int ans2 = digitSum(bd-1, 0, 1, digitB);
		
		return ans2-ans1;
	}

	static int digitSum(int idx, int sum, int tight, int[] digit){
		if(idx == -1) return sum;
		if(dp[idx][sum][tight] != -1 && tight != 1) return dp[idx][sum][tight];
		
		int ret=0;
		int k = (tight==1)? digit[idx] : 9;
		for(int i=0; i<=k; i++){
			int newTight = (digit[idx] == i) ? tight : 0;
			ret += digitSum(idx-1, sum+i, newTight, digit);
		}
		
		if(tight != 1) dp[idx][sum][tight] = ret;
		return ret;
	}

	static void getDigits(int x, int[] digit){
		int n = 0;
		while(x != 0){
			digit[n++] = x%10;
			x /= 10;
		}
	}
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String as = br.readLine();
		String[] st = as.split("\\s+");
		int a = Integer.parseInt(st[0]);
		int b = Integer.parseInt(st[1]);
		dp = new int[20][180][2];
		for(int i=0; i<20; i++)
			for(int j=0; j<180; j++)
				for(int k=0; k<2; k++)	dp[i][j][k] = -1;

		int ans  = solve(a, b, st[0].length(), st[1].length());
		System.out.println(ans);
	}
}