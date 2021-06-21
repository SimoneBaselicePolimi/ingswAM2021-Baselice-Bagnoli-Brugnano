package it.polimi.ingsw.server.model.gameitems;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.localization.Localization;

import java.util.Locale;
import java.util.Map;

/**
 * This enumeration lists all the Resource types used in the Game.
 */
public enum ResourceType {

    @JsonProperty("COINS") COINS,
    @JsonProperty("STONES") STONES,
    @JsonProperty("SERVANTS") SERVANTS,
    @JsonProperty("SHIELDS") SHIELDS;

    private final static Map<ResourceType, String> RESOURCE_LOC = Map.of(
        ResourceType.COINS, "resources.coins",
        ResourceType.SERVANTS, "resources.servants",
        ResourceType.SHIELDS, "resources.shields",
        ResourceType.STONES, "resources.stones"
    );

    private final static Map<ResourceType, String> RESOURCE_IMG = Map.of(
        ResourceType.COINS, "coin.png",
        ResourceType.SERVANTS, "servant.png",
        ResourceType.SHIELDS, "shield.png",
        ResourceType.STONES, "stone.png"
    );

    @JsonIgnore
    public String getLocalizedNameSingular() {
        return Localization.getLocalizationInstance().getString(RESOURCE_LOC.get(this) + ".singular");
    }

    @JsonIgnore
    public String getLocalizedNamePlural() {
        return Localization.getLocalizationInstance().getString(RESOURCE_LOC.get(this) + ".plural");
    }

    public static ResourceType getResourceTypeFromLocalizedName(String resourceType) {
        for(ResourceType r : ResourceType.values())
            if(r.getLocalizedNameSingular().equals(resourceType.toLowerCase(Locale.ROOT))
                || r.getLocalizedNamePlural().equals(resourceType.toLowerCase(Locale.ROOT)))
                return r;
        return null;
    }

    @JsonIgnore
    public String getIconPathForResourceType() {
        return RESOURCE_IMG.get(this);
    }
}