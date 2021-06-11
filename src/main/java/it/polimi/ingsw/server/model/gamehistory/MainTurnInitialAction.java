package it.polimi.ingsw.server.model.gamehistory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;

public class MainTurnInitialAction extends GameAction{
    private final Player player;

    public MainTurnInitialAction(Player player) {
        this.player = player;
    }

    @JsonIgnore
    @Override
    public String getActionMessage() {
        return Localization.getLocalizationInstance().getString(
            "gameHistory.gameState.mainTurn.initialMessage",
            player.getName()
        );
    }

}
