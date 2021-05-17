package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientPopeCardsUpdate;
import it.polimi.ingsw.client.model.GameContextRepresentation;

    public class PopeCardsUpdateHandler extends GameUpdateHandler<ClientPopeCardsUpdate>{
    @Override
    public void handleGameUpdate(ClientPopeCardsUpdate update, GameContextRepresentation gameContextRepresentation) {
        gameContextRepresentation.getFaithPath().setPopeFavorCards(update.faithPopeCardsStates);
    }
}
