package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;

public class LeaderCardView extends CliView{

    public static int LEADER_CARD_ROW_SIZE = 7;
    public static int LEADER_CARD_COL_SIZE = 12;

    protected ClientLeaderCardRepresentation card;

    public LeaderCardView(CliClientManager clientManager, ClientLeaderCardRepresentation card) {
        super(clientManager, LEADER_CARD_ROW_SIZE, LEADER_CARD_COL_SIZE);
        this.card = card;
    }

}
