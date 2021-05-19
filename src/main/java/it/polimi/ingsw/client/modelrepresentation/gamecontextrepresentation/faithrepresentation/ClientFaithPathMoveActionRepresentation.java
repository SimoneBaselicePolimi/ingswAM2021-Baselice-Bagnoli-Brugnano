package it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.faithrepresentation;

import it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation.ClientGameActionRepresentation;
import it.polimi.ingsw.client.modelrepresentation.ClientPlayerRepresentation;

public class ClientFaithPathMoveActionRepresentation extends ClientGameActionRepresentation {
    private final ClientPlayerRepresentation player;
    private final int steps;

    public ClientFaithPathMoveActionRepresentation(ClientPlayerRepresentation player, int steps) {
        this.player = player;
        this.steps = steps;
    }

    public ClientPlayerRepresentation getPlayer() {
        return player;
    }

    public int getSteps() {
        return steps;
    }
}
