package films;

import java.io.Serializable;

import files.Files;

/**
 * The menu class used by the user to interact with a Collection
 * @author Alessandro Cavicchioli
 * @version 1.1
 */
public class Menu
implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Collection collection;
	
	/**
	 * Default constructor. Creates the collection the menu will refer to
	 */
	public Menu()
	{
		this.collection = new Collection();
	}
	
	/**
	 * Returns the collection stored in this menu instance 
	 * @return the collection
	 */
	public Collection getCollection()
	{
		return collection;
	}
	
	/**
	 * Sets the collection stored in this menu instance to the specified one
	 * @param collection the collection to set
	 */
	public void setCollection(Collection collection)
	{
		this.collection = collection;
	}
	
	/**
	 * Menu function to display the collection. Equivalent to collection.displayFilmDetails()
	 */
	public void displayCollection()
	{
		collection.displayFilmDetails();
	}
	
	/**
	 * Menu function to fill in the details of a single film.
	 * @param filmTitle The title of the film whose details will be set
	 */
	public void setFilmDetails(String filmTitle)
	{
		Film film = collection.searchFilm(filmTitle);
		if(film != null)
		{
			film.promptForDetails();
		}
		else
		{
			if(filmTitle.isEmpty())
			{
				System.out.println("There are already 3 films in the collection. There is no room for more");
			}
			else
			{
				System.out.println("No film with the specified title could be found");
			}
		}
	}
	
	public void menu()
	{
		System.out.println("Input a command");
		System.out.println("Accepted commands are:");
		System.out.println(" - disp: displays the collection");
		System.out.println(" - set: opens a dialogue to set the details of a film");
		System.out.println(" - exit: closes this menu");
		

		boolean shouldContinue = true;
		while(shouldContinue)
		{
			String input = Files.scan.nextLine();
			
			switch(input)
			{
				case "disp":
					displayCollection();
					break;
				case "set":
					System.out.println("Insert the title of the film to set. Insert nothing and press enter for a new film");
					setFilmDetails(Files.scan.nextLine());
					System.out.println("\nInput a command");
					break;
				case "exit":
					shouldContinue = false;
					break;
				default:
					System.out.println("Command not recognised");
					break;	
			}
		}
	}
	
}