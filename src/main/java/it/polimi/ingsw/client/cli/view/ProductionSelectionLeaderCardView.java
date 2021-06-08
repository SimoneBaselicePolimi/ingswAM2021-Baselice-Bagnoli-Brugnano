package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ProductionSelectionLeaderCardView extends AbstractLeaderCardView{

    protected List<ClientProductionRepresentation> alreadySelectedProductions;

    protected Map<ResourceType, Integer> resourcesLeftToThePlayer;

    protected GameView gameView;

    public ProductionSelectionLeaderCardView(
        List<ClientProductionRepresentation> alreadySelectedProductions,
        Map<ResourceType, Integer> resourcesLeftToThePlayer,
        CliClientManager clientManager,
        GameView gameView
     ) {
        super(clientManager.getMyPlayer(), clientManager, gameView);

        this.alreadySelectedProductions = alreadySelectedProductions;
        this.resourcesLeftToThePlayer = resourcesLeftToThePlayer;

        askPlayerForLeaderCardsProduction();
    }

    CompletableFuture<Void> askPlayerForLeaderCardsProduction() {
        return clientManager.askUserLocalized("client.cli.playerDashboard.askPlayerForLeaderProductionsChoice")
            .thenCompose(input -> {
                int intInput = Integer.parseInt(input);
                if (intInput > 0 && intInput < leaderCardsPlayerContext.getActiveLeaderCardsProductions().size()) {
                    //TODO ask the user which production he wants to activate from that leader card (there can be more than one)
                    ClientProductionRepresentation production = new ArrayList<>(leaderCardsPlayerContext.getActiveLeaderCardsProductions()).get(intInput-1);
                    if(alreadySelectedProductions.contains(production)) {
                        clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerProductionAlreadyChosen");
                        return askPlayerForLeaderCardsProduction();
                    }
                    return checkIfThePlayerHasNecessaryResources(production);
                } else {
                    clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerProductionNumberIsInvalid");
                    return askPlayerForLeaderCardsProduction();
                }
            });
    }

    //TODO this method in ProductionSelectionDashboardView has changed (split in two different methods)
    CompletableFuture<Void> checkIfThePlayerHasNecessaryResources(ClientProductionRepresentation production){
        return null;
    }

}
