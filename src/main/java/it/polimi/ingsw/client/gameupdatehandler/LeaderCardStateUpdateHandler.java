package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.model.GameContextRepresentation;
import it.polimi.ingsw.client.model.LeaderCardRepresentation;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerMarketUpdate;

public class LeaderCardStateUpdateHandler extends GameUpdateHandler<ServerMarketUpdate>{
    @Override
    public void handleGameUpdate(ServerMarketUpdate update, GameContextRepresentation gameContextRepresentation) {
        LeaderCardRepresentation leaderCard = gameContextRepresentation.getPlayerContexts().get
    }
}
