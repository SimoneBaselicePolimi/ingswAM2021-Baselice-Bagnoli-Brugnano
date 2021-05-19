package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientPopeCardsUpdate;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.ClientGameContextRepresentation;

    public class PopeCardsUpdateHandler extends GameUpdateHandler<ClientPopeCardsUpdate>{
    @Override
    public void handleGameUpdate(ClientPopeCardsUpdate update, ClientGameContextRepresentation gameContextRepresentation) {
        gameContextRepresentation.getFaithPath().setPopeFavorCards(update.faithPopeCardsStates);
    }
}
