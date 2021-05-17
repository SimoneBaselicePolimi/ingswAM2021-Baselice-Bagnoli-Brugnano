package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import it.polimi.ingsw.client.modelrepresentation.ClientPlayerRepresentation;

public class ClientPostTurnFinalActionRepresentation extends ClientGameActionRepresentation {
    private final ClientPlayerRepresentation player;

    public ClientPostTurnFinalActionRepresentation(ClientPlayerRepresentation player) {
        this.player = player;
    }

    public ClientPlayerRepresentation getPlayer() {
        return player;
    }
}
