package it.polimi.ingsw.server.model.gameitems;

import java.util.Optional;

/**
 * This class represents the coloured Marble which fill the Market matrix. Each Marble is characterized by an ID.
 * Each Marble can be used by a Player to obtain certain types of resources or faith points. There are also some
 * special Marbles which can be used to gain a generic type of resources if activated by a special Leader card power.
 */
public class MarbleColour {
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
	 * ID which identifies this specific Marble
	 */
	private String marbleID;

	/**
	 * Class constructor.
	 * @param resourceType optional type of resource obtainable with this Marble
	 * @param faithPoints number of faith points obtainable by this Marble
	 * @param isSpecialMarble boolean which states if this Marble can be transformed into a generic type of resource
	 *                        by activating a special Leader card power
	 * @param marbleID ID which identifies this specific Marble
	 */
	public MarbleColour(Optional<ResourceType> resourceType, int faithPoints, boolean isSpecialMarble, String marbleID) {
		this.resourceType = resourceType;
		this.faithPoints = faithPoints;
		this.isSpecialMarble = isSpecialMarble;
		this.marbleID = marbleID;
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
	public boolean isSpecialMarble() {
		return isSpecialMarble;
	}

	/**
	 * Method to get the unique ID which identifies this specific Marble.
	 * @return ID of this Marble
	 */
	public String getMarbleID() {
		return marbleID;
	}

	/**
	 * Override of the equals method used to compare the equality between two Marbles.
	 * @param o Object to compare to this Marble
	 * @return true if the Object passed as parameter is identified by the same Marble ID of the Marble
	 * which invokes this method, false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof MarbleColour)) return false;
		MarbleColour m = (MarbleColour) o;
		return (marbleID.equals(m.marbleID));
	}

	/**
	 * Override of the hashCode method used to return the hash code of this Marble.
	 * @return hash code of this Marble based on its ID
	 */
	@Override
	public int hashCode() {
		return marbleID.hashCode();
	}

}
