package it.polimi.ingsw.server.model.gameitems;

import java.util.Optional;

public abstract class MarbleColour {

	public MarbleColour(Optional<ResourceType> resourceType, int faithPoints, boolean isSpecialMarble, String marbleID) {

	}

	public Optional<ResourceType> getResourceType() {
		return null;
	}

	public int getFaithPoints() {
		return 0;
	}

	public boolean isSpecialMarble() {
		return false;
	}

	public String getMarbleID() {
		return null;
	}

}
