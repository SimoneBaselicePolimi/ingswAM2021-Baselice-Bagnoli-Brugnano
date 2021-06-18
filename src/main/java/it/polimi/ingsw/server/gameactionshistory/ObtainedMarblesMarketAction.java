package it.polimi.ingsw.server.gameactionshistory;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.MarbleColour;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsMapWithIdKey;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.Map;

public class ObtainedMarblesMarketAction extends GameAction {

    @SerializeIdOnly
    private final Player player;

    @SerializeAsMapWithIdKey
    private final Map<MarbleColour, Integer> marbleColours;

    public ObtainedMarblesMarketAction(
        @JsonProperty("player") Player player,
        @JsonProperty("marbleColours") Map<MarbleColour, Integer> marbleColours
    ) {
        this.player = player;
        this.marbleColours = marbleColours;
    }

}
