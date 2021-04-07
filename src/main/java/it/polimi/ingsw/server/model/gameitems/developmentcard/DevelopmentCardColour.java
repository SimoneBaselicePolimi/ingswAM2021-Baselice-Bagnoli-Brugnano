package it.polimi.ingsw.server.model.gameitems.developmentcard;

/**
 * This enumeration lists all the Development Card Colours present in the Game.
 */
public enum DevelopmentCardColour {
	GREEN("GREEN"),
	BLUE("BLUE"),
	YELLOW("YELLOW"),
	PURPLE("PURPLE");

	String serializationName;

	DevelopmentCardColour(String serializationName) {
		this.serializationName = serializationName;
	}

	public String serialize() {
		return this.serializationName;
	}

	public static DevelopmentCardColour deserialize(String serializationName) {
		for(DevelopmentCardColour cardColour : DevelopmentCardColour.values())
			if(cardColour.serializationName.equals(serializationName))
				return cardColour;
		throw new IllegalArgumentException("Deserialization failed: invalid serialization name");
	}

	}
