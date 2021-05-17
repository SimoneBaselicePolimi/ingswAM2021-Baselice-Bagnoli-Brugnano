package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientLeaderCardStateUpdate;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.ClientGameContextRepresentation;

public class LeaderCardStateUpdateHandler extends GameUpdateHandler<ClientLeaderCardStateUpdate>{

    @Override
    public void handleGameUpdate(ClientLeaderCardStateUpdate update, ClientGameContextRepresentation gameContextRepresentation) {
        update.leaderCard.setState(update.leaderCardState);
    }
}
