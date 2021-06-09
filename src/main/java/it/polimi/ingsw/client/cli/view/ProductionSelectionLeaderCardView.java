package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.DialogUtils;
import it.polimi.ingsw.client.cli.ProductionsSelectionInfo;
import it.polimi.ingsw.client.cli.UserChoicesUtils;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;


import java.util.concurrent.CompletableFuture;

public class ProductionSelectionLeaderCardView extends AbstractLeaderCardView{

    protected ProductionsSelectionInfo selectionInfo;

    protected GameView gameView;

    public ProductionSelectionLeaderCardView(
        ProductionsSelectionInfo selectionInfo,
        CliClientManager clientManager,
        GameView gameView
     ) {
        super(clientManager.getMyPlayer(), clientManager, gameView);
        this.selectionInfo = selectionInfo;

        askPlayerForLeaderCardsProduction();
    }

    CompletableFuture<Void> askPlayerForLeaderCardsProduction() {

        return UserChoicesUtils.makeUserChoose(clientManager)
            .addUserChoice(
                () -> askPlayerForLeaderCard()
                    .exceptionally(e -> {
                        askPlayerForLeaderCard();
                        return null;
                    }),
                "client.cli.playerDashboard.activateNewLeaderCardProduction"
            )

            .addUserChoice(
                () -> gameView.setMainContentView(new ProductionSelectionDashboardView(
                    selectionInfo,
                    clientManager,
                    gameView
                )),
                "client.cli.playerDashboard.returnToProductionActivation"
            ).apply();
    }

    CompletableFuture<Void> askPlayerForLeaderCard(){
        return clientManager.askUserLocalized("client.cli.playerDashboard.askPlayerForLeaderProductionsChoice")
            .thenCompose(input -> {
                int intInput = Integer.parseInt(input);
                if (intInput > 0 && intInput <= leaderCardList.size()) {
                    if(leaderCardList.get(intInput - 1).getState().equals(LeaderCardState.ACTIVE)) {
                        ClientLeaderCardRepresentation selectedLeaderCard = leaderCardList.get(intInput - 1);
                        return askPlayerForTypeOfLeaderCardsProduction(selectedLeaderCard);
                    }
                    else {
                        clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerLeaderCardIsNotActive");
                        return askPlayerForLeaderCardsProduction();
                    }
                } else {
                    clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerProductionNumberIsInvalid");
                    return askPlayerForLeaderCardsProduction();
                }
            });
    }

    CompletableFuture<Void> askPlayerForTypeOfLeaderCardsProduction(ClientLeaderCardRepresentation selectedLeaderCard){
        return clientManager.askUserLocalized("client.cli.playerDashboard.askPlayerForTypeOfLeaderProductionsChoice")
            .thenCompose(input -> {
                int intInput = Integer.parseInt(input);
                if (intInput > 0 && intInput >= selectedLeaderCard.getProductions().size()){
                    ClientProductionRepresentation production = selectedLeaderCard.getProductions().get(intInput - 1);
                    return DialogUtils.onSelectedProductionDialog(production, selectionInfo, clientManager);
                }
                else {
                    clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerProductionNumberIsInvalid");
                    return askPlayerForTypeOfLeaderCardsProduction(selectedLeaderCard);
                }
            });
    }
}
