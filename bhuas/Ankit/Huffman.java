import java.util.*;
import java.io.*;

class HuffmanNode{
	int data;
	char c;
	
	HuffmanNode left;
	HuffmanNode right;
	
	public HuffmanNode(int data, char c){
		this.data = data;
		this.c = c;
		left = null;
		right = null;
	}
}

public class Huffman{
	
	public static void printCode(HuffmanNode root, String s, Map<Character, String> code){
		
		if(root.left == null && root.right == null && Character.isLetter(root.c)){
			System.out.println(root.c + ":" + s);
			code.put(root.c, s);
			return;
		}
		
		printCode(root.left, s + "0", code);
		printCode(root.right, s + "1", code);
	}

	public static String decodeHuffman(HuffmanNode root, String s, Map<Character, String> code){
		String ans = "";
		HuffmanNode curr = root;
		
		for(int i=0; i<s.length(); i++){
			if(s.charAt(i) == '0')	curr = curr.left;
			else curr = curr.right;
			
			if(curr.left == null && curr.right == null){
				ans += curr.c;
				curr = root;
			}
		}
		
		return ans;
	}
	
    public static void main(String[] lonar){
		Scanner in = new Scanner(System.in);

		String str = "geeksforgeeks";
		int[] freq = new int[125];
		for(int i = 0; i < str.length(); i++){
			freq[str.charAt(i)]++;
		}

		Map<Character, String> code = new HashMap<>();

		PriorityQueue<HuffmanNode> queue = 
					new PriorityQueue<HuffmanNode>(100, new Comparator<HuffmanNode>(){
						@Override
						public int compare(HuffmanNode x, HuffmanNode y){
							return x.data - y.data;
						}
					});
		
		for(int i=0; i<125; i++){
			if(freq[i] == 0) continue;
			
			char ch = (char) i;
			int da = freq[i];
			HuffmanNode hf = new HuffmanNode(freq[i], ch);
			queue.add(hf);
		}
		
		HuffmanNode root = null;
		while(queue.size() > 1){
			HuffmanNode x = queue.peek();
			queue.poll();
			
			HuffmanNode y = queue.peek();
			queue.poll();
			
			int pData = x.data + y.data ;
			char pC = '-';
			
			HuffmanNode f = new HuffmanNode(pData, pC);
			f.left = x;
			f.right = y;
			root = f;
			
			queue.add(f);
		}

		printCode(root, "", code);
		System.out.println();		System.out.println();		System.out.println();
		
		String encodeString = "";
		for(int i = 0; i < str.length(); i++){
			encodeString += code.get(str.charAt(i));
		}
		
		System.out.println("Encoded string: " + encodeString);
		System.out.println();		System.out.println();		System.out.println();
		
		String decodedString = decodeHuffman(root, encodeString, code);
		System.out.println(decodedString);
	}
}


