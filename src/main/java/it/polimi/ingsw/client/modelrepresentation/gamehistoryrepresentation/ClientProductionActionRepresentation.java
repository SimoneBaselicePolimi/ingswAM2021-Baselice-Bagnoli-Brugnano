package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.server.model.Player;

import java.util.Set;

public class ClientProductionActionRepresentation extends ClientGameActionRepresentation {
    private final Player player;
    private final Set<ClientProductionRepresentation> productions;

    public ClientProductionActionRepresentation(
        @JsonProperty("player") Player player,
        @JsonProperty("productions") Set<ClientProductionRepresentation> productions
    ) {
        this.player = player;
        this.productions = productions;
    }

    public Player getPlayer() {
        return player;
    }

    public Set<ClientProductionRepresentation> getProductions() {
        return productions;
    }
}
