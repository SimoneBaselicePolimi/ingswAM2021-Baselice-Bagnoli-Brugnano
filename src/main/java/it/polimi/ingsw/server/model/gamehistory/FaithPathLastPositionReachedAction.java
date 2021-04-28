package it.polimi.ingsw.server.model.gamehistory;

import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;

public class FaithPathLastPositionReachedAction extends GameAction {
    private final Player player;

    public FaithPathLastPositionReachedAction(Player player) {
        this.player = player;
    }

    @Override
    public String getActionMessage() {
        return Localization.getLocalization().getString(
            "gameHistory.faithPath.lastPositionReached",
            player.getName()
        );

    }
}
