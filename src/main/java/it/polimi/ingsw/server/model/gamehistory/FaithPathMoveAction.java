package it.polimi.ingsw.server.model.gamehistory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;

public class FaithPathMoveAction extends GameAction {
    private final Player player;
    private final int steps;

    public FaithPathMoveAction(Player player, int steps) {
        this.player = player;
        this.steps = steps;
    }

    @JsonIgnore
    @Override
    public String getActionMessage() {
        if (steps == 1)
            return Localization.getLocalizationInstance().getString(
                "gameHistory.faithPath.playerMove.singular",
                player.getName()
            );
        else
            return Localization.getLocalizationInstance().getString(
                "gameHistory.faithPath.playerMove.plural",
                player.getName(),
                steps
            );
    }

}
