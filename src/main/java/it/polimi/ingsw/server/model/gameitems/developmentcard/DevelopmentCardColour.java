package it.polimi.ingsw.server.model.gameitems.developmentcard;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This enumeration lists all the Development Card Colours present in the Game.
 */
public enum DevelopmentCardColour {
	@JsonProperty("GREEN") GREEN,
	@JsonProperty("BLUE") BLUE,
	@JsonProperty("YELLOW") YELLOW,
	@JsonProperty("PURPLE") PURPLE;
}
