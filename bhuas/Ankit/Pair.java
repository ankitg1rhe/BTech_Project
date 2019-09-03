import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;
import javafx.util.Pair;

public class Ankit {

	static Pair<String, Integer> getMaximum(ArrayList<Pair<String, Integer>> li){
		int max = Integer.MIN_VALUE;
		Pair<String, Integer> ans = new Pair<String, Integer>("", 0);
		
		for(Pair<String, Integer> temp : li){
			int val = temp.getValue();
			if(val > max){
				ans = temp;
				max = val;
			}
		}
		
		return ans;
	}

    public static void main(String[] lonar){
		Scanner in = new Scanner(System.in);
		int n = 5;
		ArrayList<Pair<String, Integer>> li = new ArrayList<>();
		
		li.add(new Pair<String, Integer>("Student A", 90));
		li.add(new Pair<String, Integer>("Student B", 54));
		li.add(new Pair<String, Integer>("Student C", 99));
		li.add(new Pair<String, Integer>("Student D", 88));
		li.add(new Pair<String, Integer>("Student F", 89));
		
		Pair<String, Integer> ans = getMaximum(li);
		System.out.println(ans.getKey() + " is top scorer with score: " + ans.getValue());
	}
}
