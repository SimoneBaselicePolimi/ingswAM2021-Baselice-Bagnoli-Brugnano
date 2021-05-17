package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientGameHistoryUpdate;
import it.polimi.ingsw.client.model.GameContextRepresentation;

public class GameHistoryUpdateHandler extends GameUpdateHandler<ClientGameHistoryUpdate>{

    @Override
    public void handleGameUpdate(ClientGameHistoryUpdate update, GameContextRepresentation gameContextRepresentation) {
        // TODO: l'unico riferimento a GameHistory ce l'ho col GameManager (di cui non abbiamo una Representation nel client), non nel GameContext.

    }
}
