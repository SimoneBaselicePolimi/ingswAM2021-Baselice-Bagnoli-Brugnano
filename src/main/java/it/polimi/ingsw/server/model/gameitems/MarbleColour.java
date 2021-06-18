package it.polimi.ingsw.server.model.gameitems;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Representable;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerMarbleColourRepresentation;
import it.polimi.ingsw.utils.Colour;

import java.util.List;
import java.util.Optional;

/**
 * This class represents the coloured Marble which fill the Market matrix. Each Marble is characterized by an ID.
 * Each Marble can be used by a Player to obtain certain types of resources or faith points. There are also some
 * special Marbles which can be used to gain a generic type of resources if activated by a special Leader card power.
 */
public class MarbleColour extends RegisteredIdentifiableItem implements Representable<ServerMarbleColourRepresentation> {
	/**
	 * Optional type of resource obtainable with this Marble
	 */
	private Optional<ResourceType> resourceType;

	/**
	 * Number of faith points given to the Player who owns this Marble
	 */
	private int faithPoints;

	/**
	 * Marble that can be transformed into a generic type of resource by activating a special Leader card power
	 */
	private boolean isSpecialMarble;

	/**
	 * Marble colour, specified as a list (in case of multi-coloured marbles) of RGB colour codes
	 */
	private List<Colour> marbleColour;

	/**
	 * Class constructor.
	 * @param marbleID ID which identifies this specific Marble
	 * @param gameItemsManager a reference to gameItemsManager is needed to register the new MarbleColour object
	 *                          (see {@link RegisteredIdentifiableItem})
	 * @param resourceType optional type of resource obtainable with this Marble
	 * @param faithPoints number of faith points obtainable by this Marble
	 * @param isSpecialMarble boolean which states if this Marble can be transformed into a generic type of resource
	 *                        by activating a special Leader card power
	 */
	public MarbleColour(
		String marbleID,
		GameItemsManager gameItemsManager,
		Optional<ResourceType> resourceType,
		int faithPoints,
		boolean isSpecialMarble
	) {
		super(marbleID, gameItemsManager);
		this.resourceType = resourceType;
		this.faithPoints = faithPoints;
		this.isSpecialMarble = isSpecialMarble;
	}

	/**
	 * Class constructor.
	 * @param marbleID ID which identifies this specific Marble
	 * @param gameItemsManager a reference to gameItemsManager is needed to register the new MarbleColour object
	 *                          (see {@link RegisteredIdentifiableItem})
	 * @param resourceType optional type of resource obtainable with this Marble
	 * @param faithPoints number of faith points obtainable by this Marble
	 * @param isSpecialMarble boolean which states if this Marble can be transformed into a generic type of resource
	 *                        by activating a special Leader card power
	 * @param marbleColour marble colour, specified as a list (in case of multi-coloured marbles) of RGB colour codes
	 */
	public MarbleColour(
		String marbleID,
		GameItemsManager gameItemsManager,
		Optional<ResourceType> resourceType,
		int faithPoints,
		boolean isSpecialMarble,
		List<Colour> marbleColour
	) {
		super(marbleID, gameItemsManager);
		this.resourceType = resourceType;
		this.faithPoints = faithPoints;
		this.isSpecialMarble = isSpecialMarble;
		this.marbleColour = marbleColour;
	}

	/**
	 * Method to get the specific type of resources obtainable by the Player who owns this Marble.
	 * @return optional type of resource associated to this Marble
	 */
	public Optional<ResourceType> getResourceType() {
		return resourceType;
	}

	/**
	 * Method to get the number of fatih points obtainable by the Player who owns this Marble.
	 * @return number of faith points associated to this Marble
	 */
	public int getFaithPoints() {
		return faithPoints;
	}

	/**
	 * Method which states if this Marble can be transformed into a generic type of resource by activating
	 * a special Leader card power.
	 * @return true if this is special Marble, false otherwise
	 */
	@JsonGetter("isSpecialMarble")
	public boolean isSpecialMarble() {
		return isSpecialMarble;
	}

	@JsonIgnore
	@Override
	public ServerMarbleColourRepresentation getServerRepresentation() {
		return new ServerMarbleColourRepresentation(itemID, resourceType, faithPoints, isSpecialMarble, marbleColour);
	}

	@JsonIgnore
	@Override
	public ServerMarbleColourRepresentation getServerRepresentationForPlayer(Player player) {
		return getServerRepresentation();
	}
}
