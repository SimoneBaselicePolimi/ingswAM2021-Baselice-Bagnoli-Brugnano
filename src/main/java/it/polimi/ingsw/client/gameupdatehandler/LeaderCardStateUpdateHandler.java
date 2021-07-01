package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.client.gameupdate.ClientLeaderCardStateUpdate;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LeaderCardStateUpdateHandler extends GameUpdateHandler<ClientLeaderCardStateUpdate>{

    @Override
    public void handleGameUpdate(ClientLeaderCardStateUpdate update, ClientManager clientManager) {
        update.leaderCard.setState(update.leaderCardState);
        if(!update.player.equals(clientManager.getMyPlayer())) {
            Set<ClientLeaderCardRepresentation> leaderCardsThePlayerOwnedBeforeUpdate =
                clientManager.getGameContextRepresentation().getPlayerContext(update.player).getLeaderCardsPlayerOwns();
            if(update.leaderCardState.equals(LeaderCardState.ACTIVE)) {
                clientManager.getGameContextRepresentation().getPlayerContext(update.player).setLeaderCardsPlayerOwns(
                    Stream.concat(leaderCardsThePlayerOwnedBeforeUpdate.stream(), Stream.of(update.leaderCard))
                        .collect(Collectors.toSet())
                );
            } else if(update.leaderCardState.equals(LeaderCardState.DISCARDED)) {
                clientManager.getGameContextRepresentation().getPlayerContext(update.player).setNumberOfLeaderCardsGivenToThePlayer(
                    clientManager.getGameContextRepresentation().getPlayerContext(update.player).getNumberOfLeaderCardsGivenToThePlayer() - 1
                );
            }
        }
    }

}
