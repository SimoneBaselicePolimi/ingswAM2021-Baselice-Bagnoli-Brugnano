package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientLeaderCardStateUpdate;
import it.polimi.ingsw.client.model.GameContextRepresentation;

public class LeaderCardStateUpdateHandler extends GameUpdateHandler<ClientLeaderCardStateUpdate>{

    @Override
    public void handleGameUpdate(ClientLeaderCardStateUpdate update, GameContextRepresentation gameContextRepresentation) {
        update.leaderCard.setState(update.leaderCardState);
    }
}
