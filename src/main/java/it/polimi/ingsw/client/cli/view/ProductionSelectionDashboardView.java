package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.ServerMessageUtils;
import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.DialogUtils;
import it.polimi.ingsw.client.cli.ProductionsSelectionInfo;
import it.polimi.ingsw.client.cli.UserChoicesUtils;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.cli.view.grid.GridView;
import it.polimi.ingsw.client.cli.view.grid.LineBorderStyle;
import it.polimi.ingsw.client.clientmessage.PlayerRequestClientMessage;
import it.polimi.ingsw.client.clientrequest.ProductionActionClientRequest;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.client.servermessage.GameUpdateServerMessage;
import it.polimi.ingsw.client.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.ResourceUtils;
import it.polimi.ingsw.utils.Colour;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ProductionSelectionDashboardView extends AbstractPlayerDashboardView {

    protected ProductionsSelectionInfo selectionInfo;

    protected GameView gameView;

    public ProductionSelectionDashboardView(
        ProductionsSelectionInfo selectionInfo,
        CliClientManager clientManager,
        GameView gameView
    ) {
        super(clientManager.getMyPlayer(), clientManager, gameView);
        this.selectionInfo = selectionInfo;

        askPlayerForProduction();
    }


    CompletableFuture<Void> askPlayerForProduction(){

        return UserChoicesUtils.makeUserChoose(clientManager)
            .addUserChoice(
                this::askPlayerForTypeOfProductions,
                "client.cli.playerDashboard.activateNewProduction"
            )
            .addUserChoice(
                this::sendPlayerChoiceToServer,
                "client.cli.playerDashboard.endProductionsActivation"
            )
            .addUserChoice(
                () -> gameView.setMainContentView(new PlayerDashboardView(
                    activePlayer,
                    clientManager,
                    gameView
                )),
                "client.cli.playerDashboard.cancelSelectedProductions"
            ).apply();
    }

    CompletableFuture<Void> askPlayerForTypeOfProductions() {
        return UserChoicesUtils.makeUserChoose(clientManager)
            .addUserChoice(
                () -> askPlayerForBaseProduction()
                    .exceptionally(e -> {
                        askPlayerForProduction();
                        return null;
                    }),
                "client.cli.playerDashboard.activateBaseProduction"
            )
            .addUserChoice(
                this::askPlayerForDashboardProductions,
                "client.cli.playerDashboard.activateDashboardProductions"
            )
            .addUserChoice(
                () -> gameView.setMainContentView(new ProductionSelectionLeaderCardView(
                    selectionInfo,
                    clientManager,
                    gameView
                )),
                "client.cli.playerDashboard.leaderCardList"
            ).addUserChoice(
                () -> gameView.setMainContentView(new MainMenuView(clientManager)),
                "client.cli.game.returnToMenu"
            ).apply();
    }

    CompletableFuture<Void> askPlayerForDashboardProductions () {

        return clientManager.askUserLocalized("client.cli.playerDashboard.askPlayerForDevProductionsChoice")
            .thenCompose(input -> {
                int intInput = Integer.parseInt(input);
                if(intInput > 0 && intInput <= dashboardPlayerContext.getDevelopmentCardDecks().size()) {
                    ClientProductionRepresentation production = dashboardPlayerContext.getDevelopmentCardDecks()
                        .get(intInput - 1).peek().getProduction();
                    return DialogUtils.onSelectedProductionDialog(production, selectionInfo, clientManager);
                }
                else {
                    clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerProductionNumberIsInvalid");
                    return askPlayerForDashboardProductions();
                }
            });
    }

    CompletableFuture<Void> askPlayerForBaseProduction() {

        return clientManager.askUserLocalized("client.cli.playerDashboard.askPlayerForBaseProductionChoice")
            .thenCompose(input -> {
                int intInput = Integer.parseInt(input);
                if(intInput > 0 && intInput <= dashboardPlayerContext.getBaseProductions().size()) {
                    ClientProductionRepresentation production = baseProductions.get(intInput-1);
                    return DialogUtils.onSelectedProductionDialog(production, selectionInfo, clientManager);
                }
                else {
                    clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerProductionNumberIsInvalid");
                    return askPlayerForBaseProduction();
                }
            });
    }

    protected boolean checkIfThePlayerHasNecessaryResources(ClientProductionRepresentation production) {
        int numOfStarResources = production.getStarResourceCost();
        int totalResourcesCost = production.getResourceCost().values().stream().reduce(0, Integer::sum);
        return (ResourceUtils.areResourcesAContainedInB(
            production.getResourceCost(),
            selectionInfo.getResourcesLeftToThePlayer()
        ))
            && (totalResourcesCost + numOfStarResources
            >= selectionInfo.getResourcesLeftToThePlayer().values().stream().reduce(0, Integer::sum));
    }

    CompletableFuture<Void> sendPlayerChoiceToServer(){
        return clientManager.sendMessageAndGetAnswer(new PlayerRequestClientMessage(
            new ProductionActionClientRequest(
                activePlayer,
                new HashSet<>(selectionInfo.getAlreadySelectedProductions()),
                selectionInfo.getTotalStarResourcesProductionCost(),
                selectionInfo.getTotalStarResourcesProductionReward()
            )
        )).thenCompose(serverMessage ->
            ServerMessageUtils.ifMessageTypeCompute(
                serverMessage,
                GameUpdateServerMessage.class,
                message -> {
                    clientManager.setGameState(GameState.MY_PLAYER_TURN_AFTER_MAIN_ACTION);
                    clientManager.handleGameUpdates(message.gameUpdates);
                    gameView.setMainContentView(new PlayerDashboardView(
                        activePlayer,
                        clientManager,
                        gameView
                    ));
                    return CompletableFuture.<Void>completedFuture(null);
                }
            ).elseIfMessageTypeCompute(
                InvalidRequestServerMessage.class,
                message -> {
                    clientManager.tellUser(message.errorMessage);
                    gameView.setMainContentView(new PlayerDashboardView(
                        activePlayer,
                        clientManager,
                        gameView
                    ));
                    return CompletableFuture.completedFuture(null);
                }
            ).elseCompute(message -> {
                gameView.setMainContentView(new PlayerDashboardView(
                    activePlayer,
                    clientManager,
                    gameView
                ));
                return CompletableFuture.completedFuture(null);
                }).apply()
        );
    }

    @Override
    public FormattedCharsBuffer getContentAsFormattedCharsBuffer() {

        devCardDashboardDeckViewList.stream()
            .filter( d -> d.getCardDeck().numberOfCardsInDeck()>0)
            .forEach(cardDeckView -> {
                ClientProductionRepresentation production = cardDeckView.getCardOnTop().getProduction();

                if(selectionInfo.getAlreadySelectedProductions().contains(production))
                    cardDeckView.setCardBorderColour(Colour.GREEN);
                else if (checkIfThePlayerHasNecessaryResources(production))
                    cardDeckView.setCardBorderColour(Colour.YELLOW);
                else
                    cardDeckView.setCardBorderColour(Colour.GREY);
            });

        for (int p = 0; p < baseProductions.size(); p++) {
            ClientProductionRepresentation productionRepresentation = baseProductions.get(p);
            GridView productionGrid = baseProductionGridLists.get(p);
            if(selectionInfo.getAlreadySelectedProductions().contains(productionRepresentation))
                productionGrid.setBorderStyle(new LineBorderStyle(Colour.GREEN));
            else if (checkIfThePlayerHasNecessaryResources(productionRepresentation))
                productionGrid.setBorderStyle(new LineBorderStyle(Colour.YELLOW));
            else
                productionGrid.setBorderStyle(new LineBorderStyle(Colour.GREY));
        }

        return super.getContentAsFormattedCharsBuffer();
    }

}
