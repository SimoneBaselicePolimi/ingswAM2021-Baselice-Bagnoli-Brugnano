package it.polimi.ingsw.server.model.observableproxy;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPath;
import it.polimi.ingsw.server.model.gamecontext.market.Market;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gamehistory.GameHistory;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTable;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerActivePlayerUpdate;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerGameUpdate;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerMarketUpdate;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.ServerGameContextRepresentation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameContextObservableProxy extends ObservableProxy<GameContext> implements GameContext{

    protected boolean startNextPlayerTurn = false;

    public GameContextObservableProxy(GameContext imp, GameManager gameManager) {
        super(imp, gameManager);
    }

    @Override
    public ServerGameContextRepresentation getServerRepresentation() {
        return null;
    }

    @Override
    public ServerGameContextRepresentation getServerRepresentationForPlayer(Player player) {
        return null;
    }

    @Override
    public List<Player> getPlayersTurnOrder() {
        return imp.getPlayersTurnOrder();
    }

    @Override
    public Set<DevelopmentCard> getDevelopmentCardsPlayerCanBuy(Player player) {
        return imp.getDevelopmentCardsPlayerCanBuy(player);
    }

    @Override
    public PlayerContext getPlayerContext(Player player) {
        return imp.getPlayerContext(player);
    }

    @Override
    public Market getMarket() {
        return imp.getMarket();
    }

    @Override
    public DevelopmentCardsTable getDevelopmentCardsTable() {
        return imp.getDevelopmentCardsTable();
    }

    @Override
    public FaithPath getFaithPath() {
        return imp.getFaithPath();
    }

    @Override
    public Player getActivePlayer() {
        return imp.getActivePlayer();
    }

    @Override
    public Player startNextPlayerTurn() {
        startNextPlayerTurn = true;
        return imp.startNextPlayerTurn();
    }

    @Override
    public Set<ServerGameUpdate> getUpdates() {
        if (startNextPlayerTurn) {
            startNextPlayerTurn = false;
            return Set.of(new ServerActivePlayerUpdate(getActivePlayer()));
        }
        else
            return new HashSet<>();
    }
}
