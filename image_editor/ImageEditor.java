import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class ImageEditor {

	public static void main(String[] args) throws IOException {
		
		String infile;
		String outfile;
		String operation;
		int blurLength = 0;
		
		// not enough arguments
		try{
			infile = args[0];
			outfile = args[1];
			operation = args[2];
		}
		catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("USAGE: java ImageEditor in-file out-file (grayscale|invert|emboss|motionblur motion-blur-length");
			return;
		}
		
		// 
		try{
			if(operation.equals("motionblur")) {
				blurLength = Integer.parseInt(args[3]);
				if(blurLength < 0){
//					System.out.println("the number must be non-negative.");
					return;
				}
			}
			else if(args.length > 3) {
				throw new ArrayIndexOutOfBoundsException();
			}
			else if(!operation.equals("invert") && !operation.equals("grayscale") && !operation.equals("emboss")){
				System.out.println("USAGE: java ImageEditor in-file out-file (grayscale|invert|emboss|motionblur motion-blur-length");
				return;
			}
		}
		catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("USAGE: java ImageEditor in-file out-file (grayscale|invert|emboss|motionblur motion-blur-length");
			return;			
		}	
		
		File srcFile = new File(args[0]);
		File destFile = new File(args[1]);	
		Picture picture = new Picture(srcFile, destFile, operation, blurLength);
		
		
	}
}