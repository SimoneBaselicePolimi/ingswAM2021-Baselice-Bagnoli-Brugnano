package it.polimi.ingsw.server.model.gameupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.gameactionshistory.GameAction;

import java.util.List;

public class ServerGameHistoryUpdate extends ServerGameUpdate {
    public final List<GameAction> newGameActions;

    public ServerGameHistoryUpdate(
        @JsonProperty("newGameActions") List<GameAction> newGameActions
    ) {
        this.newGameActions = newGameActions;
    }
}

