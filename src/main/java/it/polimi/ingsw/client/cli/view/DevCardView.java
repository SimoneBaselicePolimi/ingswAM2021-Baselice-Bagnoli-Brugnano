package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.cli.view.grid.GridView;
import it.polimi.ingsw.client.cli.view.grid.LineBorderStyle;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.localization.LocalizationUtils;
import it.polimi.ingsw.utils.Colour;

/**
 * View representing the development card
 */
import java.util.ArrayList;

public class DevCardView extends CliView {

    public static final int DEV_CARD_ROW_SIZE = 15;
    public static final int DEV_CARD_COL_SIZE = 27;

    protected ClientDevelopmentCardRepresentation card;
    protected LabelView cardText;
    protected GridView cardGrid;
    protected Colour borderColour = Colour.WHITE;

    public DevCardView(
        ClientDevelopmentCardRepresentation card,
        CliClientManager clientManager
    ) {
        super(clientManager, DEV_CARD_ROW_SIZE, DEV_CARD_COL_SIZE);
        this.card = card;

        cardGrid = new GridView(clientManager, 1, 1, 1, DEV_CARD_ROW_SIZE, DEV_CARD_COL_SIZE);
        cardGrid.setBorderStyle(new LineBorderStyle());
        addChildView(cardGrid, 0, 0);
        if(card!=null) {
            cardText = new LabelView(
                FormattedChar.convertStringToFormattedCharList(getDevelopmentCardDescription()), clientManager
            );
        } else {
            cardText = new LabelView(new ArrayList<>(), clientManager);
        }
        cardGrid.setView(0, 0, cardText);
    }

    protected String getDevelopmentCardDescription() {
        return Localization.getLocalizationInstance().getString("developmentCards.name") +
            " " +
            Localization.getLocalizationInstance().getString("developmentCards.level") +
            " " + card.getLevel().toValue() +
            "\n" + card.getColour().getColourNameLocalizedSingular() +
            "\n\n" +
            Localization.getLocalizationInstance().getString("developmentCards.cost") +
            " " + LocalizationUtils.getResourcesListAsCompactString(card.getPurchaseCost()) +
            "\n\n" +
            card.getProduction().getDescription() + "\n" +
            Localization.getLocalizationInstance().getString("leaderCards.victoryPoints", card.getVictoryPoints());
    }

    @Override
    public FormattedCharsBuffer getContentAsFormattedCharsBuffer() {
        cardGrid.setBorderStyle(new LineBorderStyle(borderColour));
        return super.getContentAsFormattedCharsBuffer();
    }

    public void setCardBorderColour(Colour colour) {
        borderColour = colour;
    }

}
