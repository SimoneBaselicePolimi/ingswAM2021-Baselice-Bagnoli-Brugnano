package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.server.model.Player;

public class PlayerLeaderCardsInDashboardView extends AbstractPlayerLeaderCardsInDashboardView {

    protected GameView gameView;

    protected Player player;
    protected ClientPlayerContextRepresentation playerContext;

    public PlayerLeaderCardsInDashboardView(CliClientManager clientManager, Player player, GameView gameView) {
        super(player, clientManager, gameView);
        this.player = player;
        this.playerContext = clientManager.getGameContextRepresentation().getPlayerContext(player);
        startLeaderCardsInDashboardDialog();
    }

    void startLeaderCardsInDashboardDialog() {

    }

}
