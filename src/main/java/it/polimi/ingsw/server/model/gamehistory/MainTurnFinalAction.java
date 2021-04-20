package it.polimi.ingsw.server.model.gamehistory;

import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;

public class MainTurnFinalAction extends GameAction{
    private final Player player;

    public MainTurnFinalAction(Player player) {
        this.player = player;
    }

    @Override
    public String getActionMessage() {
        return Localization.getLocalization().getString(
            "mainTurn.finalMessage",
            player.getName()
        );
    }
}
