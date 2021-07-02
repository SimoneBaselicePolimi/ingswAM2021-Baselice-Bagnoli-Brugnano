package it.polimi.ingsw.server.model.gameitems.singleplayertokens;

import it.polimi.ingsw.server.gameactionshistory.singleplayer.MoveBlackCrossSinglePlayerTokenAction;
import it.polimi.ingsw.server.model.gamecontext.SinglePlayerGameContext;
import it.polimi.ingsw.server.model.gamehistory.GameHistory;

public class BlackCrossMoveToken extends SinglePlayerToken {

    public final int numOfSteps;

    public BlackCrossMoveToken(int numOfSteps) {
        this.numOfSteps = numOfSteps;
    }

    @Override
    public void executeTokenAction(
        SinglePlayerGameContext singlePlayerGameContext, GameHistory gameHistory
    ) {
        singlePlayerGameContext.getFaithPathSinglePlayer().moveBlackCross(numOfSteps);
        gameHistory.addAction(new MoveBlackCrossSinglePlayerTokenAction(numOfSteps));
        //TODO SP01 end of the game
    }

}
