package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.server.controller.servermessage.GameUpdateServerMessage;
import it.polimi.ingsw.server.controller.servermessage.InitialChoicesServerMessage;
import it.polimi.ingsw.server.controller.servermessage.PostGameSetupServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.SinglePlayerGameContext;
import it.polimi.ingsw.server.model.gamehistory.GameHistory;
import it.polimi.ingsw.server.model.gameitems.singleplayertokens.SinglePlayerToken;
import it.polimi.ingsw.server.model.gamemanager.GameManager;

import java.util.Map;

public class SinglePlayerTokenActionState extends GameState<GameUpdateServerMessage, GameUpdateServerMessage>{

    SinglePlayerGameContext singlePlayerGameContext;
    GameHistory gameHistory;

    /**
     * GameState constructor
     *
     * @param gameManager GameManager, see {@link GameManager}
     */
    protected SinglePlayerTokenActionState(GameManager gameManager) {
        super(gameManager);
        singlePlayerGameContext = gameManager.getSinglePlayerGameContext().get();
        gameHistory = gameManager.getGameHistory();
    }

    /**
     * Method that verifies that the current state is closed
     */
    @Override
    public boolean isStateDone() {
        return true;
    }

    /**
     * Method that changes the state of the game
     */
    @Override
    public GameState getNextState() {
        return new GameTurnMainActionState(gameManager);
    }


    /**
     * The initial messages that should be sent to the clients when this GameState becomes the currentGameState
     *
     * @return the default implementation returns an empty map (no message will be sent)
     */
    @Override
    public Map<Player, GameUpdateServerMessage> getInitialServerMessage() {
        SinglePlayerToken token = singlePlayerGameContext.getSinglePlayerTokensDeck().pop();
        token.executeTokenAction(singlePlayerGameContext, gameHistory);
        return buildGameUpdateServerMessage();
    }

}
