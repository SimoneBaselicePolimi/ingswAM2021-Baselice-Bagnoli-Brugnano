package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientLeaderCardStateUpdate;
import it.polimi.ingsw.client.model.GameContextRepresentation;
import it.polimi.ingsw.client.model.LeaderCardRepresentation;
import it.polimi.ingsw.client.model.PlayerContextRepresentation;
import it.polimi.ingsw.client.model.PlayerRepresentation;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerLeaderCardCanBeActivatedUpdate;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerMarketUpdate;

import java.util.Set;

public class LeaderCardStateUpdateHandler extends GameUpdateHandler<ClientLeaderCardStateUpdate>{

    @Override
    public void handleGameUpdate(ClientLeaderCardStateUpdate update, GameContextRepresentation gameContextRepresentation) {

        PlayerRepresentation sender = gameContextRepresentation.getActivePlayer();
        Set<LeaderCardRepresentation> leaderCardsPlayerOwns = gameContextRepresentation
            .getPlayerContexts().get(sender).getLeaderCardsPlayerOwns();

        for(LeaderCardRepresentation leaderCard : leaderCardsPlayerOwns){
            if (leaderCard.equals(update.leaderCard))
                leaderCard.setState(update.leaderCardState);
        }
    }
}
