package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;
import it.polimi.ingsw.gameactionshistory.GameAction;

import java.util.ArrayList;
import java.util.List;

public class ClientGameHistoryRepresentation extends ClientRepresentation {

    private List<GameAction> gameActions;

    public ClientGameHistoryRepresentation(
        @JsonProperty("gameActions") List<GameAction> gameActions
    ) {
        this.gameActions = gameActions;
    }

    public List<GameAction> getGameActions() {
        return new ArrayList<>(gameActions);
    }

    public void setGameActions(List<GameAction> gameActions) {
        this.gameActions = new ArrayList<>(gameActions);
    }

    public void addGameAction(GameAction gameAction) {
        this.gameActions.add(gameAction);
    }

}
