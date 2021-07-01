package it.polimi.ingsw.client.clientrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class DevelopmentActionClientRequest extends ClientRequest {

    @SerializeIdOnly
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
