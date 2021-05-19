package it.polimi.ingsw.server.model.gamehistory;

import it.polimi.ingsw.server.model.Representable;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.marketrepresentation.ServerMarketRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerGameHistoryRepresentation;

import java.util.List;

public interface GameHistory extends Representable<ServerGameHistoryRepresentation> {
    List<GameAction> getGameHistory();

    void addAction(GameAction gameAction);
}
