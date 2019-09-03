import java.util.*;
import java.io.*;
import java.lang.*;

public class Ankit {
	static boolean[] visited;
	static int cyc;

	void solveHC(int[][] graph, int n){
		visited = new boolean[n];
		cyc = 0;
		visited[0] = true;
		solveHCUtil(("" + 0), graph, n, 0);
		if(cyc == 0) System.out.println("No hamiltonion cycle");
	}
	
	void solveHCUtil(String s, int[][] graph, int n, int v){
		boolean all = true;
		for(int i=0; i<n; i++)
			if(visited[i] == false){
				all = false; break;
			}
		if(all && graph[v][0] == 1){
			System.out.println(s);
			cyc++;
			return;
		}
		
		for(int i=1; i<n; i++){
			if(visited[i]==false && graph[v][i] == 1){
				visited[i] = true;
				solveHCUtil((s+" -> "+i), graph, n, i);
				visited[i] = false;
			}
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s = br.readLine();
		int n = Integer.parseInt(s.split("\\s+")[0]);
		int[][] graph = new int[n][n];

		for(int i=0; i<n; i++){
			String line = br.readLine();
			String[] st = line.split("\\s+");
			for(int j=0; j<n; j++)
				graph[i][j] = Integer.parseInt(st[j]);
		}

		new Ankit().solveHC(graph,n);
		
	}
}
 