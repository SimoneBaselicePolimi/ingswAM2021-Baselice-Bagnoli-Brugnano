package it.polimi.ingsw.server.model.gameitems;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This enumeration lists all the Resource types used in the Game.
 */
public enum ResourceType {
    @JsonProperty("COINS") COINS,
    @JsonProperty("STONES") STONES,
    @JsonProperty("SERVANTS") SERVANTS,
    @JsonProperty("SHIELDS") SHIELDS;
}