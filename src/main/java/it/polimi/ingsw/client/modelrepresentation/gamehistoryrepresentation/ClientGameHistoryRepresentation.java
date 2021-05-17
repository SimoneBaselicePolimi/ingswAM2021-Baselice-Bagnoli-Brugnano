package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;

import java.util.List;

public class ClientGameHistoryRepresentation extends ClientRepresentation {

    private List<ClientGameActionRepresentation> gameActions;

    public ClientGameHistoryRepresentation(List<ClientGameActionRepresentation> gameActions) {
        this.gameActions = gameActions;
    }

    public List<ClientGameActionRepresentation> getGameActions() {
        return gameActions;
    }

    public void setGameActions(List<ClientGameActionRepresentation> gameActions) {
        this.gameActions = gameActions;
    }

}
