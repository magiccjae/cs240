package listem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GrepImpl extends Mysuper implements Grep {
	
	public GrepImpl(){
	}
	
	/**
	 * Find lines that match a given pattern in files whose names match another
	 * pattern
	 * 
	 * @param directory The base directory to look at files from
	 * @param fileSelectionPattern Pattern for selecting file names
	 * @param substringSelectionPattern Pattern to search for in lines of a file
	 * @param recursive Recursively search through directories
	 * @return A Map containing files that had at least one match found inside them.
	 * Each file is mapped to a list of strings which are the exact strings from
	 * the file where the <code>substringSelectionPattern</code> was found.
	 */
	public Map<File, List<String>> grep(File directory, String fileSelectionPattern, 
			String substringSelectionPattern, boolean recursive){
		Map<File, List<String>> mymap = new HashMap<File, List<String>>();
		ArrayList<File> myfiles = new ArrayList();
		myfiles = super.initialsearch(directory, fileSelectionPattern, recursive);
		
		for(int i = 0; i < myfiles.size(); i++){
			List<String> word = process(myfiles.get(i), substringSelectionPattern);
			if(word.size() > 0){
				mymap.put(myfiles.get(i), word);
			}
		}
		
		return mymap;
	}
	
	public List<String> process(File temp, String substringSelection){
		List<String> word = new ArrayList();
		Pattern p = Pattern.compile(substringSelection);
			
		try {
			Scanner scanner = new Scanner(temp);
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				Matcher m = p.matcher(line);
				if(m.find()){
					word.add(line);
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return word;
	}
}
