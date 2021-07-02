package it.polimi.ingsw.server.model.observableproxy;

import it.polimi.ingsw.server.model.gamecontext.SinglePlayerGameContext;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPathSinglePlayer;
import it.polimi.ingsw.server.model.gameitems.singleplayertokens.SinglePlayerToken;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.gameupdate.ServerGameUpdate;

import java.util.Set;
import java.util.Stack;

public class SinglePlayerGameContextObservableProxy extends GameContextObservableProxy
    implements SinglePlayerGameContext {

    SinglePlayerGameContext singlePlayerGameContextImp;

    public SinglePlayerGameContextObservableProxy(
        SinglePlayerGameContext imp,
        GameManager gameManager
    ) {
        super(imp, gameManager);
        this.singlePlayerGameContextImp = imp;
    }

    @Override
    public FaithPathSinglePlayer getFaithPathSinglePlayer() {
        return singlePlayerGameContextImp.getFaithPathSinglePlayer();
    }

    @Override
    public Stack<SinglePlayerToken> getSinglePlayerTokensDeck() {
        return singlePlayerGameContextImp.getSinglePlayerTokensDeck();
    }

    @Override
    public void resetAndShuffleSinglePlayerTokens() {
        singlePlayerGameContextImp.resetAndShuffleSinglePlayerTokens();
    }

    @Override
    public Set<ServerGameUpdate> getUpdates() {
        Set<ServerGameUpdate> updates = super.getUpdates();
        return updates;
    }

}
