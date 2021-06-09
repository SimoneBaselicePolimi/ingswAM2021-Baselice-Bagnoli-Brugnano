package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.view.grid.GridView;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.server.model.Player;

import java.util.ArrayList;
import java.util.List;

public class AbstractLeaderCardView extends CliView{

    protected Player leaderCardsPlayer, activePlayer;
    protected ClientPlayerContextRepresentation leaderCardsPlayerContext;

    protected GameView gameView;
    protected GridView container;
    protected LeaderCardListView cardListView;

    protected List<ClientLeaderCardRepresentation> leaderCardList;

    public AbstractLeaderCardView(
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

        container = new GridView(clientManager, 1, 1, 1);
        addChildView(container, 0,0);

        if(leaderCardsPlayer.equals(clientManager.getMyPlayer())) {
            leaderCardList = new ArrayList<>(leaderCardsPlayerContext.getLeaderCardsPlayerOwns());
        } else {
            leaderCardList = new ArrayList<>(leaderCardsPlayerContext.getActiveLeaderCards());
        }

        cardListView = new LeaderCardListView(
            leaderCardList,
            true,
            clientManager
        );

        container.setView(0, 0, cardListView);
    }

    public AbstractLeaderCardView(
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
