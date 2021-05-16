package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientLeaderCardCanBeActivatedUpdate;
import it.polimi.ingsw.client.model.GameContextRepresentation;

public class LeaderCardCanBeActivatedUpdateHandler extends GameUpdateHandler<ClientLeaderCardCanBeActivatedUpdate>{

    @Override
    public void handleGameUpdate(ClientLeaderCardCanBeActivatedUpdate update, GameContextRepresentation gameContextRepresentation) {
        //TODO: posso aggiungere al LeaderCardRepresentation un boolean che setto ogni volta che ricevo questo GameUpdate?
    }

}
