package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.UserChoicesUtils;
import it.polimi.ingsw.client.cli.graphicutils.CliColour;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.cli.view.grid.GridView;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.localization.Localization;

import java.util.List;

public class GameSetupChoicesView extends CliView {

    protected GameView gameView;
    protected GridView container;
    protected LabelView cardsLeftToChooseLabel;
    protected LeaderCardListView cardListView;

    int cardsLeftToChoose;

    public GameSetupChoicesView(
        List<ClientLeaderCardRepresentation> leaderCardsToChooseFrom,
        int numberOfLeaderCardsToChoose,
        CliClientManager clientManager,
        GameView gameView
    ) {
        super(clientManager);
        this.gameView = gameView;
        cardsLeftToChoose = numberOfLeaderCardsToChoose;

        container = new GridView(clientManager, 4, 1, 1);
        addChildView(container, 0,0);
        container.setRowWeight(3, 7);

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
        container.setView(2, 0, cardsLeftToChooseLabel);

        cardListView = new LeaderCardListView(leaderCardsToChooseFrom, true, clientManager);
        container.setView(3, 0, cardListView);

        startDialog();
    }

    @Override
    public FormattedCharsBuffer getContentAsFormattedCharsBuffer() {
        if(cardsLeftToChoose > 1)
            cardsLeftToChooseLabel.setText(FormattedChar.convertStringToFormattedCharList(
                Localization.getLocalizationInstance().getString(
                    "client.cli.gameSetup.leaderCardsToChooseInfo.plural",
                    cardsLeftToChoose
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

    void startLeaderCardChoiceDialog() {

    }


}
