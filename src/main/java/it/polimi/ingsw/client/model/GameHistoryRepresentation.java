package it.polimi.ingsw.client.model;

import java.util.List;

public class GameHistoryRepresentation extends Representation{

    private List<GameActionRepresentation> gameActions;

    public GameHistoryRepresentation(List<GameActionRepresentation> gameActions) {
        this.gameActions = gameActions;
    }

    public List<GameActionRepresentation> getGameActions() {
        return gameActions;
    }

    public void setGameActions(List<GameActionRepresentation> gameActions) {
        this.gameActions = gameActions;
    }

}
