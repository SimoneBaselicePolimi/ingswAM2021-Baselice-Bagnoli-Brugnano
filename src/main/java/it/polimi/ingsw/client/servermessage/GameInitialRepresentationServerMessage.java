package it.polimi.ingsw.client.servermessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.ClientGameContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.ServerGameContextRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ServerLeaderCardRepresentation;

import java.util.Set;

//NOTE: this message must be serialized/deserialized using different classes for client and server
//CLIENT VERSION
public class GameInitialRepresentationServerMessage extends ServerMessage {

    public final Set<ClientLeaderCardRepresentation> leaderCardRepresentations;
    public final ClientGameContextRepresentation gameContextRepresentation;


    public GameInitialRepresentationServerMessage(
        @JsonProperty("leaderCardRepresentations") Set<ClientLeaderCardRepresentation> leaderCardRepresentations,
        @JsonProperty("gameContextRepresentation") ClientGameContextRepresentation gameContextRepresentation
    ) {
        this.leaderCardRepresentations = leaderCardRepresentations;
        this.gameContextRepresentation = gameContextRepresentation;
    }

}
