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
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class DevCardTableView extends CliView{

    protected GameView gameView;
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
                () -> gameView.setMainContentView(new MainMenuView(clientManager)),
                "client.cli.game.returnToMenu"
            ).apply();
    }

    CompletableFuture<Integer> askPlayerForDevCardLevelChoice (){
        return clientManager.askUserLocalized("client.cli.devCardTable.askForDevCardLevelChoice")
            .thenCompose(levelInput -> {
                int intLevelInput = Integer.parseInt(levelInput);
                if (DevelopmentCardLevel.forValue(intLevelInput) != null)
                    return askPlayerForDevCardColourChoice(DevelopmentCardLevel.forValue(intLevelInput));
                else {
                    clientManager.tellUserLocalized("client.cli.devCardTable.notifyPlayerDevCardLevelIsInvalid");
                    return askPlayerForDevCardLevelChoice();
                }
            });
    }

    CompletableFuture<Integer> askPlayerForDevCardColourChoice(DevelopmentCardLevel level) {
        return clientManager.askUserLocalized("client.cli.devCardTable.askForDevCardColourChoice")
            .thenCompose(colourInput -> {
                DevelopmentCardColour colour = DevelopmentCardColour.getColourFromLocalizedName(colourInput);
                if (colour != null) {
                    oneLevelCards = new HashMap<>(table.getCards().get(level));
                    if (oneLevelCards.get(colour) != null)
                        return checkThePlayerHasNecessaryResources(level, colour);
                    else
                        clientManager.tellUserLocalized("client.cli.devCardTable.notifyPlayerDeckIsEmpty");
                        return askPlayerForDevCardLevelChoice();
                }
                else {
                    clientManager.tellUserLocalized("client.cli.devCardTable.notifyPlayerDevCardColourIsInvalid");
                    return askPlayerForDevCardColourChoice(level);
                }
            });
    }

    //TODO
    private CompletableFuture<Integer> checkThePlayerHasNecessaryResources(
        DevelopmentCardLevel level,
        DevelopmentCardColour colour
    ){
        return null;
    }

    //TODO
//    CompletableFuture<Integer> askPlayerForDeckNumber(){
//        return clientManager.askUserLocalized("client.cli.devCardTable.askForDeckNumber")
//            .thenCompose(input -> {
//                int intInput = Integer.parseInt(input);
//                if(playerContextActivePlayer
//            });
//    }

}

