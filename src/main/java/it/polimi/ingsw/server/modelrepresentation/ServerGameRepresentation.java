package it.polimi.ingsw.server.modelrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.ServerGameContextRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ServerLeaderCardRepresentation;

import java.util.Set;

public class ServerGameRepresentation extends ServerRepresentation{

    public final Set<ServerLeaderCardRepresentation> leaderCardRepresentation;
    public final ServerGameContextRepresentation serverGameContextRepresentation;

    public ServerGameRepresentation(
        @JsonProperty("leaderCardRepresentation") Set<ServerLeaderCardRepresentation> leaderCardRepresentation,
        @JsonProperty("serverGameContextRepresentation") ServerGameContextRepresentation serverGameContextRepresentation
    ) {
        this.leaderCardRepresentation = leaderCardRepresentation;
        this.serverGameContextRepresentation = serverGameContextRepresentation;
    }

}
