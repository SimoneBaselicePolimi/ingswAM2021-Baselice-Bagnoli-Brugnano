package it.polimi.ingsw.server.model.gameitems.developmentcard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.utils.Colour;

import java.util.Locale;

/**
 * This enumeration lists all the Development Card Colours present in the Game.
 */
public enum DevelopmentCardColour {
	@JsonProperty("GREEN") GREEN("developmentCards.colours.green", Colour.GREEN),
	@JsonProperty("BLUE") BLUE("developmentCards.colours.blue", Colour.BLUE),
	@JsonProperty("YELLOW") YELLOW("developmentCards.colours.yellow", Colour.YELLOW),
	@JsonProperty("PURPLE") PURPLE("developmentCards.colours.purple", Colour.PURPLE);

	public static DevelopmentCardColour getColourFromLocalizedName(String colour) {
	    for(DevelopmentCardColour c : DevelopmentCardColour.values())
	    	if(c.getColourNameLocalizedSingular().equals(colour.toLowerCase(Locale.ROOT)))
	    		return c;
	    return null;
	}

	private final String localizationPath;

	private final Colour colour;

	DevelopmentCardColour(String localizationPath, Colour colour) {
		this.localizationPath = localizationPath;
		this.colour = colour;
	}

	@JsonIgnore
	public Colour getUIColour() {
		return colour;
	}

	@JsonIgnore
	public String getColourNameLocalizedSingular() {
		return Localization.getLocalizationInstance().getString(localizationPath + ".singular");
	}

	@JsonIgnore
	public String getColourNameLocalizedPlural() {
		return Localization.getLocalizationInstance().getString(localizationPath + ".plural");
	}

}
