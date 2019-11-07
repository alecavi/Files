package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author Alessandro Cavicchioli
 * @version 1.0
 */
public class Files
{	
	private static Scanner scan = new Scanner(System.in);
	
	// ## COPY THE FOLLOWING TWO LINES OF CODE AS FIELDS INTO YOUR 'FILES' CLASS - DON'T MODIFY THEM
	private static final String crypt1 = "cipherabdfgjk";
	private static final String crypt2 = "lmnoqstuvwxyz";

	// ## COPY THE FOLLOWING METHOD INTO YOUR 'FILES' CLASS TOO - AGAIN, DON'T MODIFY ITS CONTENTS
	/**
	 * method to encipher and decipher a given String using parallel arrays (crypt1 & crypt2)
	 *
	 * @param text A String containing text that is to be enciphered or deciphered
	 * @return A new String containing the result, e.g. the en/deciphered version of the String provided as an input
	 */
	private static String cipherDecipherString(String text)
	{
	    // declare variables we need
	    int i, j;
	    boolean found = false;
	    String temp="" ; // empty String to hold converted text

	    for (i = 0; i < text.length(); i++) // look at every character in text
	    {
	        found = false;
	        if ((j = crypt1.indexOf(text.charAt(i))) > -1) // is char in crypt1?
	        {           
	            found = true; // yes!
	            temp = temp + crypt2.charAt(j); // add the cipher character to temp
	        } 
	        else if ((j = crypt2.indexOf(text.charAt(i))) > -1) // and so on
	        {
	            found = true;
	            temp = temp + crypt1.charAt(j);
	        }

	        if (! found) // to deal with cases where char is NOT in crypt2 or 2
	        {
	            temp = temp + text.charAt(i); // just copy across the character
	        }
	    }
	    return temp;
	}

	public void printFile(String path) //path is src/data/*.txt
	{
		try(BufferedReader reader = new BufferedReader(new FileReader(path))) //will this be consistent in a jar?
		{		
			String nextLine = reader.readLine();
			while(nextLine != null)
			{
				System.out.println(nextLine);
				nextLine = reader.readLine();
			}
		} 
		catch(FileNotFoundException e) //from the FileReader constructor
		{
			System.out.println("File" + e + "does not exist");
		}
		catch(IOException e) //From readLine();
		{
			System.out.println("IO Error reading from file: " + e);
		}
	}
	
	public void writeToFile(String path)
	{
		try(PrintWriter writer = new PrintWriter(path))
		{
			while(true)
			{
				String line = scan.nextLine();
				
				if(line.isEmpty()) break;
				
				writer.print(line + "\n");
			}
		} 
		catch(FileNotFoundException e)
		{
			System.out.println("File" + e + "cannot be created or opened for writing");
		}
	}
	
	public void copyFile(String from, String to)
	{
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(from));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Source file not found (File" + e + ")");
		}
		
		PrintWriter writer = null;
		try
		{
			writer = new PrintWriter(to);
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Destination file not found (File" + e + ")");		
		}
		
		try
		{
			String nextLine = reader.readLine();
			
			while(nextLine != null)
			{
				writer.print(nextLine + "\n");
				nextLine = reader.readLine();
			}
			reader.close();
			writer.close();
		}
		catch(IOException e)
		{
			System.out.println("IO Error reading from file: " + e);
		}
	}
	
	public void runFileTests()
	{	
		System.out.println("Select an operation");
		System.out.println(" - \"read\" prints the specified file");
		System.out.println(" - \"write\" writes user input to the specified file");
		
		String input = scan.nextLine();
		
		switch(input)
		{
		case "read":
			System.out.println("Input a path");
			printFile(scan.nextLine());
			break;
		case "write":
			System.out.println("Input a path");
			writeToFile(scan.nextLine());
			break;
		case "copy":
			System.out.println("Input the path to the source file");
			String from = scan.nextLine();
			System.out.println("Input the path to the destination file");
			String to = scan.nextLine();
			copyFile(from, to);
			break;
		default:
			System.out.println("Command not recognised");
		}
	}
	
	public static void main(String[] args)
	{
		Files files = new Files();
		files.runFileTests();
	}
}


























