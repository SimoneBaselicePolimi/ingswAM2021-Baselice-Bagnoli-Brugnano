package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientLeaderCardCanBeActivatedUpdate;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.ClientGameContextRepresentation;

public class LeaderCardCanBeActivatedUpdateHandler extends GameUpdateHandler<ClientLeaderCardCanBeActivatedUpdate>{

    @Override
    public void handleGameUpdate(ClientLeaderCardCanBeActivatedUpdate update, ClientGameContextRepresentation gameContextRepresentation) {
        update.leaderCard.setCanBeActivated(update.canBeActivated);
    }

}
