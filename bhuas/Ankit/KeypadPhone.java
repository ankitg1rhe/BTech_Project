import java.util.*;
import java.lang.*;
import java.io.*;

class Ankit{
	static Map<Character, String> mp = new HashMap<>();
	static int[][] combination;
	static int[] arr = new int[10];
	
	static int comb(int n, int s){
		if(n < 1 || s<1) return 0;	if(n == 1) return 1;
		if(combination[n][s] != 0){
			System.out.println("Used dp");
			 return combination[n][s];
		}
		
		int co=0, i=1;
		while(i<n && i<=s){	co += comb(n-i, s);	i++; }
		
		if(n<=s) co += 1;
		System.out.println("Not Used dp");
		return (combination[n][s] = co);
	}
	
	static int solve(String s){
		s = s.toLowerCase();

		String ans = "";
		for(int i=0; i<s.length(); i++)	ans += mp.get(s.charAt(i));
		System.out.println(ans);
		
		int n=ans.length();
		combination = new int[n+1][5];
		int[] given = new int[n];
		for(int i=0; i<n; i++) given[i] = Integer.parseInt(ans.substring(i,i+1));
		
		int i=0, send, num, k, answer=1;
		while(i < n){
			send = 1;
			num = arr[given[i]];
			
			for(k=i+1; k<n; k++)
				if(given[k] == given[i]) send++;
				else break;
			
			answer *= comb(send, num);
			i = k;
		}
		
		return answer;
	}

    public static void main(String[] lonar) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		mp.put(' ', "0");
		mp.put('a', "2");	mp.put('b', "22");	mp.put('c', "222");
		mp.put('d', "3");	mp.put('e', "33");	mp.put('f', "333");	
		mp.put('g', "4");	mp.put('h', "44");	mp.put('i', "444");	
		mp.put('j', "5");	mp.put('k', "55");	mp.put('l', "555");	
		mp.put('m', "6");	mp.put('n', "66");	mp.put('o', "666");
		mp.put('p', "7");	mp.put('q', "77");	mp.put('r', "777");	mp.put('s', "7777");
		mp.put('t', "8");	mp.put('u', "88");	mp.put('v', "888");
		mp.put('w', "9");	mp.put('x', "99");	mp.put('y', "999");	mp.put('z', "9999");
		
		arr[0] = 0;	arr[1] = 1; arr[2] = 3; arr[3] = 3; arr[4] = 3; arr[5] = 3;
		arr[6] = 3; arr[7] = 4; arr[8] = 3; arr[9] = 4;
		
	    int t = Integer.parseInt(br.readLine());
	    while(t-- > 0){
	        String s = br.readLine();
	        int ans = solve(s);
			System.out.println(ans);
	    }
    }
}


/*
		

*/