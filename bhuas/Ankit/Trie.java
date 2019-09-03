import java.io.*;
import java.util.*;
import java.lang.*;

class Trie{
	private final int ALPHABATE_SIZE = 26;

	private class TrieNode{
		TrieNode[] children = new TrieNode[ALPHABATE_SIZE];
		
		boolean isEndOfWorld;
		String meaning;
		
		public TrieNode(){
			isEndOfWorld = false;
			meaning = "";
			for(int i=0; i<ALPHABATE_SIZE; i++) children[i] = null;
		}
	}

	private TrieNode root;

	public void delete(String key){
		int len = key.length();
		if(len > 0)
			deleteHelper(root, key, 0, len);
	}

	public boolean deleteHelper(TrieNode node, String key, int level, int length){
		if(node != null){
			if(level == length){
				if(node.isEndOfWorld){
					node.isEndOfWorld = false;
					if(isFreeNode(node)){
						return true;
					}
					return false;
				}
			}else{
				int index = key.charAt(level) - 'a';

				if(deleteHelper(node.children[index], key, level+1, length)){
					node.children[index] = null;
					return (node.isEndOfWorld == false && isFreeNode(node)); 
				}
			}
		}

		return false;
	}

	public boolean isFreeNode(TrieNode node){
		for(int i=0; i<ALPHABATE_SIZE; i++)
			if(node.children[i] != null) return false;
		return true;
	}
	
	public void insert(String key, String meaning){
		int level;
		int length = key.length();
		int index;
		TrieNode pCrawl = root;
		
		for(level=0; level<length; level++){
			index = key.charAt(level) - 'a';
			if(pCrawl.children[index] == null)
				pCrawl.children[index] = new TrieNode();
			
			pCrawl = pCrawl.children[index];
		}

		pCrawl.isEndOfWorld = true;
		pCrawl.meaning = meaning;
	}
	
	public String search(String key){
		int level;
		int length = key.length();
		int index;
		TrieNode pCrawl = root;
		
		for(level=0; level<length; level++){
			index = key.charAt(level) - 'a';
			if(pCrawl.children[index] == null)
				return "There's nothing here";
			
			pCrawl = pCrawl.children[index];
		}
		
		if(pCrawl != null && pCrawl.isEndOfWorld) return key + " : " + pCrawl.meaning;
		return "There's nothing here";
	}
	
	public Trie(){
		root = new TrieNode();
	}
}


















