package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.server.model.Player;
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
        super(clientManager);

        this.alreadySelectedProductions = alreadySelectedProductions;
        this.resourcesLeftToThePlayer = resourcesLeftToThePlayer;

        askPlayerForLeaderCardsProduction();
    }

    CompletableFuture<Void> askPlayerForLeaderCardsProduction() {
        return null;
    }
}
