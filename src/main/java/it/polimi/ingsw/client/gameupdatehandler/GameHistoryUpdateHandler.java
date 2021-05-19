package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientGameHistoryUpdate;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.ClientGameContextRepresentation;

public class GameHistoryUpdateHandler extends GameUpdateHandler<ClientGameHistoryUpdate>{

    @Override
    public void handleGameUpdate(ClientGameHistoryUpdate update, ClientGameContextRepresentation gameContextRepresentation) {
        // TODO: l'unico riferimento a GameHistory ce l'ho col GameManager (di cui non abbiamo una Representation nel client), non nel GameContext.

    }
}
