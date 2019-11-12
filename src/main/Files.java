package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.UnaryOperator;

import films.Menu;

/**
 * @author Alessandro Cavicchioli
 * @version 1.0
 */
public class Files
{	
	public static final Scanner scan = new Scanner(System.in);
	private Menu menu = new Menu();
	
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

	public boolean isReadable(String path)
	{
		File file = new File(path);
		return file.isFile() && file.canRead();
	}
	
	/**
	 * Reads the specified file and prints its contents
	 * @param path the file to read
	 */
	public void printFile(String path) //path is src/data/*.txt
	{
		if(!isReadable(path))
		{
			System.out.println("Error: " + path + " is not a readable file");
			return;
		}
		
		try(BufferedReader reader = new BufferedReader(new FileReader(path)))
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
			System.out.println("File" + path + " does not exist");
		}
		catch(IOException e) //From readLine();
		{
			System.out.println("IO Error reading from file: " + path);
		}
	}
	
	/**
	 * Prompts the user for lines of input and writes them to the specified file
	 * @param path the file to write to
	 */
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
			System.out.println("File" + path + "cannot be created or opened for writing");
		}
	}
	
	/**
	 * For each line in the input file, applies {@code operation} to it and writes the returned value to
	 * the output file
	 * @param from the file to read from
	 * @param to the file to write to
	 * @param operation a functional interface containing the operation to perform
	 */
	private void doFileToFile(String from, String to, UnaryOperator<String> operation)
	{
		if(!isReadable(from))
		{
			System.out.println("Error: " + from + " is not a readable file");
			return;
		}
		
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(from));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Source file not found (File" + from + ")");
		}
		
		PrintWriter writer = null;
		try
		{
			writer = new PrintWriter(to);
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Destination file not found (File" + to + ")");		
		}
		
		try
		{
			String nextLine = reader.readLine();
			
			while(nextLine != null)
			{
				writer.print(operation.apply(nextLine) + "\n");
				nextLine = reader.readLine();
			}
			reader.close();
			writer.close();
		}
		catch(IOException e)
		{
			System.out.println("IO Error reading from file: " + from);
		}
	}

	/**
	 * Copies all contents of the input file to the output file
	 * @param from the file to read from
	 * @param to the file to write to
	 */
	public void copyFile(String from, String to)
	{
		doFileToFile(from, to, (input) -> {return input;});
	}
	
	/**
	 * Reads the input file, deciphers it, and writes the results to the output file
	 * @param from the file to read from
	 * @param to the file to write to
	 */
	public void decipherMystery(String from, String to)
	{
		doFileToFile(from, to, (input) -> {return cipherDecipherString(input);});
	}
	
	/**
	 * If the string matches the regex {@code \\w+\\s\\w+\\s(?:\\d+(?:\\s|$))+},
	 * (one word, space, another word, space, any amount of integers separated either by
	 * spaces or by the end of the line/string)), formats it as if by {@code firstWord + "," secondWord + ":"
	 * "The average score is" + averageOfNumbers}, with two decimal digits on the average, then prints and
	 * returns the formatted string
	 * @param input the string to format
	 * @return the formatted string
	 * @throws IllegalArgumentException if {@code input} does not match the regex
	 */
	private String formatAsAverage(String input)
	{
		if(!input.matches("\\w+\\s\\w+\\s(?:\\d+(?:\\s|$))+")) 
			throw new IllegalArgumentException("Malformed input: " + input);
		
		String[] data = input.split("\\s");
		String[] numbers = Arrays.copyOfRange(data, 2, data.length);
		
		float average = 0;
		int sum = 0;
		for(String num : numbers)
		{
			sum += Float.parseFloat(num); //Cannot throw: If the string were invalid, we wouldn't have gotten here
		}
		average = (float)sum / numbers.length;

		String out = String.format("%2$s, %1$s: Average score is %3$.2f", data[0], data[1], average);
		System.out.println(out);
		return out;
	}
	
	/**
	 * Reads the input file and, for each person listed, writes the average of their scores to the output file
	 * @param from
	 * @param to
	 */
	public void writeAveragesToFile(String from, String to)
	{
		doFileToFile(from, to, (input) -> formatAsAverage(input));
	}
	
	public void write2DArrayToFile(String path)
	{
		int[][] matrix = new int[10][15];
		
		int filler = 0;
		for(int i=0; i<matrix.length; i++)
		{
			for(int j=0; j<matrix[i].length; j++)
			{
				matrix[i][j] = filler;
				filler++;
			}
		}
		
		try(PrintWriter writer = new PrintWriter(path))
		{
			for(int i=0; i<matrix.length; i++)
			{
				for(int j=0; j<matrix[i].length; j++)
				{
					writer.print(matrix[i][j] + "\t");
				}
				writer.print("\n");
			}
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File" + path + "cannot be created or opened for writing");
		}
	}
	
	/**
	 * Serializes input object and saves it to the specified file
	 * @param path the file to save to
	 * @param object the object to save
	 * @throws IllegalArgumentException if the object is not serializable
	 */
	public void serializeObject(String path, Object object)
	{
		if(!(object instanceof Serializable)) 
			throw new IllegalArgumentException("Object cannot be serialized");
		
		try(FileOutputStream fileOut = new FileOutputStream(path);
				ObjectOutputStream objOut = new ObjectOutputStream(fileOut))
		{
			objOut.writeObject(object);
		} 
		catch(FileNotFoundException e)
		{
			System.out.println("File" + path + "cannot be created or opened for writing");
		}
		catch(IOException e)
		{
			e.printStackTrace(); 
			/*
			 * may be thrown by fileOut when close() is automatically called. There is no documentation about why
			 * this would happen, so I can't deal with the error any better than this
			 */
		}
	}
	
	/**
	 * loads a serialized object from the specified file and deserializes it
	 * @param path the file to load from
	 * @return the loaded object
	 */
	public Object deserializeObject(String path)
	{
		try(FileInputStream fileIn = new FileInputStream(path);
				ObjectInputStream objIn = new ObjectInputStream(fileIn))
		{
			return objIn.readObject();
		} 
		catch(FileNotFoundException e)
		{
			System.out.println("File" + path + " does not exist");
		} 
		catch(IOException e)
		{
			e.printStackTrace();
			//may be thrown by fileIn when close() is automatically called. There is no documentation about why
			//this would happen, so I can't deal with the error any better than this
		} 
		catch(ClassNotFoundException e)
		{
			System.out.println("The data does not match a known data type");
		}
		return null;
	}
	
	/**
	 * Prints all the available commands
	 */
	private void printCommands()
	{
		System.out.println(" - \"read\" prints the specified file");
		System.out.println(" - \"write\" writes user input to the specified file");
		System.out.println(" - \"copy\" copies the first file's contents to the second file");
		System.out.println(" - \"decipher\" deciphers the input file and writes the results to"
				+ "the second file");
		System.out.println(" - \"average\" computes the average score of all people in first "
				+ "file, then, it prints the results and saves them to the second"
				+ "file");
		System.out.println(" - \"matrix\" builds a matrix, then writes it to the specified file");
		System.out.println(" - \"film\" switches to the film management menu");
		System.out.println(" - \"serialize\" saves the current film collection to the specified file");
		System.out.println(" - \"deserialize\" loads a film collection from the specified file");
		System.out.println(" - \"exit\" exits this program");	
	}
	
	/**
	 * Allows the user to select an operation and input the necessary parameters, then
	 * performs the specified operation
	 */
	public void runFileTests()
	{	
		printCommands();
		
		boolean shouldContinue = true;
		while(shouldContinue)
		{
			System.out.println("Input a command");
			String input = scan.nextLine();
			
			String from;
			String to;
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
				from = scan.nextLine();
				System.out.println("Input the path to the destination file");
				to = scan.nextLine();
				copyFile(from, to);
				break;
			case "decipher":
				System.out.println("Input the path to the source file");
				from = scan.nextLine();
				System.out.println("Input the path to the destination file");
				to = scan.nextLine();
				decipherMystery(from, to);
				break;
			case "average":
				System.out.println("Input the path to the source file");
				from = scan.nextLine();
				System.out.println("Input the path to the destination file");
				to = scan.nextLine();
				writeAveragesToFile(from, to);
				break;
			case "matrix":
				System.out.println("Input a path");
				write2DArrayToFile(scan.nextLine());
				break;
			case "film":
				menu.menu();
				break;
			case "serialize":
				System.out.println("Input a path");
				serializeObject(scan.nextLine(), this.menu);
				break;
			case "deserialize": 
				System.out.println("Input a path");
				Object obj = deserializeObject(scan.nextLine());
				if(obj instanceof Menu)
				{
					menu = (Menu)obj;
				}
				else
				{
					System.out.println("The specified path does not contain a film collection");
				}
				break;
			case "exit":
				shouldContinue = false;
				break;
			default:
				System.out.println("Command not recognised. Accepted commands are");
				printCommands();
			}
		}
	}
	
	public static void main(String[] args)
	{
		Files files = new Files();
		files.runFileTests();
		scan.close();
	}
}