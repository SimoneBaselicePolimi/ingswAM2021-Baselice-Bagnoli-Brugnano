package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;

import java.util.ArrayList;
import java.util.List;

public class ClientGameHistoryRepresentation extends ClientRepresentation {

    private List<ClientGameActionRepresentation> gameActions;

    public ClientGameHistoryRepresentation(
        @JsonProperty("gameActions") List<ClientGameActionRepresentation> gameActions
    ) {
        this.gameActions = gameActions;
    }

    public List<ClientGameActionRepresentation> getGameActions() {
        return new ArrayList<>(gameActions);
    }

    public void setGameActions(List<ClientGameActionRepresentation> gameActions) {
        this.gameActions = new ArrayList<>(gameActions);
    }

    public void addGameAction(ClientGameActionRepresentation gameAction) {
        this.gameActions.add(gameAction);
    }

}
