package spell;

import java.io.IOException;

import spell.SpellCorrector.NoSimilarWordFoundException;

/**
 * A simple main class for running the spelling corrector
 */
public class Main {
	
	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 * @throws spell.SpellCorrectorImpl.NoSimilarWordFoundException 
	 */
	public static void main(String[] args) throws NoSimilarWordFoundException, IOException {
		
		String dictionaryFileName = args[0];
		String inputWord = args[1];
		
		SpellCorrectorImpl corrector = new SpellCorrectorImpl();
		
		corrector.useDictionary(dictionaryFileName);
		String suggestion = corrector.suggestSimilarWord(inputWord);
		
		System.out.println("Suggestion is: " + suggestion);
	}

}
