package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.UserChoicesUtils;
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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class DevCardTableView extends CliView{

    protected GameView gameView;
    protected DevelopmentCardColour colour;
    protected DevelopmentCardLevel level;
    protected int deckNumber;
    protected ClientDevelopmentCardRepresentation developmentCard;

    ClientDevelopmentCardsTableRepresentation table = clientManager.getGameContextRepresentation().getDevelopmentCardsTable();
    Map<DevelopmentCardColour, ClientCoveredCardsDeckRepresentation<ClientDevelopmentCardRepresentation>> oneLevelCards;
    Player activePlayer;
    ClientPlayerContextRepresentation activePlayerContext;

    public DevCardTableView(CliClientManager clientManager, GameView gameView) {
        super(clientManager);
        this.gameView = gameView;
        startDevCardTableDialog();
    }

    void startDevCardTableDialog() {

        activePlayer = clientManager.getGameContextRepresentation().getActivePlayer();
        activePlayerContext = clientManager.getGameContextRepresentation().getPlayerContext(activePlayer);

        //game setup
        if (clientManager.getGameState().equals(GameState.GAME_SETUP)) {
            UserChoicesUtils.makeUserChoose(clientManager)
                .addUserChoiceLocalized(
                    () -> gameView.setMainContentView(new LeaderCardSetupView(clientManager, gameView)),
                    "client.cli.game.returnToSetupView"
                ).apply();
        } else if(!clientManager.getMyPlayer().equals(activePlayer)) { //game started and my player is not the active player
            UserChoicesUtils.makeUserChoose(clientManager)
                .addUserChoice(
                    () -> gameView.setMainContentView(new MainMenuView(clientManager)),
                    "client.cli.game.returnToMenu"
                ).apply();

        //game started and my player is the active player
        } else{
            UserChoicesUtils.makeUserChoose(clientManager)
                .addUserChoice(
                    () -> askPlayerForDevCardLevelChoice()
                        .thenCompose(input ->
                            clientManager.sendMessageAndGetAnswer(new PlayerRequestClientMessage(
                                new DevelopmentActionClientRequest(
                                    activePlayer,
                                    developmentCard,
                                    deckNumber
                                )
                            ))),
                    "client.cli.devCardTable.devCardChoice"
                )
                .addUserChoice(
                    () -> gameView.setMainContentView(new MainMenuView(clientManager)),
                    "client.cli.game.returnToMenu"
                ).apply();
        }
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

