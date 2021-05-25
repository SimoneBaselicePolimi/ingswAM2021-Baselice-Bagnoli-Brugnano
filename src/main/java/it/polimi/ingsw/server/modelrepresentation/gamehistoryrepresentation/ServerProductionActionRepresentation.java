package it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerProductionRepresentation;

import java.util.Set;

public class ServerProductionActionRepresentation extends ServerGameActionRepresentation {

    public final Player player;
    public final Set<ServerProductionRepresentation> productions;

    public ServerProductionActionRepresentation(
        @JsonProperty("player") Player player,
        @JsonProperty("productions") Set<ServerProductionRepresentation> productions
    ) {
        this.player = player;
        this.productions = productions;
    }
}
