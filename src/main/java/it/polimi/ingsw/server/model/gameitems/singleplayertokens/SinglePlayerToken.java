package it.polimi.ingsw.server.model.gameitems.singleplayertokens;

import it.polimi.ingsw.server.model.gamecontext.SinglePlayerGameContext;
import it.polimi.ingsw.server.model.gamehistory.GameHistory;

public abstract class SinglePlayerToken { //implements Representable<ServerSinglePlayerTokenRepresentation> {

    public abstract void executeTokenAction(SinglePlayerGameContext singlePlayerGameContext, GameHistory gameHistory);

}
