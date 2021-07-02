package it.polimi.ingsw.server.model.gamecontext;

import it.polimi.ingsw.server.model.gamecontext.faith.FaithPathSinglePlayer;
import it.polimi.ingsw.server.model.gameitems.singleplayertokens.SinglePlayerToken;

import java.util.Stack;

public interface SinglePlayerGameContext extends GameContext {

    FaithPathSinglePlayer getFaithPathSinglePlayer();

    Stack<SinglePlayerToken> getSinglePlayerTokensDeck();

    void resetAndShuffleSinglePlayerTokens();

}
