package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.ServerMessageUtils;
import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.UserChoicesUtils;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.cli.view.grid.GridView;
import it.polimi.ingsw.client.clientmessage.PlayerRequestClientMessage;
import it.polimi.ingsw.client.clientrequest.DevelopmentActionClientRequest;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientCoveredCardsDeckRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientPlayerOwnedDevelopmentCardDeckRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardsTableRepresentation;
import it.polimi.ingsw.client.servermessage.GameUpdateServerMessage;
import it.polimi.ingsw.client.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.utils.Colour;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class DevCardTableView extends CliView{

    public static final int SPACE_BETWEEN_CARDS = 2;

    protected GameView gameView;

    protected DevelopmentCardLevel visibleCardsLevel;

    protected ClientDevelopmentCardsTableRepresentation table;
    protected Player activePlayer;
    protected ClientPlayerContextRepresentation activePlayerContext;

    protected GridView developmentCardTableGrid;

    protected List<DevCardTableDeckView> cardTableDeckViewList;

    public DevCardTableView(CliClientManager clientManager, GameView gameView) {
        super(clientManager);
        this.gameView = gameView;

        table = clientManager.getGameContextRepresentation().getDevelopmentCardsTable();
        activePlayer = clientManager.getGameContextRepresentation().getActivePlayer();
        activePlayerContext = clientManager.getGameContextRepresentation().getPlayerContext(activePlayer);

        subscribeToRepresentation(table);

        setVisibleCards(DevelopmentCardLevel.FIRST_LEVEL);
        startDevCardTableDialog();
    }

    protected void setVisibleCards(DevelopmentCardLevel cardLevel) {
        visibleCardsLevel = cardLevel;
        List<ClientDevelopmentCardRepresentation> visibleCards = table.getCards().get(cardLevel).values().stream()
            .map(ClientCoveredCardsDeckRepresentation::getCardOnTop)
            .collect(Collectors.toList());

        if(developmentCardTableGrid != null) {
            developmentCardTableGrid.destroyView();
            children.clear();
        }

        int gridRowSize = 2*SPACE_BETWEEN_CARDS + DevCardTableDeckView.DEV_CARD_DECK_ROW_SIZE;
        int gridColSize = SPACE_BETWEEN_CARDS +
            visibleCards.size()*(DevCardTableDeckView.DEV_CARD_DECK_COL_SIZE + SPACE_BETWEEN_CARDS);

        developmentCardTableGrid = new GridView(
            clientManager,
            1,
            visibleCards.size(),
            SPACE_BETWEEN_CARDS,
            gridRowSize,
            gridColSize
        );

        addChildView(developmentCardTableGrid, 0, 0);

        cardTableDeckViewList = new ArrayList<>();
        for (int i = 0; i < visibleCards.size(); i++) {
            DevCardTableDeckView deckView = new DevCardTableDeckView(
                visibleCards.get(i).getColour(), visibleCardsLevel, clientManager
            );
            developmentCardTableGrid.setView(0, i, deckView);
            cardTableDeckViewList.add(deckView);
        }

    }

    void startDevCardTableDialog() {

        UserChoicesUtils.PossibleUserChoices choices = UserChoicesUtils.makeUserChoose(clientManager);

        //Change visible cards
        for (
            DevelopmentCardLevel level : Arrays.stream(DevelopmentCardLevel.values())
                .filter(l -> !l.equals(visibleCardsLevel))
                .collect(Collectors.toList())
        ) {
            choices.addUserChoiceLocalized(
                () -> {
                    setVisibleCards(level);
                    updateView();
                    startDevCardTableDialog();
                },
                "client.cli.devCardTable.viewCardOfLevel",
                level.toValue()
            );
        }

        //Game started, my player is the active player and he has not done a main action yet
        if(clientManager.getGameState().equals(GameState.MY_PLAYER_TURN_BEFORE_MAIN_ACTION)) {
            choices.addUserChoiceLocalized(
                () -> {
                    CompletableFuture<ClientDevelopmentCardRepresentation> f = askPlayerForDevCard();
                    f.exceptionally(e -> {
                        startDevCardTableDialog();
                        return null;
                    });
                    f.thenCompose(card -> {
                        List<Integer> validDeckIndexesForCard = getPlayerDashboardDecksForCard(card);

                        //check that there is at least one deck on the player dashboard where it is possible to add the
                        // selected card
                        if (validDeckIndexesForCard.size() > 0) {
                            return askPlayerForDeckNumber(
                                activePlayerContext.getDevelopmentCardDecks().size(),
                                validDeckIndexesForCard
                            ).thenCompose(selectedDeckIndex -> sendPlayerChoiceToServer(card, selectedDeckIndex));
                        } else {
                            clientManager.tellUserLocalized(
                                "client.cli.devCardTable.notifyAllPlayerDecksAreInvalid"
                            );
                            startDevCardTableDialog();
                            return CompletableFuture.failedFuture(new IllegalAccessException());
                        }
                    });
                },
                "client.cli.devCardTable.devCardChoice",
                visibleCardsLevel.toValue()
            );
        }

        if (clientManager.getGameState().equals(GameState.GAME_SETUP)) {
            choices.addUserChoiceLocalized(
                () -> gameView.setMainContentView(new LeaderCardSetupView(clientManager, gameView)),
                "client.cli.game.returnToSetupView"
            );
        } else {
            choices.addUserChoiceLocalized(
                () -> gameView.setMainContentView(new MainMenuView(clientManager, gameView)),
                "client.cli.game.returnToMenu"
            );
        }

        choices.apply();
    }

    CompletableFuture<ClientDevelopmentCardRepresentation> askPlayerForDevCard() {
        return clientManager.askUserLocalized("client.cli.devCardTable.askForDevCardColourChoice")
            .thenCompose(colourInput -> {
                DevelopmentCardColour colour = DevelopmentCardColour.getColourFromLocalizedName(colourInput);
                if (colour != null) {
                    Map<
                        DevelopmentCardColour,
                        ClientCoveredCardsDeckRepresentation<ClientDevelopmentCardRepresentation>
                    > oneLevelCards = table.getCards().get(visibleCardsLevel);
                    if (oneLevelCards.containsKey(colour) && oneLevelCards.get(colour).numberOfCardsInDeck > 0) {
                        if(table.isCardPurchasableByMyPlayer(oneLevelCards.get(colour).cardOnTop))
                            return CompletableFuture.completedFuture(oneLevelCards.get(colour).cardOnTop);
                        else {
                            clientManager.tellUserLocalized("client.cli.devCardTable.notifyPlayerHeDoesNotHaveNeededResources");
                            CompletableFuture.failedFuture(new IllegalAccessException());
                        }
                    }
                    else
                        clientManager.tellUserLocalized("client.cli.devCardTable.notifyPlayerDeckIsEmpty");
                        return CompletableFuture.failedFuture(new IllegalAccessException());
                }
                else {
                    clientManager.tellUserLocalized("client.cli.devCardTable.notifyPlayerDevCardColourIsInvalid");
                    return askPlayerForDevCard();
                }
            });
    }

    List<Integer> getPlayerDashboardDecksForCard(ClientDevelopmentCardRepresentation card) {
        List<ClientPlayerOwnedDevelopmentCardDeckRepresentation> decks = activePlayerContext.getDevelopmentCardDecks();
        return decks.stream()
            .filter(d ->
                (card.getLevel().equals(DevelopmentCardLevel.FIRST_LEVEL) && d.getCardDeck().isEmpty()) ||
                d.getCardDeck().peek().getLevel().toValue() == card.getLevel().toValue() - 1
            ).map(decks::indexOf)
            .collect(Collectors.toList());
    }

    CompletableFuture<Integer> askPlayerForDeckNumber(int numOfDecksInDashboard, List<Integer> validDecksIndexes) {
        return clientManager.askUserLocalized("client.cli.devCardTable.askForDeckNumber")
            .thenCompose(input -> {
                if (input.matches("\\G\\s*\\d+\\s*$")) {
                    int intInput = Integer.parseInt(input.replaceAll("\\D+",""));
                    if (intInput < 1 || intInput > numOfDecksInDashboard) {
                        clientManager.tellUserLocalized("client.cli.devCardTable.notifyPlayerDeckNumberIsInvalid");
                        return askPlayerForDeckNumber(numOfDecksInDashboard, validDecksIndexes);
                    } else if (!validDecksIndexes.contains(intInput)) {
                        clientManager.tellUserLocalized("client.cli.devCardTable.notifyPlayerCannotAddCardToDeck");
                        return askPlayerForDeckNumber(numOfDecksInDashboard, validDecksIndexes);
                    } else {
                        return CompletableFuture.completedFuture(intInput);
                    }
                } else {
                    clientManager.tellUserLocalized("client.errors.invalidInput");
                    return askPlayerForDeckNumber(numOfDecksInDashboard, validDecksIndexes);
                }
            });
    }

    CompletableFuture<Void> sendPlayerChoiceToServer(ClientDevelopmentCardRepresentation cardToBuy, int deckIndex) {
        return clientManager.sendMessageAndGetAnswer(new PlayerRequestClientMessage(
            new DevelopmentActionClientRequest(activePlayer, cardToBuy, deckIndex)
        )).thenCompose(serverMessage ->
            ServerMessageUtils.ifMessageTypeCompute(
                serverMessage,
                GameUpdateServerMessage.class,
                message -> {
                    clientManager.setGameState(GameState.MY_PLAYER_TURN_AFTER_MAIN_ACTION);
                    clientManager.handleGameUpdates(message.gameUpdates);
                    return CompletableFuture.<Void>completedFuture(null);
                }
            ).elseIfMessageTypeCompute(
                InvalidRequestServerMessage.class,
                message -> {
                    clientManager.tellUser(message.errorMessage);
                    startDevCardTableDialog();
                    return CompletableFuture.completedFuture(null);
                }
            ).elseCompute(message -> {
                startDevCardTableDialog();
                return CompletableFuture.completedFuture(null);
            }).apply()
        );
    }

    @Override
    public FormattedCharsBuffer getContentAsFormattedCharsBuffer() {
        
        cardTableDeckViewList.forEach(d -> {
            if(table.isCardPurchasableByMyPlayer(d.getCardOnTop()))
                d.setCardBorderColour(Colour.YELLOW);
            else
                d.setCardBorderColour(Colour.GREY);
        });

        return super.getContentAsFormattedCharsBuffer();
    }

}