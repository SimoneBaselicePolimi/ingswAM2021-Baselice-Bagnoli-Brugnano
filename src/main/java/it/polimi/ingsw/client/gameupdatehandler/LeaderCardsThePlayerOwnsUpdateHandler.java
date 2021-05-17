package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientLeaderCardsThePlayerOwnsUpdate;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.ClientGameContextRepresentation;

public class LeaderCardsThePlayerOwnsUpdateHandler extends GameUpdateHandler<ClientLeaderCardsThePlayerOwnsUpdate>{

    @Override
    public void handleGameUpdate(ClientLeaderCardsThePlayerOwnsUpdate update, ClientGameContextRepresentation gameContextRepresentation) {
        gameContextRepresentation.getPlayerContext(update.player).setLeaderCardsPlayerOwns(update.leaderCardsThePlayerOwns);
    }
}
