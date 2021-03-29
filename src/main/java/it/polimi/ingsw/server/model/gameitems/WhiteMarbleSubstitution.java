package it.polimi.ingsw.server.model.gameitems;

/**
 * This class represents the White Marble Substitution power held by some Game Items (Leader Cards).
 * A Player who owns this Substitution Item, when taking Resources from the Market, can choose which Resource to take
 * (from those given by this Substitution Item) for each of the white Marbles.
 */
public class WhiteMarbleSubstitution {
	/**
	 * Resource type the Player can substitute to a White Marble taken from the Market
	 */
	private final ResourceType resourceTypeToSubstitute;

	/**
	 * Class constructor.
	 * @param resourceTypeToSubstitute resource type to substitute to a White Marble
	 * @throws IllegalArgumentException if a null Resource type is passed as parameter
	 */
	public WhiteMarbleSubstitution(ResourceType resourceTypeToSubstitute) throws IllegalArgumentException{
		if(resourceTypeToSubstitute == null)
			throw new IllegalArgumentException();
		this.resourceTypeToSubstitute = resourceTypeToSubstitute;
	}

	/**
	 * Method to get the specific Resource type which can be substituted to a White Marble.
	 * @return resource type to substitute to a White Marble
	 */
	ResourceType getResourceTypeToSubstitute() {
		return resourceTypeToSubstitute;
	}

}
