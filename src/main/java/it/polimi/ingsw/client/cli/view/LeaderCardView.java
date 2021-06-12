package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
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
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.utils.Colour;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LeaderCardView extends CliView {

    public static final int LEADER_CARD_ROW_SIZE = 16;
    public static final int LEADER_CARD_COL_SIZE = 30;

    public static final Colour defaultProductionColour = Colour.WHITE;

    protected boolean isBorderColourBasedOnState;

    protected Colour borderColour = Colour.WHITE;

    protected ClientLeaderCardRepresentation card;
    protected LabelView cardText;
    protected GridView cardGrid;

    protected int numberOfCard;

    protected Map<ClientProductionRepresentation, Colour> productionsColourMap;

    public LeaderCardView(CliClientManager clientManager, ClientLeaderCardRepresentation card, int numberOfCard) {
        this(clientManager, card, false, numberOfCard);
    }

    public LeaderCardView(CliClientManager clientManager, ClientLeaderCardRepresentation card, Colour borderColour, int numberOfCard) {
        this(clientManager, card, false, numberOfCard);
        this.borderColour = borderColour;
    }

    public LeaderCardView(CliClientManager clientManager, ClientLeaderCardRepresentation card, boolean isBorderColourBasedOnState, int numberOfCard) {
        super(clientManager, LEADER_CARD_ROW_SIZE, LEADER_CARD_COL_SIZE);
        this.card = card;
        this.isBorderColourBasedOnState = isBorderColourBasedOnState;
        this.numberOfCard = numberOfCard;

        subscribeToRepresentation(card);

        productionsColourMap = card.getProductions().stream().collect(Collectors.toMap(
            p -> p,
            p -> defaultProductionColour
        ));

        cardGrid = new GridView(clientManager, 1, 1, 1, LEADER_CARD_ROW_SIZE, LEADER_CARD_COL_SIZE);
        addChildView(cardGrid, 0, 0);
        cardText = new LabelView(new ArrayList<>(), clientManager);
        cardGrid.setView(0, 0, cardText);
    }

    public void setProductionColour(ClientProductionRepresentation production, Colour colour) {
        productionsColourMap.put(production, colour);
    }

    public void setBorderColour(Colour borderColour, boolean updateView) {
        this.borderColour = borderColour;
        isBorderColourBasedOnState = false;
        if(updateView)
            updateView();
    }

    public void setBorderColourBasedOnState(boolean borderColourBasedOnState, boolean updateView) {
        this.isBorderColourBasedOnState = borderColourBasedOnState;
        if(updateView)
            updateView();
    }

    protected List<FormattedChar> getLeaderCardDescription() {
        StringBuilder firstSectionDescriptionBuilder = new StringBuilder();
        firstSectionDescriptionBuilder.append(Localization.getLocalizationInstance().getString("leaderCards.name"))
            .append(" ").append(numberOfCard).append("\n\n");
        firstSectionDescriptionBuilder.append(
            Localization.getLocalizationInstance().getString("leaderCards.requirements.requirements")
        );
        firstSectionDescriptionBuilder.append("\n");
        card.getRequirements().stream()
            .map(ClientLeaderCardRequirementRepresentation::getDescription)
            .forEach(r -> firstSectionDescriptionBuilder.append("- ").append(r).append("\n"));
        firstSectionDescriptionBuilder.append("\n");

        String lastSectionDescription = Localization.getLocalizationInstance().getString(
            "leaderCards.victoryPoints", card.getVictoryPoints()
        );

        return Stream.concat(
            Stream.concat(
                FormattedChar.convertStringToFormattedCharList(firstSectionDescriptionBuilder.toString()).stream(),
                getSpecialPowersDescription().stream()
            ), FormattedChar.convertStringToFormattedCharList(lastSectionDescription).stream()
        ).collect(Collectors.toList());

    }

    protected List<FormattedChar> getSpecialPowersDescription() {

        List<FormattedChar> specialPowerDescription = new ArrayList<>();

        specialPowerDescription.addAll(FormattedChar.convertStringToFormattedCharList(
            Localization.getLocalizationInstance().getString("leaderCards.specialPowers.specialPowers")
        ));
        specialPowerDescription.addAll(FormattedChar.convertStringToFormattedCharList("\n"));

        card.getCardCostDiscounts().stream().map(ClientDevelopmentCardCostDiscountRepresentation::getDescription)
            .forEach(d ->
                specialPowerDescription.addAll(FormattedChar.convertStringToFormattedCharList(("> " + d + "\n")))
            );

        card.getWhiteMarbleSubstitutions().stream().map(ClientWhiteMarbleSubstitutionRepresentation::getDescription)
            .forEach(d ->
                specialPowerDescription.addAll(FormattedChar.convertStringToFormattedCharList(("> " + d + "\n")))
            );

        card.getProductions().forEach(p -> specialPowerDescription.addAll(
            FormattedChar.convertStringToFormattedCharList(
                ("> " + p.getDescription() + "\n"),
                productionsColourMap.get(p),
                Colour.BLACK
            )
        ));

        card.getResourceStorages().stream().map(ClientResourceStorageRepresentation::getDescription)
            .forEach(d ->
                specialPowerDescription.addAll(FormattedChar.convertStringToFormattedCharList(("> " + d + "\n")))
            );

        return specialPowerDescription;
    }

    @Override
    public FormattedCharsBuffer getContentAsFormattedCharsBuffer() {

        cardText.setText(getLeaderCardDescription());

        if(isBorderColourBasedOnState) {
            if (card.getState().equals(LeaderCardState.ACTIVE))
                cardGrid.setBorderStyle(new LineBorderStyle(Colour.WHITE));
            else if (card.getState().equals(LeaderCardState.DISCARDED))
                cardGrid.setBorderStyle(new LineBorderStyle(Colour.RED));
            else
                cardGrid.setBorderStyle(new LineBorderStyle(Colour.GREY));
        } else {
            cardGrid.setBorderStyle(new LineBorderStyle(borderColour));
        }

        return super.getContentAsFormattedCharsBuffer();
    }
}
