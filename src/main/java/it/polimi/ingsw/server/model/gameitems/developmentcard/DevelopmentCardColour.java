package it.polimi.ingsw.server.model.gameitems.developmentcard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.localization.Localization;

import java.util.Locale;

/**
 * This enumeration lists all the Development Card Colours present in the Game.
 */
public enum DevelopmentCardColour {
	@JsonProperty("GREEN") GREEN("developmentCards.colours.green"),
	@JsonProperty("BLUE") BLUE("developmentCards.colours.blue"),
	@JsonProperty("YELLOW") YELLOW("developmentCards.colours.yellow"),
	@JsonProperty("PURPLE") PURPLE("developmentCards.colours.purple");

	public static DevelopmentCardColour getColourFromLocalizedName(String colour) {
	    for(DevelopmentCardColour c : DevelopmentCardColour.values())
	    	if(c.getColourNameLocalized().equals(colour.toLowerCase(Locale.ROOT)))
	    		return c;
	    return null;
	}

	private final String localizationPath;

	DevelopmentCardColour(String localizationPath) {
		this.localizationPath = localizationPath;
	}

	@JsonIgnore
	public String getColourNameLocalized() {
		return Localization.getLocalizationInstance().getString(localizationPath);
	}

}
