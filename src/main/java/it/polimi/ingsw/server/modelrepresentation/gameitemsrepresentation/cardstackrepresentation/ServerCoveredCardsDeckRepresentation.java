package it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.cardstackrepresentation;

import it.polimi.ingsw.server.modelrepresentation.ServerRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerRegisteredIdentifiableItemRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ServerDevelopmentCardRepresentation;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class ServerCoveredCardsDeckRepresentation<C extends ServerRepresentation> extends ServerRegisteredIdentifiableItemRepresentation {

    @SerializeIdOnly
    public final ServerDevelopmentCardRepresentation cardOnTop;  //Only the card on top of the deck should be visible to the player

    public final int numberOfCardsInDeck;

    public ServerCoveredCardsDeckRepresentation(
        String itemID,
        ServerDevelopmentCardRepresentation cardOnTop,
        int numberOfCardsInDeck
    ) {
        super(itemID);
        this.cardOnTop = cardOnTop;
        this.numberOfCardsInDeck = numberOfCardsInDeck;
    }
}
