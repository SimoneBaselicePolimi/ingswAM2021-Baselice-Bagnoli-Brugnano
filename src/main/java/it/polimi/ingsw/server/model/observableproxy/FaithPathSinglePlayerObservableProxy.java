package it.polimi.ingsw.server.model.observableproxy;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.*;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.gameupdate.ServerBlackCrossFaithUpdate;
import it.polimi.ingsw.server.model.gameupdate.ServerGameUpdate;
import it.polimi.ingsw.server.model.gameupdate.ServerPopeCardsUpdate;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.faithrepresentation.ServerFaithPathSinglePlayerRepresentation;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FaithPathSinglePlayerObservableProxy extends ObservableProxy<FaithPathSinglePlayer> implements FaithPathSinglePlayer {

    protected boolean hasThePlayerMoved = false;
    protected boolean hasBlackCrossMoved = false;
    protected FaithPathEvent faithPathEvent;
    protected Player player;

    public FaithPathSinglePlayerObservableProxy(FaithPathSinglePlayerImp imp, GameManager gameManager) {
        super(imp, gameManager);
    }

    @Override
    public int getBlackCrossFaithPosition() {
        return imp.getBlackCrossFaithPosition();
    }

    @Override
    public FaithPathEvent moveBlackCross(int steps) {
        hasBlackCrossMoved = true;
        faithPathEvent = imp.moveBlackCross(steps);
        return faithPathEvent;

    }

    @Override
    public ServerFaithPathSinglePlayerRepresentation getServerRepresentation() {
        return (ServerFaithPathSinglePlayerRepresentation) imp.getServerRepresentation();
    }

    @Override
    public ServerFaithPathSinglePlayerRepresentation getServerRepresentationForPlayer(Player player) {
        return (ServerFaithPathSinglePlayerRepresentation) imp.getServerRepresentationForPlayer(player);
    }

    @Override
    public Set<ServerGameUpdate> getUpdates() {
        Set<ServerGameUpdate> updates = new HashSet<>();
        if(hasBlackCrossMoved) {
            hasBlackCrossMoved = false;
            updates.add(new ServerBlackCrossFaithUpdate(imp.getBlackCrossFaithPosition()));
            if (faithPathEvent.hasVaticanMeetingHappened())
                updates.add(new ServerPopeCardsUpdate(imp.getPopeFavorCardsState()));
        }
        return updates;
    }

    @Override
    public int getPlayerFaithPosition(Player player) {
        return imp.getBlackCrossFaithPosition();
    }

    @Override
    public Map<Player, Integer> getFaithPosition() {
        return imp.getFaithPosition();
    }

    @Override
    public List<PopeFavorCardState> getPlayerPopeFavorCardsState(Player player) {
        return imp.getPlayerPopeFavorCardsState(player);
    }

    @Override
    public Map<Player, List<PopeFavorCardState>> getPopeFavorCardsState() {
        return imp.getPopeFavorCardsState();
    }

    @Override
    public int getPlayerVictoryPoints(Player player) {
        return imp.getPlayerVictoryPoints(player);
    }

    @Override
    public Map<Player, Integer> getVictoryPoints() {
        return imp.getVictoryPoints();
    }

    @Override
    public boolean lastPositionHasBeenReached() {
        return imp.lastPositionHasBeenReached();
    }

    @Override
    public FaithPathEvent move(Player player, int steps) {
        hasThePlayerMoved = true;
        faithPathEvent = imp.move(player, steps);
        this.player = player;
        return faithPathEvent;
    }

}
