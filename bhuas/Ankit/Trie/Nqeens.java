import java.util.*;
import java.io.*;
import java.lang.*;

public class Ankit {
	static int[][] chess;

	void printSolution(int n){
		for(int x=0; x<n; x++){
			for(int y=0; y<n; y++)
				System.out.print(chess[x][y] + " ");
			System.out.println();
		}
		
		System.out.println();
	}

	boolean isSafe(int r, int c, int n){
		if(r<0 || c<0 || r>=n || c>= n) return false;
		
		for(int i=1; (r-i)>=0 && (c-i)>=0; i++)
			if(chess[r-i][c-i] == 1) return false;
		for(int i=1; (r-i)>=0 && (c+i)<n; i++)
			if(chess[r-i][c+i] == 1) return false;
		return true;
	}

	void solveNQ(int n){
		int no = 0;
		for(int i=0; i<n; i++){
			for(int x=0; x<n; x++)
				for(int y=0; y<n; y++) chess[x][y] = 0;
			
			chess[0][i] = 1;
			boolean[] col = new boolean[n];
			col[i] = true;
			if(solveNQUtil(1, col,n)){
				System.out.println("Solution no.: " + (++no));
				printSolution(n);
			}
		}
		
		if(no == 0)
			System.out.println("No solutions");
	}
	
	boolean solveNQUtil(int row, boolean[] col, int n){
		if(row == n) return true;
		
		for(int i=0; i<n; i++){
			if(col[i]) continue;
			
			if(isSafe(row, i,n)){
				chess[row][i] = 1;
				col[i] = true;
				if(solveNQUtil(row+1, col, n)) return true;
				chess[row][i] = 0;
				col[i] = false;
			}
		}
		return false;
	}
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		chess = new int[n][n];
		
		new Ankit().solveNQ(n);
		
	}
}
 