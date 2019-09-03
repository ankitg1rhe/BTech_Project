import java.util.*;
import java.lang.*;
import java.io.*;
import java.awt.Point;
import javafx.util.Pair;

/*class Pair{
	public int x, y;
	public Pair(int a, int b){
		x = a;
		y = b;
	}
}*/

public class Ankit{
	
	public static void main(String[] args){
		Point p = new Point(3, 4);
		Point q = new Point(3, 4);
	//	Pair q = new Pair(3, 4);
		HashMap<String,Boolean> hm = new HashMap<>();
		String s = new String("Ankit");hm.put(s,true);
		String r =new String("Ankit");
		
		if(hm.containsKey(r))
		System.out.println("Ankit");
		//System.out.println(q.x);
	}
}