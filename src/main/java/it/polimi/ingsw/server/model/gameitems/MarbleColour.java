package it.polimi.ingsw.server.model.gameitems;

import java.util.Optional;

public class MarbleColour {
	private Optional<ResourceType> resourceType;
	private int faithPoints;
	private boolean isSpecialMarble;
	private String marbleID;

	public MarbleColour(Optional<ResourceType> resourceType, int faithPoints, boolean isSpecialMarble, String marbleID) {
		this.resourceType = resourceType;
		this.faithPoints = faithPoints;
		this.isSpecialMarble = isSpecialMarble;
		this.marbleID = marbleID;
	}

	public Optional<ResourceType> getResourceType() {
		return resourceType;
	}

	public int getFaithPoints() {
		return faithPoints;
	}

	public boolean isSpecialMarble() {
		return isSpecialMarble;
	}

	public String getMarbleID() {
		return marbleID;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof MarbleColour)) return false;
		MarbleColour m = (MarbleColour) o;
		return (marbleID.equals(m.marbleID));
	}

	@Override
	public int hashCode() {
		return marbleID.hashCode();
	}

}
