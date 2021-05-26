package it.polimi.ingsw.server.controller.servermessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.ServerGameContextRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ServerDevelopmentCardRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ServerLeaderCardRepresentation;

import java.util.Set;

public class GameInitialRepresentationServerMessage extends ServerMessage {

    public final Set<ServerDevelopmentCardRepresentation> developmentCardRepresentations;
    public final Set<ServerLeaderCardRepresentation> leaderCardRepresentations;
    public final ServerGameContextRepresentation gameContextRepresentation;

    public GameInitialRepresentationServerMessage(
        @JsonProperty("developmentCardRepresentations") Set<ServerDevelopmentCardRepresentation> developmentCardRepresentations,
        @JsonProperty("leaderCardRepresentations") Set<ServerLeaderCardRepresentation> leaderCardRepresentations,
        @JsonProperty("gameContextRepresentation") ServerGameContextRepresentation gameContextRepresentation
    ) {
        this.developmentCardRepresentations = developmentCardRepresentations;
        this.leaderCardRepresentations = leaderCardRepresentations;
        this.gameContextRepresentation = gameContextRepresentation;
    }

}
