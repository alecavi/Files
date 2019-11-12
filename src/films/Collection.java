package films;

import java.io.Serializable;

/**
 * Stores a list of Films
 * @author Alessandro Cavicchioli
 * @version 1.0
 */
public class Collection
implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Film[] films = new Film[3];
	
	/**
	 * Default constructor. Creates the actual film objects in the collection array.
	 */
	Collection()
	{
		for(int i=0; i<films.length; i++)
		{
			films[i] = new Film();
		}
	}
	
	/**
	 * Prompts the user to input the details of all films in the collection, and sets them
	 */
	public void promptForFilmsDetails()
	{
		for(int i=0; i<films.length; i++)
		{
			films[i].promptForDetails();
			System.out.println();
		}
	}
	
	/**
	 * Displays the details of all films in the collection.
	 */
	public void displayFilmDetails()
	{
		for(int i=0; i<films.length; i++)
		{
			if(films[i].getTitle().isEmpty()) continue; //skips empty positions in the list
			
			films[i].displayDetails();
			System.out.println();
		}
	}
	
	/**
	 * Searches the collection for a film with the specified title, and returns it
	 * @param title The title to look for
	 * @return The first film with the specified title, or null if no such film was found
	 */
	//This method exists independently from searchAndDisplayFilm() to allow for code reuse in the Menu class.
	public Film searchFilm(String title)
	{
		for(int i=0; i<films.length; i++)
		{
			if(films[i].getTitle().equals(title))
			{
				return films[i];
			}
		}
		return null;
	}
	
	/**
	 * Searches the collection for a film with the specified title and displays its details,
	 * or an error message if there is no such film
	 * @param title The title to look for
	 */
	public void searchAndDisplayFilm(String title)
	{
		Film film = searchFilm(title);
		if(film != null)
		{
			film.displayDetails();
		}
		else
		{
			System.out.println("No film with the specified title could be found");
		}
	}
	
	/**
	 * Adds the cost of all films in the collection and displays the result
	 */
	public void displayTotalCost()
	{
		double out = 0;
		for(int i=0; i<films.length; i++)
		{
			out += films[i].getCost();
		}
		System.out.println("Total cost: " + out);
	}
	
	/**
	 * Adds the duration of all films in the collection and displays the result
	 */
	public void displayTotalDuration()
	{
		int out = 0;
		for(int i=0; i<films.length; i++)
		{
			out += films[i].getDuration();
		}
		System.out.println("Total duration: " + out);
	}
}







































