package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import it.polimi.ingsw.client.modelrepresentation.ClientPlayerRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;

import java.util.Set;

public class ClientProductionActionRepresentation extends ClientGameActionRepresentation {
    private final ClientPlayerRepresentation player;
    private final Set<ClientProductionRepresentation> productions;

    public ClientProductionActionRepresentation(ClientPlayerRepresentation player, Set<ClientProductionRepresentation> productions) {
        this.player = player;
        this.productions = productions;
    }

    public ClientPlayerRepresentation getPlayer() {
        return player;
    }

    public Set<ClientProductionRepresentation> getProductions() {
        return productions;
    }
}
