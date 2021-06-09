package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.UserChoicesUtils;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.server.model.Player;

public class LeaderCardsInDashBoardView extends AbstractLeaderCardView{

    protected GameView gameView;

    protected Player player;
    protected ClientPlayerContextRepresentation playerContext;

    public LeaderCardsInDashBoardView(CliClientManager clientManager, Player player, GameView gameView) {
        super(player, clientManager, gameView);
        this.player = player;
        this.playerContext = clientManager.getGameContextRepresentation().getPlayerContext(player);

        startLeaderCardsInDashboardDialog();
    }

    void startLeaderCardsInDashboardDialog() {

    }

}
