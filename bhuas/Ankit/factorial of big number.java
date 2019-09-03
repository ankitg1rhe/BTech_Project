import java.util.Scanner;
import java.math.BigInteger;

public class Ankit{
	public static void printFactorial(int n){
		int[] ans = new int[1000];
		ans[0] = 1;
		int count = 1;
		
		for(int i=2; i<=n; i++){
			int head = 0;
			for(int index = 0; index <count; index++){
				int s = ans[index]*i + head;
				int x = s%10;
				ans[index] = x;
				head = s/10;
			}
			
			while(head > 0){
				int s = head%10;
				ans[count++] = s;
				head /= 10;
			}
		}
		
		for(int i=count-1; i>=0; i--)
			System.out.print(ans[i]);
	}

	public static void main (String[] args){
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		
		printFactorial(n);
	}
}