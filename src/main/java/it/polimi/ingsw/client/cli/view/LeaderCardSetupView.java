package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.ServerMessageUtils;
import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.UserChoicesUtils;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.cli.view.grid.GridView;
import it.polimi.ingsw.client.clientmessage.PlayerRequestClientMessage;
import it.polimi.ingsw.client.clientrequest.InitialChoicesClientRequest;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientResourceStorageRepresentation;
import it.polimi.ingsw.client.servermessage.GameUpdateServerMessage;
import it.polimi.ingsw.client.servermessage.InitialChoicesServerMessage;
import it.polimi.ingsw.client.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.utils.Colour;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * View representing the setup state: the player must choose the initial leader cards
 */
public class LeaderCardSetupView extends CliView {

    protected GameView gameView;
    protected GridView container;
    protected LabelView titleView;
    protected LeaderCardListView cardListView;

    protected InitialChoicesServerMessage initialChoicesServerMessage;

    protected List<ClientLeaderCardRepresentation> alreadySelectedCards = new ArrayList<>();

    protected int cardsLeftToChoose;

    public LeaderCardSetupView(
        CliClientManager clientManager,
        GameView gameView
    ) {
        super(clientManager);
        this.gameView = gameView;
        this.initialChoicesServerMessage =
            (InitialChoicesServerMessage) clientManager.getContextInfoMap().get("initialChoicesServerMessage");
        cardsLeftToChoose = initialChoicesServerMessage.numberOfLeaderCardsToKeep;

        clientManager.getGameContextRepresentation().getPlayersOrder().stream()
            .map(p -> clientManager.getGameContextRepresentation().getPlayerContext(p))
            .forEach(c -> c.setNumberOfLeaderCardsGivenToThePlayer(
                initialChoicesServerMessage.numberOfLeaderCardsToKeep
            ));

        container = new GridView(clientManager, 2, 1, 1);
        addChildView(container, 0,0);
        container.setRowWeight(1, 16);

        titleView = new LabelView(List.of(), clientManager);
        container.setView(0,0, titleView);

        cardListView = new LeaderCardListView(
            new ArrayList<>(initialChoicesServerMessage.leaderCardsGivenToThePlayer),
            true,
            clientManager
        );
        for(int l=1; l<=initialChoicesServerMessage.leaderCardsGivenToThePlayer.size(); l++) {
            cardListView.getLeaderCardViewByNumber(l).setBorderColour(Colour.GREY, false);
        }
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
                () -> gameView.setMainContentView(new PlayerDashboardView(clientManager.getMyPlayer(), clientManager, gameView)),
                "client.cli.mainMenuActions.openPersonalDashboard"
            ).apply();
    }

    CompletableFuture<List<ClientLeaderCardRepresentation>> selectCards() {
        if(cardsLeftToChoose == 0) {
            return CompletableFuture.completedFuture(alreadySelectedCards);
        } else {
            return clientManager.askUserLocalized("client.cli.gameSetup.leaderCardsDialog.choose")
                .thenCompose(input -> {
                    if (input.matches("\\G\\s*\\d+\\s*$")) {
                        int intInput = Integer.parseInt(input.replaceAll("\\D+",""));
                        if (intInput < 1 || intInput > cardListView.cardsToView.size()) {
                            clientManager.tellUserLocalized("client.cli.gameSetup.leaderCardsDialog.invalid");
                            return selectCards();
                        }
                        if (alreadySelectedCards.contains(cardListView.getLeaderCardRepresentationByNumber(intInput))) {
                            clientManager.tellUserLocalized("client.cli.gameSetup.leaderCardsDialog.alreadyChosen");
                            return selectCards();
                        }
                        alreadySelectedCards.add(cardListView.getLeaderCardRepresentationByNumber(intInput));
                        cardListView.getLeaderCardViewByNumber(intInput).setBorderColour(Colour.GREEN, false);
                        cardsLeftToChoose--;
                        updateView();
                    } else {
                        clientManager.tellUserLocalized("client.errors.invalidInput");
                    }
                    return selectCards();
                });
        }
    }

    void startLeaderCardChoiceDialog() {
        selectCards()
           .thenCompose(chosenCards -> {
               if(initialChoicesServerMessage.numberOfStarResources > 0) {
                   ResourcesChoiceView resourcesChoiceView = new ResourcesChoiceView(
                       initialChoicesServerMessage.numberOfStarResources,
                       Arrays.stream(ResourceType.values()).collect(Collectors.toSet()),
                       clientManager,
                       Localization.getLocalizationInstance().getString("client.cli.gameSetup.gameSetupInfo"),
                       resourcesChosen -> onAllChoicesDone(resourcesChosen, chosenCards)
                   );
                   gameView.setMainContentView(resourcesChoiceView);
               } else {
                   onAllChoicesDone(new HashMap<>(), chosenCards);
               }
               return CompletableFuture.completedFuture(null);
           });
    }

    void onAllChoicesDone(Map<ResourceType, Integer> resourcesChosen, List<ClientLeaderCardRepresentation> chosenCards) {
        ResourcesRepositioningDashboardView resRepositioningView = new ResourcesRepositioningDashboardView(
            resourcesChosen,
            false,
            clientManager,
            gameView
        );
        gameView.setMainContentView(resRepositioningView);
        resRepositioningView.setOnPositioningDoneCallback( (storagesModified, resourcesLeftInTempStorage) ->
            sendInitialChoicesToServer(
                new HashSet<>(chosenCards),
                storagesModified.stream()
                    .collect(Collectors.toMap(s -> s, ClientResourceStorageRepresentation::getResources))
            )
        );
    }


    CompletableFuture<Void> sendInitialChoicesToServer(
        Set<ClientLeaderCardRepresentation> leaderCardsChosenByThePlayer,
        Map<ClientResourceStorageRepresentation, Map<ResourceType, Integer>> chosenResourcesToAddByStorage
    ){
        return clientManager.sendMessageAndGetAnswer(new PlayerRequestClientMessage(
            new InitialChoicesClientRequest(
                clientManager.getMyPlayer(),
                leaderCardsChosenByThePlayer,
                chosenResourcesToAddByStorage
            )
        )).thenCompose(serverMessage ->
            ServerMessageUtils.ifMessageTypeCompute(
                serverMessage,
                GameUpdateServerMessage.class,
                message -> {
                    if(clientManager.getMyPlayer().equals(clientManager.getGameContextRepresentation().getActivePlayer()))
                        clientManager.setGameState(GameState.MY_PLAYER_TURN_BEFORE_MAIN_ACTION);
                    else
                        clientManager.setGameState(GameState.ANOTHER_PLAYER_TURN);
                    clientManager.handleGameUpdates(message.gameUpdates);
                    gameView.setMainContentView(new MainMenuView(clientManager, gameView));
                    return CompletableFuture.<Void>completedFuture(null);
                }
            ).elseIfMessageTypeCompute(
                InvalidRequestServerMessage.class,
                message -> {
                    clientManager.tellUser(message.errorMessage);
                    gameView.setMainContentView(new LeaderCardSetupView(clientManager, gameView));
                    return CompletableFuture.completedFuture(null);
                }
            ).elseCompute(message -> {
                gameView.setMainContentView(new LeaderCardSetupView(clientManager, gameView));
                return CompletableFuture.completedFuture(null);
            }).apply()
        );
    }

}
