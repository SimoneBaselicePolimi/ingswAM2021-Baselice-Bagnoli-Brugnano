package it.polimi.ingsw.server.model.gameitems.singleplayertokens;

import it.polimi.ingsw.server.gameactionshistory.singleplayer.MoveBlackCrossAndShuffleSinglePlayerTokenAction;
import it.polimi.ingsw.server.model.gamecontext.SinglePlayerGameContext;
import it.polimi.ingsw.server.model.gamehistory.GameHistory;

public class BlackCrossMoveAndTokensShuffleToken extends SinglePlayerToken {

    public final int numOfSteps;

    public BlackCrossMoveAndTokensShuffleToken(int numOfSteps) {
        this.numOfSteps = numOfSteps;
    }


    @Override
    public void executeTokenAction(
        SinglePlayerGameContext singlePlayerGameContext, GameHistory gameHistory
    ) {
        singlePlayerGameContext.getFaithPathSinglePlayer().moveBlackCross(numOfSteps);
        singlePlayerGameContext.resetAndShuffleSinglePlayerTokens();
        gameHistory.addAction(new MoveBlackCrossAndShuffleSinglePlayerTokenAction(numOfSteps));
        //TODO SP01 end of the game
    }

}
