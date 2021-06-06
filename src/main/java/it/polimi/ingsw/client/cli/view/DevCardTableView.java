package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.UserChoicesUtils;
import it.polimi.ingsw.client.cli.view.grid.GridView;
import it.polimi.ingsw.client.clientmessage.PlayerRequestClientMessage;
import it.polimi.ingsw.client.clientrequest.DevelopmentActionClientRequest;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientCoveredCardsDeckRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientPlayerOwnedDevelopmentCardDeckRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardsTableRepresentation;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class DevCardTableView extends CliView{

    public static final int SPACE_BETWEEN_CARDS = 2;

    protected GameView gameView;
    protected DevelopmentCardColour colour;
    protected DevelopmentCardLevel level;
    protected int deckNumber;
    protected ClientDevelopmentCardRepresentation developmentCard;

    protected DevelopmentCardLevel visibleCardsLevel;

    protected ClientDevelopmentCardsTableRepresentation table;
    protected Map<DevelopmentCardColour, ClientCoveredCardsDeckRepresentation<ClientDevelopmentCardRepresentation>> oneLevelCards;
    protected Player activePlayer;
    protected ClientPlayerContextRepresentation activePlayerContext;

    protected GridView developmentCardTableGrid;

    public DevCardTableView(CliClientManager clientManager, GameView gameView) {
        super(clientManager);
        this.gameView = gameView;

        table = clientManager.getGameContextRepresentation().getDevelopmentCardsTable();
        activePlayer = clientManager.getGameContextRepresentation().getActivePlayer();
        activePlayerContext = clientManager.getGameContextRepresentation().getPlayerContext(activePlayer);

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

        int gridRowSize = 2*SPACE_BETWEEN_CARDS + DevCardView.DEV_CARD_ROW_SIZE;
        int gridColSize =
            SPACE_BETWEEN_CARDS + visibleCards.size()*(DevCardView.DEV_CARD_COL_SIZE + SPACE_BETWEEN_CARDS);

        developmentCardTableGrid = new GridView(
            clientManager,
            1,
            visibleCards.size(),
            SPACE_BETWEEN_CARDS,
            gridRowSize,
            gridColSize
        );

        addChildView(developmentCardTableGrid, 0, 0);

        for (int i = 0; i < visibleCards.size(); i++) {
            developmentCardTableGrid.setView(
                0,
                i,
                new DevCardTableDeckView(visibleCards.get(i).getColour(), visibleCardsLevel, clientManager)
            );
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
                () -> askPlayerForDevCardLevelChoice()
                    .thenCompose(input ->
                        clientManager.sendMessageAndGetAnswer(new PlayerRequestClientMessage(
                            new DevelopmentActionClientRequest(
                                activePlayer,
                                developmentCard,
                                deckNumber
                            )
                        )
                    )),
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
                () -> gameView.setMainContentView(new MainMenuView(clientManager)),
                "client.cli.game.returnToMenu"
            );
        }

        choices.apply();
    }

    CompletableFuture<Integer> askPlayerForDevCardLevelChoice (){
        return clientManager.askUserLocalized("client.cli.devCardTable.askForDevCardLevelChoice")
            .thenCompose(levelInput -> {
                int intLevelInput = Integer.parseInt(levelInput);
                this.level = DevelopmentCardLevel.forValue(intLevelInput);
                if (level != null)
                    return askPlayerForDevCardColourChoice();
                else {
                    clientManager.tellUserLocalized("client.cli.devCardTable.notifyPlayerDevCardLevelIsInvalid");
                    return askPlayerForDevCardLevelChoice();
                }
            });
    }

    CompletableFuture<Integer> askPlayerForDevCardColourChoice() {
        return clientManager.askUserLocalized("client.cli.devCardTable.askForDevCardColourChoice")
            .thenCompose(colourInput -> {
                this.colour = DevelopmentCardColour.getColourFromLocalizedName(colourInput);
                if (colour != null) {
                    oneLevelCards = new HashMap<>(table.getCards().get(level));
                    if (oneLevelCards.get(colour) != null) {
                        developmentCard = oneLevelCards.get(colour).cardOnTop;
                        return checkThePlayerHasNecessaryResources();
                    }
                    else
                        clientManager.tellUserLocalized("client.cli.devCardTable.notifyPlayerDeckIsEmpty");
                        return askPlayerForDevCardLevelChoice();
                }
                else {
                    clientManager.tellUserLocalized("client.cli.devCardTable.notifyPlayerDevCardColourIsInvalid");
                    return askPlayerForDevCardColourChoice();
                }
            });
    }

    private CompletableFuture<Integer> checkThePlayerHasNecessaryResources(){
        if (table.isCardPurchasable(developmentCard))
            return askPlayerForDeckNumber();
        else {
            clientManager.tellUserLocalized("client.cli.devCardTable.notifyPlayerHeDoesNotHaveNeededResources");
            return askPlayerForDevCardLevelChoice();
        }
    }

    CompletableFuture<Integer> askPlayerForDeckNumber() {
        return clientManager.askUserLocalized("client.cli.devCardTable.askForDeckNumber")
            .thenCompose(input -> {
                int intInput = Integer.parseInt(input);
                this.deckNumber = intInput;
                ClientPlayerOwnedDevelopmentCardDeckRepresentation playerDeck = activePlayerContext.getDevelopmentCardDecks().get(deckNumber);
                if (!playerDeck.getCardDeck().isEmpty() && playerDeck.getCardDeck().peek().getLevel().toValue() < developmentCard.getLevel().toValue())
                    return CompletableFuture.completedFuture(intInput);
                else {
                    clientManager.tellUserLocalized("client.cli.devCardTable.notifyPlayerDeckNumberIsInvalid");
                    // if I ask him again for the deckNumber the game could be stopped:
                    // the player might not have valid spaces on which to place the card.
                    // I have to ask him for the level again.
                    return askPlayerForDevCardLevelChoice();
                }
            });
    }

}