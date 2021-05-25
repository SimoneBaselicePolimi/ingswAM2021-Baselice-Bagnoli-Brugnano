package it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ServerDevelopmentCardRepresentation;

public class ServerDevelopmentActionRepresentation extends ServerGameActionRepresentation {

    public final Player player;
    public final ServerDevelopmentCardRepresentation developmentCard;

    public ServerDevelopmentActionRepresentation(
        @JsonProperty("player") Player player,
        @JsonProperty("developmentCard") ServerDevelopmentCardRepresentation developmentCard
    ) {
        this.player = player;
        this.developmentCard = developmentCard;
    }

}
