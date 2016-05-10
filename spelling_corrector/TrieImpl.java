package spell;

import java.util.*;

/**
 * Your trie class should implement the Trie interface
 */
public class TrieImpl implements Trie{

	Node root = new Node();
	int wordcount = 0;
	int nodecount = 1;
	Set<String> words = new TreeSet<String>();
	
	public TrieImpl(){
		
	}
	
	public void add(String word) {
		word = word.toLowerCase();
		Node tracker = root;
		
		for(int i = 0; i < word.length(); i++){
			int position = word.charAt(i) - 'a';
			String temp = word.substring(0, i+1);
			
			if(nodecount == 1){
				Node newnode = new Node();
				newnode.setName(temp);
				root.nodeArray[position] = newnode;
				tracker = newnode;
				nodecount++;
			}
			else{
				if(!temp.equals(word))
				{
					if(tracker.nodeArray[position] == null){
						Node newnode = new Node();
						newnode.setName(temp);
						tracker.nodeArray[position] = newnode;
						tracker = newnode;
						nodecount++;
					}
					else{
						tracker = tracker.nodeArray[position];
					}
				}
				else{
					if(tracker.nodeArray[position] == null){
						Node newnode = new Node();
						newnode.setName(temp);
						tracker.nodeArray[position] = newnode;
						tracker = newnode;
						nodecount++;
						wordcount++;
						tracker.frequency++;
						words.add(temp);
					}
					else{
						tracker = tracker.nodeArray[position];
						tracker.frequency++;
						words.add(temp);
					}

				}
			}
		}
	}
	
	public Trie.Node find(String word) {
		Node tracker = root;		
		
		for(int i = 0; i < word.length(); i++){
			int position = word.charAt(i) - 'a';
			String temp = word.substring(0, i+1);

			if(!temp.equals(word)){
				if(tracker.nodeArray[position] == null)
				{
					return null;
				}
				else{
					tracker = tracker.nodeArray[position];	
				}
			}
			else{
				tracker = tracker.nodeArray[position];
				if(tracker.getValue() == 0)
				{
					return null;
				}
				return tracker;
			}
		}
		return null;
	}
	
	public int getWordCount() {
		return wordcount;
	}
	
	public int getNodeCount() {
		return nodecount;
	}
	@Override
	public String toString() {
		String output = "";
		Iterator<String> iterator = words.iterator();
		while(iterator.hasNext()) {
	        String setElement = iterator.next();
	        output = output + setElement + " " + Integer.toString(find(setElement).getValue()) + "\n"; 
		}
		return output;
	}
	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash*17 + wordcount;
		hash = hash*31 + nodecount;
		return hash;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null){
			return false;
		}
		if(this.toString().equals(o.toString())){
			return true;
		}
		return false;
	}

	public class Node implements Trie.Node{
	
		int frequency = 0;
		String name = null;
		Node[] nodeArray = new Node[26];
		
		public Node(){	
		}
		
		public void setName(String temp){
			this.name = temp;
		}
		public int getValue() {
			return frequency;
		}
	}
	
}