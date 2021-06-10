package it.polimi.ingsw.client.clientrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.server.model.Player;

public class DevelopmentActionClientRequest extends ClientRequest {

    public final ClientDevelopmentCardRepresentation developmentCard;
    public final int deckNumber;

    public DevelopmentActionClientRequest(
        @JsonProperty("player") Player player,
        @JsonProperty("developmentCard") ClientDevelopmentCardRepresentation developmentCard,
        @JsonProperty("deckNumber") int deckNumber
    ) {
        super(player);
        this.developmentCard = developmentCard;
        this.deckNumber = deckNumber;
    }

}
