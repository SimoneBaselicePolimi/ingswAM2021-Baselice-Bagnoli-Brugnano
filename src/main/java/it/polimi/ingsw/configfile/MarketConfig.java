package it.polimi.ingsw.configfile;

import it.polimi.ingsw.server.model.gameitems.MarbleColour;
import it.polimi.ingsw.utils.serialization.SerializeAsMapWithIdKey;
import it.polimi.ingsw.utils.serialization.SerializeAsSetOfIds;
import it.polimi.ingsw.utils.serialization.SerializeIdOnly;

import java.util.Map;
import java.util.Set;

public class MarketConfig {

    public int numberOfRows;

    public int numberOfColumns;

    @SerializeIdOnly
    public MarbleColour m;

    @SerializeAsMapWithIdKey
    Map<MarbleColour, Integer> marblesMap;

    @SerializeAsSetOfIds
    Set<MarbleColour> marblesSet;


    public MarketConfig() {};

    public MarketConfig(int numberOfRows, int numberOfColumns, MarbleColour m, Map<MarbleColour, Integer> marblesMap, Set<MarbleColour> marblesSet) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.m = m;
        this.marblesMap = marblesMap;
        this.marblesSet = marblesSet;
    }
}
