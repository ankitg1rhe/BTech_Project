/*

		Pair<Integer, Integer>[] pair;
		pair = li.toArray();
		Arrays.sort(li, new Comparator<Pair>(){
			@Override
			public int compare(Pair p1, Pair p2){
				return p1.getKey().compareTo(p2.getKey());
			}
		});

*/


import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;
import javafx.util.Pair;

class SortPair implements Comparator<Pair<Integer, Integer>>{
	
	public int compare(Pair<Integer, Integer> a, Pair<Integer, Integer> b){
		return a.getKey() - b.getKey();
	}
}

public class Ankit{

    public static void main(String[] lonar){
		Scanner in = new Scanner(System.in);
		int n = 5;
		ArrayList<Pair<Integer, Integer>> li = new ArrayList<>();
		
		li.add(new Pair<Integer, Integer>(39, 60));
		li.add(new Pair<Integer, Integer>(15, 28));
		li.add(new Pair<Integer, Integer>(27, 40));
		li.add(new Pair<Integer, Integer>(50, 90));
		li.add(new Pair<Integer, Integer>(5, 24));
		
		Collections.sort(li, new SortPair());
		
		for(Pair<Integer, Integer> ans : li){
			System.out.println(ans.getKey() + " -> " + ans.getValue());
		}
	}
}


