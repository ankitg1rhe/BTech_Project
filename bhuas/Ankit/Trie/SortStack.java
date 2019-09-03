import java.util.*;
import java.lang.*;
import java.io.*;

class Ankit{

	static void sort(Stack<Integer> stack){
		if(stack.empty()) return;
		int n = stack.pop();
		sort(stack);
		putInPos(stack, n);
	}

	static void putInPos(Stack<Integer> stack, int n){
		if(stack.empty()){	stack.push(n); return;	}

		int k = stack.pop();
		if(k >= n){
			stack.push(k);
			stack.push(n);
			return;
		}else{
			putInPos(stack, n);
			stack.push(k);
		}
	}

	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		Stack<Integer> stack = new Stack<>();
		for(int i=0; i<n; i++)
			stack.push(in.nextInt());

		sort(stack);
		printStack(stack);
	}

	static void printStack(Stack<Integer> stack){
		while(!stack.empty())
			System.out.println(stack.pop());
	}
}
