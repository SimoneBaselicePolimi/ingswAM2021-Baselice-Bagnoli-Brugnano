package it.polimi.ingsw.server.model.notifier;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.gamecontext.GameContextBuilder;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPath;
import it.polimi.ingsw.server.model.gamecontext.market.Market;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTable;
import it.polimi.ingsw.server.model.notifier.gameupdate.GameUpdate;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameContextNotifier extends GameContext implements Notifier {

    protected GameContextNotifier(
        Market market,
        DevelopmentCardsTable developmentCardsTable,
        FaithPath faithPath,
        List<Player> playersOrder,
        Map<Player, PlayerContext> playerContexts
    ) {
        super(market, developmentCardsTable, faithPath, playersOrder, playerContexts);
    }

    @Override
    public Set<GameUpdate> getUpdates() {
        return null;
    }

    @Override
    public Player startNextPlayerTurn (){
        return null;
    }
}
