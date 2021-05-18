package it.polimi.ingsw.server.model.gamehistory;

import it.polimi.ingsw.server.model.Representable;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerGameActionRepresentation;

public abstract class GameAction implements Representable<ServerGameActionRepresentation> {
    public abstract String getActionMessage();
}
