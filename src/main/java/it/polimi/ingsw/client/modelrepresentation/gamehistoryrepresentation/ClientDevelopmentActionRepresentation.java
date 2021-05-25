package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.server.model.Player;

public class ClientDevelopmentActionRepresentation extends ClientGameActionRepresentation {

    private final Player player;
    private final ClientDevelopmentCardRepresentation developmentCard;

    public ClientDevelopmentActionRepresentation(
        @JsonProperty("player") Player player,
        @JsonProperty("developmentCard") ClientDevelopmentCardRepresentation developmentCard
    ) {
        this.player = player;
        this.developmentCard = developmentCard;
    }

    public Player getPlayer() {
        return player;
    }

    public ClientDevelopmentCardRepresentation getDevelopmentCard() {
        return developmentCard;
    }
}
