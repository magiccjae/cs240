package listem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Mysuper {

	private	ArrayList<File> myfiles = new ArrayList();
	Mysuper(){
		myfiles.clear();
	}
	
	protected ArrayList<File> initialsearch(File directory, String fileSelectionPattern, boolean recursive) {
		myfiles.clear();
		myfiles = search(directory, fileSelectionPattern, recursive);
		return myfiles;
	}
	
	protected ArrayList<File> search(File directory, String fileSelectionPattern, boolean recursive){
		
		Pattern p = Pattern.compile(fileSelectionPattern);
		
		File[] files = directory.listFiles();
		if(files != null){
			for(int i = 0; i < files.length; i++){
				Matcher m = p.matcher(files[i].getName());
				if(files[i].isDirectory() && recursive){
					search(files[i], fileSelectionPattern, recursive);
				}
				else if(files[i].isFile() && m.matches()){
					myfiles.add(files[i]);
				}
			}
		}
		
//		System.out.println(myfiles.size());
//		System.out.println(fileSelectionPattern);
//		for(int i =0; i < myfiles.size(); i++){
//			System.out.println(myfiles.get(i).getName());
//		}
//		System.out.println();
		
		return myfiles;
		
	}
	
}
