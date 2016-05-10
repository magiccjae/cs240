package spell;

import java.io.File;
import java.io.IOException;
import java.util.*;

import spell.SpellCorrector.NoSimilarWordFoundException;
import spell.Trie.Node;

public class SpellCorrectorImpl implements SpellCorrector {

	private Set<String> suggestion = new TreeSet<String>();
	private TrieImpl dictionary = new TrieImpl();

	public SpellCorrectorImpl(){

	}

	public void useDictionary(String dictionaryFileName) throws IOException {
		File srcFile = new File(dictionaryFileName);
		Scanner scanner = new Scanner(srcFile);
		dictionary = new TrieImpl();

		while(scanner.hasNext()){
			String word  = scanner.next();
			dictionary.add(word);
		}
		
	}

	public String suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException{
		String word = inputWord.toLowerCase();
		suggestion = new TreeSet<String>();
		
		if(dictionary.words.contains(word)){
			return word;
		}
		// edit distance 1
		deletion(word);
		transposition(word);
		alteration(word);
		insertion(word);
		int mostfrequent = 0;
		String finalword = "";
		for(String temp : suggestion){
			if(dictionary.words.contains(temp)){
				int frequency = dictionary.find(temp).getValue();
				if(mostfrequent < frequency){
					finalword = temp;
					mostfrequent = frequency;
				}
			}
		}
		if(finalword != "")
		{
			return finalword;
		}

		Set<String> suggestion1 = new TreeSet<String>();
		suggestion1.addAll(suggestion);
		suggestion.clear();
		// edit distance 2
		int mostfrequent1 = 0;
		String finalword1 = "";
		for(String temp : suggestion1){
			deletion(temp);
			transposition(temp);
			alteration(temp);
			insertion(temp);
			for(String temp1 : suggestion){
				if(dictionary.words.contains(temp1)){
					int frequency = dictionary.find(temp1).getValue();
					if(mostfrequent1 < frequency){
						finalword1 = temp1;
						mostfrequent1 = frequency;
					}
				}
			}
		}
		if(finalword1 != ""){
			return finalword1;
		}
		
		throw new NoSimilarWordFoundException();
	}

	public void deletion(String word){
		for(int i = 0; i < word.length(); i++){
			suggestion.add(word.substring(0, i) + word.substring(i+1, word.length()));
		}
	}
	public void transposition(String word){
		for(int i = 0; i < word.length()-1; i++){
			StringBuilder sb = new StringBuilder(word);
			char letter = sb.charAt(i);
			sb.deleteCharAt(i);
			sb.insert(i+1, letter);
			suggestion.add(sb.toString());
		}
	}
	public void alteration(String word){
		for(int i = 0; i < word.length(); i++){
			for(char letter = 'a'; letter <= 'z'; letter++){
				StringBuilder sb = new StringBuilder(word);
				sb.setCharAt(i, letter);
				if(!sb.toString().equals(word)){
					suggestion.add(sb.toString());
				}
			}
		}
	}
	public void insertion(String word){
		for(int i = 0; i < word.length()+1; i++){
			for(char letter = 'a'; letter <= 'z'; letter++){
				StringBuilder sb = new StringBuilder(word);
				sb.insert(i, letter);
				suggestion.add(sb.toString());
			}
		}	
	}

}
