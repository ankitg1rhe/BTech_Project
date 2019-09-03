import java.util.*;
import java.io.*;
import java.lang.*;
import java.math.*;

public class Ankit {
	
	static class Node{
		int tim, ind;
		public Node(int t, int i){
			tim = t;
			ind = i;
		}
	}
	
	static List<Integer> solve(List<Integer> time, List<Integer> dir){
		int n = time.size();
		PriorityQueue<Node> entry = new PriorityQueue<Node>(n, new Comparator<Node>(){
			@Override
			public int compare(Node a, Node b){
				return a.tim - b.tim;
			}
		});
		
		PriorityQueue<Node> exit = new PriorityQueue<Node>(n, new Comparator<Node>(){
			@Override
			public int compare(Node a, Node b){
				return a.tim - b.tim;
			}
		});
		
		for(int i=0; i<n; i++){
			if(dir.get(i) == 1) exit.add(new Node(time.get(i), i));
			else entry.add(new Node(time.get(i), i));
		}
		
		while(!entry.isEmpty() || !exit.isEmpty()){
			
		}
	}
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		String a = br.readLine();
		String b = br.readLine();
		
		List<Integer> time = new ArrayList<Integer>();
		List<Integer> dir = new ArrayList<Integer>();
		String[] ti = a.split("\\s+");
		String[] di = b.split("\\s+");
		
		for(int i=0; i<n; i++){
			time.add(Integer.parseInt(ti[i]));
			dir.add(Integer.parseInt(di[i]));
		}
		
		List<Integer> ans = solve(time, dir);
		for(int i=0; i<ans.size(); i++)
			System.out.println(ans.get(i));
	}
}
