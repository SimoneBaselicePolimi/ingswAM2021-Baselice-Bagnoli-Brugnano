package it.polimi.ingsw.server.model.observableproxy;

import it.polimi.ingsw.server.model.gamecontext.GameContext;
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
        //TODO SP01
        return singlePlayerGameContextImp.getFaithPathSinglePlayer();
    }

    @Override
    public Stack<SinglePlayerToken> getSinglePlayerTokensDeck() {
        //TODO SP01
        return singlePlayerGameContextImp.getSinglePlayerTokensDeck();
    }

    @Override
    public void initializeAndShuffleSinglePlayerTokens() {
        //TODO SP01
        singlePlayerGameContextImp.initializeAndShuffleSinglePlayerTokens();
    }

    @Override
    public Set<ServerGameUpdate> getUpdates() {
        Set<ServerGameUpdate> updates = super.getUpdates();
        //TODO SP01
        return updates;
    }

}
