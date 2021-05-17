package it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.faithrepresentation;

import it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation.ClientGameActionRepresentation;
import it.polimi.ingsw.client.modelrepresentation.ClientPlayerRepresentation;

public class ClientFaithPathLastPositionReachedActionRepresentation extends ClientGameActionRepresentation {
    private final ClientPlayerRepresentation player;

    public ClientFaithPathLastPositionReachedActionRepresentation(ClientPlayerRepresentation player) {
        this.player = player;
    }

    public ClientPlayerRepresentation getPlayer() {
        return player;
    }
}
