package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.ServerMessageUtils;
import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.UserChoicesUtils;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBufferUtils;
import it.polimi.ingsw.client.cli.view.grid.GridView;
import it.polimi.ingsw.client.cli.view.grid.LineBorderStyle;
import it.polimi.ingsw.client.clientmessage.PlayerRequestClientMessage;
import it.polimi.ingsw.client.clientrequest.EndTurnClientRequest;
import it.polimi.ingsw.client.servermessage.EndTurnServerMessage;
import it.polimi.ingsw.client.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.localization.LocalizationUtils;
import it.polimi.ingsw.utils.Colour;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * View representing the main menu that allows the player to choose which component to switch to
 */
public class MainMenuView extends CliView{

    protected GameView gameView;
    protected GridView outerGrid;
    protected LabelView playersInfo;

    public MainMenuView(CliClientManager clientManager, GameView gameView, int rowSize, int columnSize) {
        super(clientManager, rowSize, columnSize);
        this.gameView = gameView;

        outerGrid = new GridView(clientManager, 2, 1, 1);
        outerGrid.setRowWeight(1, 19);
        addChildView(outerGrid, 0, 0);

        LabelView titleLabel = new LabelView(
            FormattedChar.convertStringToFormattedCharList(
                Localization.getLocalizationInstance().getString("client.mainMenu.gameTitle"),
                Colour.BLUE,
                Colour.BLACK,
                false,
                true,
                true
            ),
            clientManager
        );
        titleLabel.setHorizontalAlignment(FormattedCharsBufferUtils.HorizontalAlignment.CENTER);
        outerGrid.setView(0, 0, titleLabel);

        GridView playersGrid = new GridView(clientManager, 1, 1, 1);
        playersGrid.setBorderStyle(new LineBorderStyle());
        outerGrid.setView(1, 0, playersGrid);

        playersInfo = new LabelView(new ArrayList<>(), clientManager);
        playersGrid.setView(0, 0, playersInfo);

        clientManager.getGameContextRepresentation().getPlayersOrder().forEach(p -> {
            subscribeToRepresentation(clientManager.getGameContextRepresentation().getPlayerContext(p));
            subscribeToRepresentation(clientManager.getGameContextRepresentation().getFaithPath());
        });
        subscribeToRepresentation(clientManager.getGameContextRepresentation());

        startMainMenuDialog();
    }

    public MainMenuView(CliClientManager clientManager, GameView gameView) {
        this(clientManager, gameView, 0, 0);
    }

    void startMainMenuDialog() {
        UserChoicesUtils.PossibleUserChoices userChoices = UserChoicesUtils.makeUserChoose(clientManager);

        userChoices.addUserChoiceLocalized(
            () -> gameView.setMainContentView(new MarketView(clientManager, gameView)),
            "client.cli.mainMenuActions.openMarket"
        ).addUserChoiceLocalized(
            () -> gameView.setMainContentView(new DevCardTableView(clientManager, gameView)),
            "client.cli.mainMenuActions.openDevCardsTable"
        ).addUserChoiceLocalized(
            () -> gameView.setMainContentView(new FaithPathView(clientManager, gameView)),
            "client.cli.mainMenuActions.openFaithPath"
        ).addUserChoiceLocalized(
            () -> gameView.setMainContentView(new PlayerDashboardView(clientManager.getMyPlayer(), clientManager, gameView)),
            "client.cli.mainMenuActions.openPersonalDashboard"
        );

        if(!clientManager.getGameContextRepresentation().isSinglePlayerGame())
            userChoices.addUserChoiceLocalized(
            this::askThePlayerToOpenTheDashboardOf,
            "client.cli.mainMenuActions.openDifferentPlayerDashboard"
        );

        if(clientManager.getGameState() == GameState.MY_PLAYER_TURN_AFTER_MAIN_ACTION) {
            userChoices.addUserChoiceLocalized(this::endMyTurn, "client.cli.mainMenuActions.endMyTurn");
        }

        userChoices.apply();
    }

    protected void endMyTurn() {
        if (clientManager.getGameState() == GameState.MY_PLAYER_TURN_AFTER_MAIN_ACTION) {
            clientManager.sendMessageAndGetAnswer(
                new PlayerRequestClientMessage(new EndTurnClientRequest(clientManager.getMyPlayer()))
            ).thenCompose(serverMessage ->
                ServerMessageUtils.ifMessageTypeCompute(
                    serverMessage,
                    EndTurnServerMessage.class,
                    message -> {
                        clientManager.handleGameUpdates(message.gameUpdates);
                        gameView.setMainContentView(new MainMenuView(clientManager, gameView));
                        return CompletableFuture.completedFuture(null);
                    }
                ).elseIfMessageTypeCompute(
                    InvalidRequestServerMessage.class,
                    message -> {
                        clientManager.tellUser(message.errorMessage);
                        gameView.setMainContentView(new MainMenuView(clientManager, gameView));
                        return CompletableFuture.completedFuture(null);
                    }
                ).elseCompute(message -> {
                    gameView.setMainContentView(new MainMenuView(clientManager, gameView));
                    return CompletableFuture.completedFuture(null);
                }).apply()
            );
        } else {
            clientManager.tellUserLocalized("client.errors.turnCanNotEndBeforeMainAction");
            startMainMenuDialog();
        }
    }

    protected void askThePlayerToOpenTheDashboardOf() {
        clientManager.getGameContextRepresentation().getPlayersOrder().stream()
            .filter(player -> !player.equals(clientManager.getMyPlayer()))
            .forEach(anotherPlayer ->
                UserChoicesUtils.makeUserChoose(clientManager).addUserChoiceLocalized(
                    () -> gameView.setMainContentView(new PlayerDashboardView(anotherPlayer, clientManager, gameView)),
                    "client.cli.mainMenuActions.openDashboardOfPlayerName",
                    anotherPlayer.playerName
                ).apply()
            );
    }

    @Override
    public FormattedCharsBuffer getContentAsFormattedCharsBuffer() {

        playersInfo.setText(
            clientManager.getGameContextRepresentation().getPlayersOrder().stream()
                .flatMap(p ->
                    FormattedChar.convertStringToFormattedCharList(
                        " - " + p.playerName + "\n    "
                        + Localization.getLocalizationInstance().getString("client.mainMenu.resources")
                        + LocalizationUtils.getResourcesListAsCompactString(
                            clientManager.getGameContextRepresentation().getPlayerContext(p).getTotalResourcesOwnedByThePlayer())
                        + "\n    " + Localization.getLocalizationInstance().getString("client.mainMenu.faithPositions")
                        + clientManager.getGameContextRepresentation().getFaithPath().getFaithPositions().get(p)
                        + "\n\n",
                        clientManager.getGameContextRepresentation().getActivePlayer().equals(p) ?
                            Colour.GREEN : Colour.WHITE,
                        Colour.BLACK,
                        false,
                        p.equals(clientManager.getMyPlayer()),
                        false
                    ).stream()
                ).collect(Collectors.toList())
        );

        return super.getContentAsFormattedCharsBuffer();
    }

    @Override
    public void setRowSize(int rowSize) {
        outerGrid.setRowSize(rowSize);
        super.setRowSize(rowSize);
    }

    @Override
    public void setColumnSize(int columnSize) {
        outerGrid.setColumnSize(columnSize);
        super.setColumnSize(columnSize);
    }
}
