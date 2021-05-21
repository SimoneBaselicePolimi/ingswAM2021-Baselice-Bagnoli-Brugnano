package it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerProductionRepresentation;

import java.util.Set;

public class ServerProductionActionRepresentation extends ServerGameActionRepresentation {

    public final ServerPlayerRepresentation player;
    public final Set<ServerProductionRepresentation> productions;

    public ServerProductionActionRepresentation(
        @JsonProperty("player") ServerPlayerRepresentation player,
        @JsonProperty("productions") Set<ServerProductionRepresentation> productions
    ) {
        this.player = player;
        this.productions = productions;
    }
}
