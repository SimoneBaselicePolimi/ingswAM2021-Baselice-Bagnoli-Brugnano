package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientCoveredCardsDeckRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;

import java.util.ArrayList;

public class DevCardTableDeckView extends CliView {

    public final DevelopmentCardColour cardColour;
    public final DevelopmentCardLevel cardLevel;

    protected final ClientCoveredCardsDeckRepresentation<ClientDevelopmentCardRepresentation> cardDeck;

    public DevCardTableDeckView(
        DevelopmentCardColour cardColour,
        DevelopmentCardLevel cardLevel,
        CliClientManager clientManager,
        int rowSize,
        int columnSize
    ) {
        super(clientManager, rowSize, columnSize);
        this.cardColour = cardColour;
        this.cardLevel = cardLevel;
        this.cardDeck = clientManager.getGameContextRepresentation()
            .getDevelopmentCardsTable().getDeck(cardLevel, cardColour);

        LabelView deckLabel = new LabelView(new ArrayList<>(), clientManager);

    }

    @Override
    public FormattedCharsBuffer getContentAsFormattedCharsBuffer() {
        if(cardDeck.getNumberOfCardsInDeck() > 0)

        return super.getContentAsFormattedCharsBuffer();
    }

    public DevCardTableDeckView(DevelopmentCardColour cardColour, DevelopmentCardLevel cardLevel, CliClientManager clientManager) {
        this(cardColour, cardLevel, clientManager, 0, 0);
    }

}
