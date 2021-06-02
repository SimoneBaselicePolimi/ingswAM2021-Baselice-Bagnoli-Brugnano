package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.UserChoicesUtils;
import it.polimi.ingsw.client.cli.graphicutils.CliColour;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.cli.view.grid.GridView;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.localization.Localization;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.lang.Integer.parseInt;

public class GameSetupChoicesView extends CliView {

    protected GameView gameView;
    protected GridView container;
    protected LabelView cardsLeftToChooseLabel;
    protected LeaderCardListView cardListView;

    int cardsToChoose;

    public GameSetupChoicesView(
        List<ClientLeaderCardRepresentation> leaderCardsToChooseFrom,
        int numberOfLeaderCardsToChoose,
        CliClientManager clientManager,
        GameView gameView
    ) {
        super(clientManager);
        this.gameView = gameView;
        cardsToChoose = numberOfLeaderCardsToChoose;

        container = new GridView(clientManager, 3, 1, 1);
        addChildView(container, 0,0);
        container.setRowWeight(2, 10);

        container.setView(
            0,
            0,
            new LabelView(
                FormattedChar.convertStringToFormattedCharList(
                    Localization.getLocalizationInstance().getString("client.cli.gameSetup.gameSetupInfo"),
                    CliColour.WHITE,
                    CliColour.BLACK,
                    true,
                    false,
                    false
                ),
                clientManager
            )
        );

        cardsLeftToChooseLabel = new LabelView(List.of(), clientManager);
        container.setView(1, 0, cardsLeftToChooseLabel);

        cardListView = new LeaderCardListView(leaderCardsToChooseFrom, true, clientManager);
        container.setView(2, 0, cardListView);

        startDialog();
    }

    @Override
    public FormattedCharsBuffer getContentAsFormattedCharsBuffer() {
        if(cardsToChoose > 1)
            cardsLeftToChooseLabel.setText(FormattedChar.convertStringToFormattedCharList(
                Localization.getLocalizationInstance().getString(
                    "client.cli.gameSetup.leaderCardsToChooseInfo.plural",
                    cardsToChoose
                ),
                CliColour.WHITE,
                CliColour.BLACK,
                false,
                true,
                false
            ));
        else
            cardsLeftToChooseLabel.setText(FormattedChar.convertStringToFormattedCharList(
                Localization.getLocalizationInstance().getString(
                    "client.cli.gameSetup.leaderCardsToChooseInfo.singular"
                ),
                CliColour.WHITE,
                CliColour.BLACK,
                false,
                true,
                false
            ));
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

    CompletableFuture<List<ClientLeaderCardRepresentation>> selectCards(
        List<ClientLeaderCardRepresentation> alreadySelectedCards,
        int cardsLeftToSelect
    ) {
        if(cardsLeftToSelect == 0) {
            return CompletableFuture.completedFuture(alreadySelectedCards);
        } else {
            return clientManager.askUserLocalized("client.cli.gameSetup.leaderCardsDialog.choose")
                .thenCompose(input -> {
                        int intInput = parseInt(input);
                        cardListView.selectCard(intInput);
                        alreadySelectedCards.add(cardListView.getLeaderCardViewByNumber(intInput));
                        return selectCards(alreadySelectedCards, cardsLeftToSelect - 1);
                    }
                );
        }
    }

    void startLeaderCardChoiceDialog() {
       selectCards(new ArrayList<>(), cardsToChoose)
//           .thenCompose(chosenCards ->
//
//           )
        ;
    }

}
