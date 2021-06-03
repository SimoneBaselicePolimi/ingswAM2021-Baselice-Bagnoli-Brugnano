package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.UserChoicesUtils;
import it.polimi.ingsw.utils.Colour;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.cli.view.grid.GridView;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.client.servermessage.InitialChoicesServerMessage;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class LeaderCardSetupView extends CliView {

    protected GameView gameView;
    protected GridView container;
    protected LabelView titleView;
    protected LeaderCardListView cardListView;

    protected InitialChoicesServerMessage initialChoicesServerMessage;

    List<ClientLeaderCardRepresentation> alreadySelectedCards = new ArrayList<>();
    int cardsLeftToChoose;

    public LeaderCardSetupView(
        InitialChoicesServerMessage initialChoicesServerMessage,
        CliClientManager clientManager,
        GameView gameView
    ) {
        super(clientManager);
        this.gameView = gameView;
        this.initialChoicesServerMessage = initialChoicesServerMessage;
        cardsLeftToChoose = initialChoicesServerMessage.numberOfLeaderCardsToKeep;

        container = new GridView(clientManager, 2, 1, 1);
        addChildView(container, 0,0);
        container.setRowWeight(1, 14);

        titleView = new LabelView(List.of(), clientManager);
        container.setView(0,0, titleView);

        cardListView = new LeaderCardListView(
            new ArrayList<>(initialChoicesServerMessage.leaderCardsGivenToThePlayer),
            true,
            clientManager
        );
        container.setView(1, 0, cardListView);

        startDialog();
    }

    @Override
    public FormattedCharsBuffer getContentAsFormattedCharsBuffer() {

        List<FormattedChar> gameSetupInfo = FormattedChar.convertStringToFormattedCharList(
            Localization.getLocalizationInstance().getString("client.cli.gameSetup.gameSetupInfo") + ": ",
            Colour.WHITE,
            Colour.BLACK,
            true,
            false,
            false
        );

        String localizedText;
        if(cardsLeftToChoose > 1)
            localizedText = Localization.getLocalizationInstance().getString(
                "client.cli.gameSetup.leaderCardsToChooseInfo.plural",
                cardsLeftToChoose
            );
        else
            localizedText = Localization.getLocalizationInstance().getString(
                "client.cli.gameSetup.leaderCardsToChooseInfo.singular"
            );

        List<FormattedChar> leaderCardsToChooseInfo = FormattedChar.convertStringToFormattedCharList(
            localizedText,
            Colour.WHITE,
            Colour.BLACK,
            false,
            true,
            false
        );

        gameSetupInfo.addAll(leaderCardsToChooseInfo);
        titleView.setText(gameSetupInfo);

        return super.getContentAsFormattedCharsBuffer();
    }

    @Override
    public void setRowSize(int rowSize) {
        container.setRowSize(rowSize);
        super.setRowSize(rowSize);
    }

    @Override
    public void setColumnSize(int columnSize) {
        container.setColumnSize(columnSize);
        super.setColumnSize(columnSize);
    }

    void startDialog() {
        UserChoicesUtils.makeUserChoose(clientManager)
            .addUserChoiceLocalized(
                this::startLeaderCardChoiceDialog,
                "client.cli.gameSetup.chooseLeaderCards"
            ).addUserChoiceLocalized(
                () -> gameView.setMainContentView(new MarketView(clientManager, gameView)),
                "client.cli.mainMenuActions.openMarket"
            ).addUserChoiceLocalized(
                () -> gameView.setMainContentView(new DevCardTableView(clientManager, gameView)),
                "client.cli.mainMenuActions.openDevCardsTable"
            ).addUserChoiceLocalized(
                () -> gameView.setMainContentView(new FaithPathView(clientManager, gameView)),
                "client.cli.mainMenuActions.openFaithPath"
            ).addUserChoiceLocalized(
                () -> gameView.setMainContentView(new PlayerDashboardView(clientManager, gameView)),
                "client.cli.mainMenuActions.openPersonalDashboard"
            ).addUserChoiceLocalized(
                () -> gameView.setMainContentView(new PlayerDashboardView(clientManager, gameView)),
                "client.cli.mainMenuActions.openDifferentPlayerDashboard"
            ).apply();
    }

    CompletableFuture<List<ClientLeaderCardRepresentation>> selectCards() {
        if(cardsLeftToChoose == 0) {
            return CompletableFuture.completedFuture(alreadySelectedCards);
        } else {
            return clientManager.askUserLocalized("client.cli.gameSetup.leaderCardsDialog.choose")
                .thenCompose(input -> {
                        int intInput = parseInt(input);
                        if(intInput < 1 || intInput > cardListView.cardsToView.size()) {
                            clientManager.tellUserLocalized("client.cli.gameSetup.leaderCardsDialog.invalid");
                            return selectCards();
                        }
                        if(cardListView.selectedCard.get(cardListView.getLeaderCardRepresentationByNumber(intInput))) {
                            clientManager.tellUserLocalized("client.cli.gameSetup.leaderCardsDialog.alreadyChosen");
                            return selectCards();
                        }
                        cardListView.selectCard(intInput);
                        alreadySelectedCards.add(cardListView.getLeaderCardRepresentationByNumber(intInput));
                        cardsLeftToChoose--;
                        updateView();
                        return selectCards();
                    }
                );
        }
    }

    void startLeaderCardChoiceDialog() {
        selectCards()
           .thenCompose(chosenCards -> {
               ResourcesChoiceView resourcesChoiceView = new ResourcesChoiceView(
                   initialChoicesServerMessage.numberOfStarResources,
                   Arrays.stream(ResourceType.values()).collect(Collectors.toSet()),
                   clientManager,
                   Localization.getLocalizationInstance().getString("client.cli.gameSetup.gameSetupInfo")
               );
               gameView.setMainContentView(resourcesChoiceView);
               resourcesChoiceView.setOnAllChoicesDoneCallback(resourcesChosen -> {
                   ;
               });
               return CompletableFuture.completedFuture(null);
           });
    }

}
