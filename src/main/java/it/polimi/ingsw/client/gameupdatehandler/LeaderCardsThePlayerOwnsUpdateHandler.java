package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientLeaderCardsThePlayerOwnsUpdate;
import it.polimi.ingsw.client.model.GameContextRepresentation;

public class LeaderCardsThePlayerOwnsUpdateHandler extends GameUpdateHandler<ClientLeaderCardsThePlayerOwnsUpdate>{

    @Override
    public void handleGameUpdate(ClientLeaderCardsThePlayerOwnsUpdate update, GameContextRepresentation gameContextRepresentation) {
        gameContextRepresentation.getPlayerContext(update.player).setLeaderCardsPlayerOwns(update.leaderCardsThePlayerOwns);
    }
}
