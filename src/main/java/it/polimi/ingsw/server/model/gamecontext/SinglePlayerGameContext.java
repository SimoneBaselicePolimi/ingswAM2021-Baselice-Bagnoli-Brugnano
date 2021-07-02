package it.polimi.ingsw.server.model.gamecontext;

import it.polimi.ingsw.server.model.gamecontext.faith.FaithPathSinglePlayer;
import it.polimi.ingsw.server.model.gameitems.cardstack.ShuffledCoveredCardDeck;
import it.polimi.ingsw.server.model.gameitems.singleplayertokens.SinglePlayerToken;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.singleplayertokens.ServerSinglePlayerTokenRepresentation;

import java.util.List;
import java.util.Queue;
import java.util.Stack;

public interface SinglePlayerGameContext extends GameContext {

    FaithPathSinglePlayer getFaithPathSinglePlayer();

    Stack<SinglePlayerToken> getSinglePlayerTokensDeck();

    void resetAndShuffleSinglePlayerTokens();

}
