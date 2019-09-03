import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class MinHeap{
	private int[] heapArr;
	private int capacity;
	private int heapSize;

	private int parent(int i){ return (i-1)/2; }
	private int left(int i){	return 2*i + 1; }
	private int right(int i){ return 2*i + 2; }
	public int getMin(){ return heapArr[0]; }

	public MinHeap(int capacity){
		this.capacity = capacity;
		heapSize = 0;
		heapArr = new int[capacity];
	}

	public void deleteKey(int i){
		decreaseKey(i, Integer.MIN_VALUE);
		extractMin();
	}

	public int extractMin(){
		if(heapSize <= 0) return Integer.MIN_VALUE;

		if(heapSize == 1){
			heapSize--;
			return heapArr[0];
		}

		int root = heapArr[0];
		heapSize--;
		heapArr[0] = heapArr[heapSize];
		minHeapify(0);

		return root;
	}

	public void minHeapify(int i){
		int l = left(i);
		int r = right(i);
		int smallest = i;

		if(l < heapSize && heapArr[l] < heapArr[i])
			smallest = l;
		if(r < heapSize && heapArr[r] < heapArr[i])
			smallest = r;
		if(smallest != i){
			int temp = heapArr[smallest];
			heapArr[smallest] = heapArr[i];
			heapArr[i] = temp;
			minHeapify(smallest);
		}
	}

	public void decreaseKey(int i, int newVal){
		heapArr[i] = newVal;
		while(i != 0 && heapArr[parent(i)] > heapArr[i]){
			int temp = heapArr[parent(i)];
			heapArr[parent(i)] = heapArr[i];
			heapArr[i] = temp;
			i = parent(i);
		}
	}

	public void insertKey(int key){
		if(heapSize == capacity){
			System.out.println("Overflow!");
			return;
		}

		int i = heapSize;
		heapSize++;
		heapArr[i] = key;

		while(i != 0 && heapArr[parent(i)] > heapArr[i]){
			int temp = heapArr[parent(i)];
			heapArr[parent(i)] = heapArr[i];
			heapArr[i] = temp;
			i = parent(i);
		}
	}
}