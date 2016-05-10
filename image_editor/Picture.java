import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import java.lang.Math;

public class Picture {
	
	private File srcFile;
	private File destFile;
	private String operation;
	private int blurLength;
	private String head;
	private int width;
	private int height;
	private int maximum;
	private Pixel[][] result;
	
	public Picture(File srcFile, File destFile, String operation, int blurLength) throws FileNotFoundException {
		this.srcFile = srcFile;
		this.destFile = destFile;
		this.operation = operation;
		this.blurLength = blurLength;
		this.makeImage(srcFile);
		this.makeFile(destFile);
		
	}
	public void setTable(Pixel[][] temp){
		result = temp;
	}
	public void setHead(String temp){
		head = temp;
	}	
	public void setWidth(int temp){
		width = temp;
	}
	public void setHeight(int temp){
		height = temp;
	}
	public void setMaximum(int temp){
		maximum = temp;
	}

	public void makeImage(File srcFile) throws FileNotFoundException {
		Scanner scanner = new Scanner(srcFile);
		scanner.useDelimiter("(\\s+)(#[^\\n]*\\n)?(\\s*)|(#[^\\n]*\\n)(\\s+)|(#[^\\n]*\\n)");
		
		String head = scanner.next();
		int width = scanner.nextInt();		
		int height = scanner.nextInt();
		int maximum = scanner.nextInt();
		setHead(head);
		setWidth(width);
		setHeight(height);
		setMaximum(maximum);
		
		Pixel[][] table = new Pixel[height][width];
		
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				int red = scanner.nextInt();
				int green = scanner.nextInt();
				int blue = scanner.nextInt();
				
				Pixel pixel = new Pixel(red, green, blue);
				table[i][j] = pixel;
				
			}
		}
		
		if(operation.equals("invert")){
			invert(table);
		} 
		else if(operation.equals("grayscale")){
			grayscale(table);
		} 
		else if(operation.equals("emboss")){
			emboss(table);
		} 
		else if(operation.equals("motionblur")){
			motionblur(table);
		} 
		
	}
	
	public void invert(Pixel[][] temp){
		Pixel[][] table = temp;
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				Pixel newpixel = new Pixel(Math.abs(maximum-table[i][j].getRed()), 
						Math.abs(maximum-table[i][j].getGreen()), Math.abs(maximum-table[i][j].getBlue()));
				table[i][j] = newpixel;			
			}
		}
		setTable(table);
				
	}
	public void grayscale(Pixel[][] temp){
		Pixel[][] table = temp;
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				int average = (table[i][j].getRed() + table[i][j].getGreen() + table[i][j].getBlue())/3;
				Pixel newpixel = new Pixel(average, average, average);
				table[i][j] = newpixel;			
			}
		}
		setTable(table);

	}
	public void emboss(Pixel[][] temp){
		Pixel[][] table = temp;
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){

				if((height-i-1) != 0 && (width-j-1) != 0){
					
					int redDiff = table[height-i-1][width-j-1].getRed()-table[height-i-2][width-j-2].getRed();
					int greenDiff = table[height-i-1][width-j-1].getGreen()-table[height-i-2][width-j-2].getGreen();
					int blueDiff = table[height-i-1][width-j-1].getBlue()-table[height-i-2][width-j-2].getBlue();
					int maxDiff = Math.max(Math.abs(redDiff), Math.max(Math.abs(greenDiff), Math.abs(blueDiff)));
					int finalValue = 0;
					if(maxDiff == Math.abs(redDiff)){
						finalValue = redDiff + 128;
					}
					else if(maxDiff == Math.abs(greenDiff)){
						finalValue = greenDiff + 128;
					}
					else if(maxDiff == Math.abs(blueDiff)){
						finalValue = blueDiff + 128;
					}

					if(finalValue < 0){
						finalValue = 0;
					}
					else if(finalValue > 255){
						finalValue = 255;
					}

					Pixel newpixel = new Pixel(finalValue, finalValue, finalValue);
					table[height-i-1][width-j-1] = newpixel;			
				}
				else{
					Pixel newpixel = new Pixel(128, 128, 128);
					table[height-i-1][width-j-1] = newpixel;								
				}
			}
		}
		setTable(table);
		
	}
	public void motionblur(Pixel[][] temp){
		Pixel[][] table = temp;
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				int red = 0;
				int green = 0;
				int blue = 0;
				boolean edge = false;
				for(int k = 0; k < blurLength; k++){
					if(j+k < width){
						red += table[i][j+k].getRed();
						green += table[i][j+k].getGreen();
						blue += table[i][j+k].getBlue();
					}
					else{
						Pixel newpixel = new Pixel(red/k, green/k, blue/k);
						table[i][j] = newpixel;
						edge = true;
						break;
					}
				}
				if(!edge){
					Pixel newpixel = new Pixel(red/blurLength, green/blurLength, blue/blurLength);
					table[i][j] = newpixel;			
				}
			}
		}
		setTable(table);
		
	}

	public void makeFile(File destFile) throws FileNotFoundException{
		PrintWriter writer = new PrintWriter(destFile);
		writer.println(head);
		writer.print(width + " " + height + "\n");
		writer.println(maximum);
		for(int i = 0; i < height; i ++){
			for(int j = 0; j < width; j++){
				writer.println(result[i][j].getRed());
				writer.println(result[i][j].getGreen());
				writer.println(result[i][j].getBlue());				
			}
		}
		writer.close();
	}
}