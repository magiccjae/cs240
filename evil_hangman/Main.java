package hangman;

import java.io.File;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Main {

	public static void main(String[] args) {
		
		String fileName = args[0];
		int wordLength = Integer.parseInt(args[1]);
		int guesses = Integer.parseInt(args[2]);
		EvilHangman hangman = new EvilHangman();
		File dictionary = new File(fileName);
		hangman.startGame(dictionary, wordLength);
		
		String key = "";
		for(int i = 0; i < wordLength; i++){
			key = key+"-";
		}
		Set<String> used = new TreeSet<String>();
		
		
		while(guesses > 0){
			String used_letters = "";
			
			for(String letter: used){
				used_letters = used_letters + " " + letter;
			}
			System.out.println("You have " + guesses + " left");
			System.out.println("Used letters:" + used_letters);
			System.out.println("Word: " + key); // print key here
			System.out.print("Enter guess: ");
			Scanner scan = new Scanner(System.in);
			boolean invalid = true;
			char guess = 0;
			while(invalid){
				String input = scan.next().toLowerCase();
				if(!input.matches("[a-z]")){
					System.out.println("Invald input");
				}
				else if(used.contains(input)){
					System.out.println("You already used that letter");
				}
				else{
					invalid = false;
					used.add(input);
					guess = input.charAt(0);
				}
			}
			hangman.makeGuess(guess);
			key = hangman.getKey();
			int frequency = 0;
			for(int i = 0; i < key.length(); i++){
				if(key.charAt(i) == guess){
					frequency++;
				}
			}
			if(frequency == 0){
				guesses--;
				if(guesses == 0){
					System.out.println("You lose!");
					System.out.println("The word was: " + hangman.getWord());
				}
				else{
					System.out.println("Sorry, there is no " + guess + "\'s\n");					
				}
			}
			else{
				if(!key.contains("-")){
					System.out.println("You Win!");
					System.out.println("The word was: " + key);
					guesses = 0;
				}
				else{
					System.out.println("Yes, there is " + frequency + " " + guess + "\n");					
				}
			}
		}
				
	}

}
