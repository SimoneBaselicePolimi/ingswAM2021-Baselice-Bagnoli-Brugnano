package it.polimi.ingsw.configfile;

import it.polimi.ingsw.server.model.gameitems.MarbleColour;
import it.polimi.ingsw.utils.serialization.SerializeAsMapWithIdKey;

import java.util.Map;

public class MarketConfig {

    public final int numberOfRows;

    public final int numberOfColumns;

    @SerializeAsMapWithIdKey
    public final Map<MarbleColour, Integer> marblesNumber;

    public MarketConfig(int numberOfRows, int numberOfColumns, Map<MarbleColour, Integer> marblesNumber) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.marblesNumber = marblesNumber;
    }

}
