package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.graphicutils.CliColour;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.cli.view.grid.GridView;
import it.polimi.ingsw.client.cli.view.grid.LineBorderStyle;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientDevelopmentCardCostDiscountRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientWhiteMarbleSubstitutionRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRequirementRepresentation;
import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientResourceStorageRepresentation;
import it.polimi.ingsw.localization.Localization;

public class LeaderCardView extends CliView {

    public static int LEADER_CARD_ROW_SIZE = 14;
    public static int LEADER_CARD_COL_SIZE = 24;

    protected boolean isSelected = false;

    protected ClientLeaderCardRepresentation card;
    protected LabelView cardText;
    protected GridView cardGrid;

    protected int numberOfCard;

    public LeaderCardView(CliClientManager clientManager, ClientLeaderCardRepresentation card, int numberOfCard) {
        super(clientManager, LEADER_CARD_ROW_SIZE, LEADER_CARD_COL_SIZE);
        this.card = card;
        this.numberOfCard = numberOfCard;
    }

    public LeaderCardView(CliClientManager clientManager, ClientLeaderCardRepresentation card, boolean isSelected, int numberOfCard) {
        super(clientManager, LEADER_CARD_ROW_SIZE, LEADER_CARD_COL_SIZE);
        this.card = card;
        this.isSelected = isSelected;
        this.numberOfCard = numberOfCard;

        cardGrid = new GridView(clientManager, 1, 1, 1, LEADER_CARD_ROW_SIZE, LEADER_CARD_COL_SIZE);
        cardGrid.setBorderStyle(new LineBorderStyle(CliColour.GREY));
        addChildView(cardGrid, 0, 0);
        cardText = new LabelView(FormattedChar.convertStringToFormattedCharList(getLeaderCardDescription()), clientManager);
        cardGrid.setView(0, 0, cardText);
    }

    @Override
    protected FormattedCharsBuffer buildMyBuffer() {
        FormattedCharsBuffer buffer = new FormattedCharsBuffer(LEADER_CARD_ROW_SIZE, LEADER_CARD_COL_SIZE);
        if(isSelected)
            cardGrid.setBorderStyle(new LineBorderStyle(CliColour.GREEN));
        return buffer;
    }

    public void selectCard(int cardNumber) {
        isSelected = true;
        updateView();
    }

    public void deselectCard(int cardNumber) {
        isSelected = false;
        updateView();
    }

    protected String getLeaderCardDescription() {
        StringBuilder descriptionBuilder = new StringBuilder();
        descriptionBuilder.append("LEADER CARD ").append(numberOfCard).append("\n");
        descriptionBuilder.append(
            Localization.getLocalizationInstance().getString("leaderCards.requirements.requirements")
        );
        descriptionBuilder.append("\n");
        card.getRequirements().stream()
            .map(ClientLeaderCardRequirementRepresentation::getDescription)
            .forEach(r -> descriptionBuilder.append("- ").append(r).append("\n"));
        descriptionBuilder.append(getSpecialPowersDescription());
        descriptionBuilder.append("\n");
        descriptionBuilder.append(
            Localization.getLocalizationInstance().getString("leaderCards.victoryPoints", card.getVictoryPoints())
        );
        return descriptionBuilder.toString();
    }

    protected String getSpecialPowersDescription() {

        StringBuilder specialPowerDescriptionBuilder = new StringBuilder();

        specialPowerDescriptionBuilder.append(
            Localization.getLocalizationInstance().getString("leaderCards.specialPowers.specialPowers")
        );
        specialPowerDescriptionBuilder.append("\n");

        card.getCardCostDiscounts().stream().map(ClientDevelopmentCardCostDiscountRepresentation::getDescription)
            .forEach(d -> specialPowerDescriptionBuilder.append("> ").append(d).append("\n"));

        card.getWhiteMarbleSubstitutions().stream().map(ClientWhiteMarbleSubstitutionRepresentation::getDescription)
            .forEach(d -> specialPowerDescriptionBuilder.append("> ").append(d).append("\n"));

        card.getProductions().stream().map(ClientProductionRepresentation::getDescription)
            .forEach(d -> specialPowerDescriptionBuilder.append("> ").append(d).append("\n"));

        card.getResourceStorages().stream().map(ClientResourceStorageRepresentation::getDescription)
            .forEach(d -> specialPowerDescriptionBuilder.append("> ").append(d).append("\n"));

        return specialPowerDescriptionBuilder.toString();
    }

}
