package it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ServerDevelopmentCardRepresentation;

public class ServerDevelopmentActionRepresentation extends ServerGameActionRepresentation {

    public final ServerPlayerRepresentation player;
    public final ServerDevelopmentCardRepresentation developmentCard;

    public ServerDevelopmentActionRepresentation(
        @JsonProperty("player") ServerPlayerRepresentation player,
        @JsonProperty("developmentCard") ServerDevelopmentCardRepresentation developmentCard
    ) {
        this.player = player;
        this.developmentCard = developmentCard;
    }

}
