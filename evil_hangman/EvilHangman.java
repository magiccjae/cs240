package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class EvilHangman implements EvilHangmanGame {
	
	Set<String> initial = new TreeSet<String>();
	Map<String, Set<String>> partition = new TreeMap<String, Set<String>>();
	int length;
	String key;	
	public EvilHangman(){
		length = 0;
		key = "";
	}
	
	public int getInitialsize(){
		return initial.size();
	}
	public String getWord(){
		
		return initial.iterator().next();
	}
	public void setLength(int length){
		this.length = length;
	}
	public String getKey(){
		return key;
	}
	public void startGame(File dictionary, int wordLength){
		try {
			// getting all the words of of the same length as wordLength in the dictionary
			initial.clear();
			setLength(wordLength);
			for(int i = 0; i < length; i++){
				key = key + "-";
			}
			Scanner scanner = new Scanner(dictionary);
			while(scanner.hasNext()){
				String word = scanner.next();
				if(word.length() == wordLength){
					initial.add(word);
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Make a guess in the current game.
	 * 
	 * @param guess The character being guessed
	 * @return The set of strings that satisfy all the guesses made so far
	 * in the game, including the guess made in this call. The game could claim
	 * that any of these words had been the secret word for the whole game. 
	 */
	public Set<String> makeGuess(char guess){
		
		partition.clear();
		// generating map done in this for loop
		for(String word: initial){
			String currentkey = makeKey(word,guess);
			if(partition.containsKey(currentkey)){
				partition.get(currentkey).add(word);
			}
			else{
				Set<String> newset = new TreeSet<String>();
				newset.add(word);
				partition.put(currentkey, newset);
			}
		}
				
		Set<String> theone = new TreeSet<String>();
		theone = getTheone(guess);
		initial = theone;
		return theone;
	}
	public Set<String> getTheone(char guess){
		String mostkey = getKey();
		Set<String> mostset = new TreeSet<String>();
		for(Map.Entry<String, Set<String>> entry: partition.entrySet()){
			if(entry.getValue().size() > mostset.size()){
				mostset = entry.getValue();
				mostkey = entry.getKey();
			}
			// when both set sizes are same, go to letter occurrence checking
//			else if(entry.getValue().size() == mostset.size()){ 
//				int currentkeycount = 0;
//				int mostkeycount = 0;
//				for(int i = 0; i < length; i++){
//					if(entry.getKey().charAt(i) == guess){
//						currentkeycount++;
//					}
//					if(mostkey.charAt(i) == guess){
//						mostkeycount++;
//					}
//				}
//				// less letter occurrence will replace MOSTSET
//				if(currentkeycount < mostkeycount){
//					mostset = entry.getValue();
//					mostkey = entry.getKey();
//				}
//				// same letter occurrence, go to rightmost checking
//				else if(currentkeycount == mostkeycount){
//					for(int i = 0; i < length; i++){
//						if(entry.getKey().charAt(length-i-1) == guess && mostkey.charAt(length-i-1) != guess){
//							mostset = entry.getValue();
//							mostkey = entry.getKey();
//							break;
//						}
//						else if(entry.getKey().charAt(length-i-1) != guess && mostkey.charAt(length-i-1) == guess){
//							break;
//						}
//					}
//				}
//				
//			}
		}
		
		key = mostkey;
		return mostset;
		
	}
	public String makeKey(String word, char guess){
		
		StringBuilder sb = new StringBuilder(length);
		for(int i = 0; i < length; i++){
			if(word.charAt(i) == guess){
				sb.append(guess);
			}
			else{
				sb.append(key.charAt(i));
			}
		}
		return sb.toString();
	}
	
}
