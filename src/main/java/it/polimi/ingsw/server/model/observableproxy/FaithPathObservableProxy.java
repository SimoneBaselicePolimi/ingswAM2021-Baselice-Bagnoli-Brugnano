package it.polimi.ingsw.server.model.observableproxy;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPath;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPathEvent;
import it.polimi.ingsw.server.model.gamecontext.faith.PopeFavorCardState;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.gameupdate.ServerFaithUpdate;
import it.polimi.ingsw.server.model.gameupdate.ServerGameUpdate;
import it.polimi.ingsw.server.model.gameupdate.ServerPopeCardsUpdate;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.faithrepresentation.ServerFaithPathRepresentation;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FaithPathObservableProxy extends ObservableProxy<FaithPath> implements FaithPath {

    protected boolean hasVaticanMeetingHappened;
    protected Set<Player> playersMoved = new HashSet<>();

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
        if(steps > 0) {
            playersMoved.add(player);
        }
        FaithPathEvent faithPathEvent = imp.move(player, steps);
        if (faithPathEvent.hasVaticanMeetingHappened())
            hasVaticanMeetingHappened = true;
        return faithPathEvent;
    }

    @Override
    public Set<ServerGameUpdate> getUpdates() {
        Set<ServerGameUpdate> updates = new HashSet<>();
        playersMoved.forEach(p -> updates.add(new ServerFaithUpdate(p, imp.getPlayerFaithPosition(p))));
        playersMoved.clear();
        if (hasVaticanMeetingHappened) {
            updates.add(new ServerPopeCardsUpdate(imp.getPopeFavorCardsState()));
            hasVaticanMeetingHappened = false;
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
