package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientPopeCardsUpdate;
import it.polimi.ingsw.client.model.GameContextRepresentation;
import it.polimi.ingsw.client.model.PlayerRepresentation;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.PopeFavorCardState;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerMarketUpdate;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerPopeCardsUpdate;

import java.util.List;
import java.util.Map;

    public class PopeCardsUpdateHandler extends GameUpdateHandler<ClientPopeCardsUpdate>{
    @Override
    public void handleGameUpdate(ClientPopeCardsUpdate update, GameContextRepresentation gameContextRepresentation) {

        gameContextRepresentation.getFaithPath().setPopeFavorCards(update.faithPopeCards);
    }
}
