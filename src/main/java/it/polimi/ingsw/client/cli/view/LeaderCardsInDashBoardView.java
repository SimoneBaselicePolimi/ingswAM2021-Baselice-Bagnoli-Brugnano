package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.server.model.Player;

public class LeaderCardsInDashBoardView extends CliView{

    protected Player player;

    public LeaderCardsInDashBoardView(CliClientManager clientManager, Player player) {
        super(clientManager);
        this.player = player;
    }
}
