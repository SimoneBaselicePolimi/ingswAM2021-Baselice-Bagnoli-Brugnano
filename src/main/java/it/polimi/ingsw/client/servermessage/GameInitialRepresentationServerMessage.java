package it.polimi.ingsw.client.servermessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.ClientGameContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;

import java.util.Set;

public class GameInitialRepresentationServerMessage extends ServerMessage {

    public final Set<ClientDevelopmentCardRepresentation> developmentCardRepresentations;
    public final Set<ClientLeaderCardRepresentation> leaderCardRepresentations;
    public final ClientGameContextRepresentation gameContextRepresentation;

    public GameInitialRepresentationServerMessage(
        @JsonProperty("developmentCardRepresentations") Set<ClientDevelopmentCardRepresentation> developmentCardRepresentations,
        @JsonProperty("leaderCardRepresentations") Set<ClientLeaderCardRepresentation> leaderCardRepresentations,
        @JsonProperty("gameContextRepresentation") ClientGameContextRepresentation gameContextRepresentation
    ) {
        this.developmentCardRepresentations = developmentCardRepresentations;
        this.leaderCardRepresentations = leaderCardRepresentations;
        this.gameContextRepresentation = gameContextRepresentation;
    }

}
