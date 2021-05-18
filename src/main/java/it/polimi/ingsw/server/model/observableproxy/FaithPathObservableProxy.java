package it.polimi.ingsw.server.model.observableproxy;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPath;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPathEvent;
import it.polimi.ingsw.server.model.gamecontext.faith.PopeFavorCardState;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerFaithUpdate;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerGameUpdate;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerPopeCardsUpdate;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.faithrepresentation.ServerFaithPathRepresentation;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FaithPathObservableProxy extends ObservableProxy<FaithPath> implements FaithPath {

    protected boolean hasThePlayerMoved = false;
    protected FaithPathEvent faithPathEvent;
    protected Player player;

    public FaithPathObservableProxy(FaithPath imp, GameManager gameManager) {
        super(imp, gameManager);
    }

    @Override
    public int getPlayerFaithPosition(Player player) {
        return imp.getPlayerFaithPosition(player);
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

    @Override
    public Set<ServerGameUpdate> getUpdates() {
        Set<ServerGameUpdate> updates = new HashSet<>();
        if(hasThePlayerMoved) {
            hasThePlayerMoved = false;
            updates.add(new ServerFaithUpdate(player, imp.getPlayerFaithPosition(player)));
            if (faithPathEvent.hasVaticanMeetingHappened())
                updates.add(new ServerPopeCardsUpdate(imp.getPopeFavorCardsState()));
        }
        return updates;
    }

    @Override
    public ServerFaithPathRepresentation getServerRepresentation() {
        return imp.getServerRepresentation();
    }

    @Override
    public ServerFaithPathRepresentation getServerRepresentationForPlayer(Player player) {
        return imp.getServerRepresentationForPlayer(player);
    }
}
