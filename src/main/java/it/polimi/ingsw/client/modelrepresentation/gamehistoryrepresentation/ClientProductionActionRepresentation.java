package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.ClientPlayerRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;

import java.util.Set;

public class ClientProductionActionRepresentation extends ClientGameActionRepresentation {
    private final ClientPlayerRepresentation player;
    private final Set<ClientProductionRepresentation> productions;

    public ClientProductionActionRepresentation(
        @JsonProperty("player") ClientPlayerRepresentation player,
        @JsonProperty("productions") Set<ClientProductionRepresentation> productions
    ) {
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
