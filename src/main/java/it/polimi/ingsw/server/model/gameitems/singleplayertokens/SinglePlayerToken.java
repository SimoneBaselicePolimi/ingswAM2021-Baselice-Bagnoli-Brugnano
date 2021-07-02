package it.polimi.ingsw.server.model.gameitems.singleplayertokens;

import it.polimi.ingsw.server.model.Representable;
import it.polimi.ingsw.server.model.gamecontext.SinglePlayerGameContext;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.singleplayertokens.ServerSinglePlayerTokenRepresentation;

public abstract class SinglePlayerToken implements Representable<ServerSinglePlayerTokenRepresentation> {

    public abstract void executeTokenAction(SinglePlayerGameContext singlePlayerGameContext);

}
