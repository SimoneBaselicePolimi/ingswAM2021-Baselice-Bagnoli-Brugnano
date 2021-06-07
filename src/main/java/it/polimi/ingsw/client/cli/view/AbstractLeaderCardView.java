package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientResourceStorageRepresentation;
import it.polimi.ingsw.server.model.Player;

import java.util.List;

public class AbstractLeaderCardView extends CliView{

    protected Player leaderCardsPlayer, activePlayer;
    protected ClientPlayerContextRepresentation leaderCardsPlayerContext;

    protected List<ClientProductionRepresentation> leaderCardsProduction;

    public AbstractLeaderCardView(CliClientManager clientManager, int rowSize, int columnSize) {
        super(clientManager, rowSize, columnSize);
        activePlayer = clientManager.getGameContextRepresentation().getActivePlayer();

        leaderCardsPlayerContext = clientManager.getGameContextRepresentation().getPlayerContext(leaderCardsPlayer);
    }

    public AbstractLeaderCardView(CliClientManager clientManager) {
        super(clientManager);
    }
}
