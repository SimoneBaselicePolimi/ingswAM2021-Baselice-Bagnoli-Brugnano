package it.polimi.ingsw.server.model.notifier;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContextImp;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPath;
import it.polimi.ingsw.server.model.gamecontext.market.Market;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTable;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerGameUpdate;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameContextNotifier extends GameContextImp implements Notifier {

    public GameContextNotifier(
        Market market,
        DevelopmentCardsTable developmentCardsTable,
        FaithPath faithPath,
        List<Player> playersOrder,
        Map<Player, PlayerContext> playerContexts
    ) {
        super(market, developmentCardsTable, faithPath, playersOrder, playerContexts);
    }

    @Override
    public Set<ServerGameUpdate> getUpdates() {
        return null;
    }

    @Override
    public Player startNextPlayerTurn (){
        return null;
    }
}
