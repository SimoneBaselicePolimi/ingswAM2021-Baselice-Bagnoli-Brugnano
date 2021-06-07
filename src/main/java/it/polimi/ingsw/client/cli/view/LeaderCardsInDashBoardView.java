package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.view.grid.GridView;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.server.model.Player;

import java.util.ArrayList;

public class LeaderCardsInDashBoardView extends CliView{

    protected GridView container;
    protected LeaderCardListView cardListView;

    protected Player player;
    protected ClientPlayerContextRepresentation playerContext;

    public LeaderCardsInDashBoardView(CliClientManager clientManager, Player player) {
        super(clientManager);
        this.player = player;
        this.playerContext = clientManager.getGameContextRepresentation().getPlayerContext(player);

        container = new GridView(clientManager, 1, 1, 1);
        addChildView(container, 0,0);

        if(player.equals(clientManager.getMyPlayer())) {
            cardListView = new LeaderCardListView(
                new ArrayList<>(playerContext.getLeaderCardsPlayerOwns()),
                true,
                clientManager
            );
        } else {
            cardListView = new LeaderCardListView(
                new ArrayList<>(playerContext.getActiveLeaderCards()),
                true,
                clientManager
            );
        }

        container.setView(0, 0, cardListView);
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
