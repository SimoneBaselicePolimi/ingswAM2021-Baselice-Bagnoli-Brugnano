package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.UserChoicesUtils;
import it.polimi.ingsw.client.clientmessage.PlayerRequestClientMessage;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientCoveredCardsDeckRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardsTableRepresentation;
import it.polimi.ingsw.network.clientrequest.DevelopmentActionClientRequest;
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

    ClientDevelopmentCardsTableRepresentation table = clientManager.getGameContextRepresentation().getDevelopmentCardsTable();
    Map<DevelopmentCardColour, ClientCoveredCardsDeckRepresentation<ClientDevelopmentCardRepresentation>> oneLevelCards;
    Player activePlayer = clientManager.getGameContextRepresentation().getActivePlayer();
    ClientPlayerContextRepresentation playerContextActivePlayer = clientManager.getGameContextRepresentation().getPlayerContext(activePlayer);

    public DevCardTableView(CliClientManager clientManager, GameView gameView) {
        super(clientManager);
        this.gameView = gameView;
        startDevCardTableDialog();
    }

    void startDevCardTableDialog() {
        UserChoicesUtils.makeUserChoose(clientManager)
            .addUserChoice(
                () -> askPlayerForDevCardLevelChoice()
                    .thenCompose(developmentCard ->
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
                    if (oneLevelCards.get(colour) != null)
                        return checkThePlayerHasNecessaryResources();
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

    //TODO
    private CompletableFuture<Integer> checkThePlayerHasNecessaryResources(){
        playerContextActivePlayer.
        return null;
    }
    //Map<ResourceType, Integer> playerResources = playerContext.getAllResources();
    //        for (ResourceType resourceType : requestToValidate.developmentCard.getPurchaseCost().keySet()) {
    //            if (!playerResources.containsKey(resourceType)
    //                || playerResources.get(resourceType) < requestToValidate.developmentCard.getPurchaseCost().get(resourceType)
    //            )
    //                return createInvalidRequestServerMessage(
    //                    "The player does not have the resources needed to obtain the development card"
    //                );
    //        }

    //TODO
//    CompletableFuture<Integer> askPlayerForDeckNumber(){
//        return clientManager.askUserLocalized("client.cli.devCardTable.askForDeckNumber")
//            .thenCompose(input -> {
//                int intInput = Integer.parseInt(input);
//                this.deckNumber = intInput;
//                if(playerContextActivePlayer
//            });
//    }


}

