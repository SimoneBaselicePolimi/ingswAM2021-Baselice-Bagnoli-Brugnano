package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.server.model.Player;

//TODO
public class ResourcePositioningInDashboardView extends AbstractPlayerDashboardView{

    public ResourcePositioningInDashboardView(Player dashboardPlayer, CliClientManager clientManager, GameView gameView, int rowSize, int columnSize) {
        super(dashboardPlayer, clientManager, gameView, rowSize, columnSize);
    }

    public ResourcePositioningInDashboardView(Player dashboardPlayer, CliClientManager clientManager, GameView gameView) {
        super(dashboardPlayer, clientManager, gameView);
    }
}
