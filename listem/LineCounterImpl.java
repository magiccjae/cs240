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

public class LineCounterImpl extends Mysuper implements LineCounter {
	
	public LineCounterImpl(){
	}
	
	/**
	 * Count the number of lines in files whose names match a given pattern.
	 * 
	 * @param directory The base directory to look at files from
	 * @param fileSelectionPattern Pattern for selecting file names
	 * @param recursive Recursively search through directories
	 * @return A Map containing files whose lines were counted. Each file is mapped
	 * to an integer which is the number of lines counted in the file.
	 */
	public Map<File, Integer> countLines(File directory, String fileSelectionPattern, 
			boolean recursive){
		
		Map<File, Integer> mymap = new HashMap<File, Integer>();
		ArrayList<File> myfiles = new ArrayList();
		
		myfiles = super.initialsearch(directory, fileSelectionPattern, recursive);
		
		for(int i = 0; i < myfiles.size(); i++){
			Integer count = process(myfiles.get(i));
			if(count > 0){
				mymap.put(myfiles.get(i), count);
			}
		}
		
		return mymap;
	}
	
	public Integer process(File temp){
		Integer count = 0;
			
		try {
			Scanner scanner = new Scanner(temp);
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				count ++;
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}


}
