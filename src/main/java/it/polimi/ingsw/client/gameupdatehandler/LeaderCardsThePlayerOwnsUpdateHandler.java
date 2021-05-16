package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientLeaderCardsThePlayerOwnsUpdate;
import it.polimi.ingsw.client.model.GameContextRepresentation;
import it.polimi.ingsw.client.model.LeaderCardRepresentation;
import it.polimi.ingsw.client.model.PlayerRepresentation;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerLeaderCardsThePlayerOwnsUpdate;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerMarketUpdate;

import java.util.Set;

public class LeaderCardsThePlayerOwnsUpdateHandler extends GameUpdateHandler<ClientLeaderCardsThePlayerOwnsUpdate>{
    @Override
    public void handleGameUpdate(ClientLeaderCardsThePlayerOwnsUpdate update, GameContextRepresentation gameContextRepresentation) {

        PlayerRepresentation sender = gameContextRepresentation.getActivePlayer();
        Set<LeaderCardRepresentation> leaderCardsThePlayerOwns = update.leaderCardsThePlayerOwns;
        gameContextRepresentation.getPlayerContexts().get(sender).setLeaderCardsPlayerOwns(leaderCardsThePlayerOwns);

    }
}
