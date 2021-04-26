package it.polimi.ingsw.server.model.notifier;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPath;
import it.polimi.ingsw.server.model.gamecontext.market.Market;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gamehistory.GameAction;
import it.polimi.ingsw.server.model.gamehistory.GameHistory;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTable;
import it.polimi.ingsw.server.model.notifier.gameupdate.GameUpdate;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameHistoryNotifier extends GameHistory implements Notifier {

    @Override
    public Set<GameUpdate> getUpdates() {
        return null;
    }

    @Override
    public void addAction (GameAction gameAction){
    }
}
