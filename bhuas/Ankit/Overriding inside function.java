import java.io.*;
import java.math.*;
import java.util.*;

class Pair{	
	int a, b;
	public Pair(int a, int b){
		this.a = a;
		this.b = b;
	}
}

public class Ankit{
    public static void main(String[] lonar){
		Scanner in = new Scanner(System.in);
		ArrayList<Pair> li = new ArrayList<>();
		li.add(new Pair(7, 40));
		li.add(new Pair(47, 40));
		li.add(new Pair(20, 80));
		li.add(new Pair(5, 24));
		li.add(new Pair(27, 40));
		li.add(new Pair(15, 45));
		li.add(new Pair(3, 14));
		li.add(new Pair(30, 54));
		
		Collections.sort(li, new Comparator<Pair>(){
			@Override
			public int compare(Pair p1, Pair p2){
				return p1.a - p2.a;
			}
		});
		
		for(Pair x : li){
			System.out.println(x.a);
		}
	}
}


