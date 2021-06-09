package it.polimi.ingsw.client.clientrequest;

import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.server.model.Player;

public class DevelopmentActionClientRequest extends ClientRequest {

    public final ClientDevelopmentCardRepresentation developmentCard;
    public final int deckNumber;

    public DevelopmentActionClientRequest(
        Player player,
        ClientDevelopmentCardRepresentation developmentCard,
        int deckNumber
    ) {
        super(player);
        this.developmentCard = developmentCard;
        this.deckNumber = deckNumber;
    }

}
