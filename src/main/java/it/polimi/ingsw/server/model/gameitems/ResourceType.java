package it.polimi.ingsw.server.model.gameitems;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.localization.Localization;

import java.util.Map;

/**
 * This enumeration lists all the Resource types used in the Game.
 */
public enum ResourceType {
    @JsonProperty("COINS") COINS,
    @JsonProperty("STONES") STONES,
    @JsonProperty("SERVANTS") SERVANTS,
    @JsonProperty("SHIELDS") SHIELDS;

    private final static Map<ResourceType, String> RESOURCELOC = Map.of(
        ResourceType.COINS, "resources.coins",
        ResourceType.SERVANTS, "resources.servants",
        ResourceType.SHIELDS, "resources.shields",
        ResourceType.STONES, "resources.stones"
    );

    public String getLocalizedNameSingular() {
        return Localization.getLocalizationInstance().getString(RESOURCELOC.get(this) + ".singular");
    }

    public String getLocalizedNamePlural() {
        return Localization.getLocalizationInstance().getString(RESOURCELOC.get(this) + ".plural");
    }

}