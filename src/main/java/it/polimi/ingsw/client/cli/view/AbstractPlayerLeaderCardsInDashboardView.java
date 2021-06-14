package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.view.grid.GridView;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.server.model.Player;

import java.util.ArrayList;
import java.util.List;

public class AbstractPlayerLeaderCardsInDashboardView extends CliView{

    protected Player leaderCardsPlayer, activePlayer;
    protected ClientPlayerContextRepresentation leaderCardsPlayerContext;

    protected GameView gameView;
    protected GridView container;
    protected LeaderCardListView cardListView;
    protected LabelView descriptionView;

    protected List<ClientLeaderCardRepresentation> leaderCardList;

    public AbstractPlayerLeaderCardsInDashboardView(
        Player leaderCardsPlayer,
        CliClientManager clientManager,
        GameView gameView,
        int rowSize,
        int columnSize
    ) {
        super(clientManager, rowSize, columnSize);
        this.leaderCardsPlayer = leaderCardsPlayer;
        this.gameView = gameView;

        activePlayer = clientManager.getGameContextRepresentation().getActivePlayer();
        leaderCardsPlayerContext = clientManager.getGameContextRepresentation().getPlayerContext(leaderCardsPlayer);

        container = new GridView(clientManager, 2, 1, 1);
        addChildView(container, 0,0);

        cardListView = new LeaderCardListView(
            new ArrayList<>(leaderCardsPlayerContext.getLeaderCardsPlayerOwns()),
            true,
            clientManager
        );

        container.setView(0, 0, cardListView);

        descriptionView = new LabelView(new ArrayList<>(), clientManager);
        container.setView(1, 0, descriptionView);
    }

    public AbstractPlayerLeaderCardsInDashboardView(
        Player leaderCardsPlayer,
        CliClientManager clientManager,
        GameView gameView
    ) {
        this(leaderCardsPlayer, clientManager, gameView, 0, 0);
    }

    @Override
    public void setRowSize(int rowSize) {
        container.setRowSize(rowSize);
        super.setRowSize(rowSize);
    }

    @Override
    public void setColumnSize(int columnSize) {
        container.setColumnSize(columnSize);
        super.setColumnSize(columnSize);
    }

}
