package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.cli.view.grid.GridView;
import it.polimi.ingsw.client.cli.view.grid.LineBorderStyle;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.localization.LocalizationUtils;

public class DevelopmentCardView extends CliView {

    protected ClientDevelopmentCardRepresentation card;
    protected LabelView cardText;
    protected GridView cardGrid;

    public DevelopmentCardView(CliClientManager clientManager, ClientDevelopmentCardRepresentation card) {
        super(clientManager);
        this.card = card;

        cardGrid = new GridView(clientManager, 1, 1, 1);
        cardGrid.setBorderStyle(new LineBorderStyle());
        addChildView(cardGrid, 0, 0);
        cardText = new LabelView(
            FormattedChar.convertStringToFormattedCharList(getDevelopmentCardDescription()), clientManager
        );
        cardGrid.setView(0, 0, cardText);
    }

    protected String getDevelopmentCardDescription() {
        return "DEVELOPMENT CARD " +
            Localization.getLocalizationInstance().getString("developmentCards.level") +
            " " + card.getLevel().toValue() +
            "\n\n" +
            Localization.getLocalizationInstance().getString("developmentCards.cost") +
            " " + LocalizationUtils.getResourcesListAsCompactString(card.getPurchaseCost()) +
            "\n\n" +
            "> " + card.getProduction().getDescription() + "\n" +
            Localization.getLocalizationInstance().getString("leaderCards.victoryPoints", card.getVictoryPoints());
    }

}
