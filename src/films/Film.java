package films;

import java.io.Serializable;

import main.Files;

/**
 * Represents a single file
 * @author Alessandro Cavicchioli
 * @version 1.0
 *
 */
public class Film
implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String title;
	private String director;
	private int duration;
	private float cost;
	
	/**
	 * Default constructor. Initialises all strings to "" and all numbers to 0
	 */
	public Film()
	{
		title = "";
		director = "";
		duration = 0;
		cost = 0;
	}
	
	/**
	 * Returns the title of this film
	 * @return The title
	 */
	public String getTitle()
	{
		return title;
	}
	
	/**
	 * Returns the name of the director of this film
	 * @return The name of the director
	 */
	public String getDirector()
	{
		return director;
	}
	
	/**
	 * Returns the duration of this film, expressed in minutes
	 * @return The duration
	 */
	public int getDuration()
	{
		return duration;
	}
	
	/**
	 * Returns the purchase cost of this film, expressed in pence
	 * @return The cost in pence
	 */
	public float getCost()
	{
		return cost;
	}
	
	/**
	 * Sets all details of this film, in order
	 * @param title The title of the film
	 * @param director The name of the director of the film
	 * @param duration The duration of the film in minutes
	 * @param cost The purchase cost of the film.
	 */
	public void setAllDetails(String title, String director, int duration, float cost)
	{
		this.title = title;
		this.director = director;
		this.duration = duration;
		this.cost = cost;
	}
	
	/**
	 * Sets all details if this film, one by one, by prompting the user to input their values.
	 * This method does not perform any kind of input check.
	 */
	public void promptForDetails()
	{	
		System.out.println("Input title");
		title = Files.scan.nextLine();
		
		System.out.println("Input director name");
		director = Files.scan.nextLine();
		
		System.out.println("Input duration in minutes");
		duration = Files.scan.nextInt();
		
		System.out.println("Input cost");
		cost = Files.scan.nextFloat();
		
		Files.scan.nextLine(); 
		/* nextFloat() does not consume the end of line char input by pressing enter. To prevent the next
		 * call to nextLine() from picking it up, we consume it here
		 */
	}
	
	/**
	 * Displays all details of this film.
	 */
	public void displayDetails()
	{
		System.out.println("Title: " + title);
		System.out.println("Director name: " + director);
		System.out.println("Duration: " + duration + " minutes");
		System.out.println("Cost: " + cost);
	}
}