package it.polimi.ingsw.server.model.gamehistory;

import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;

public class ManageResourcesFromMarketInitialAction extends GameAction{
    private final Player player;

    public ManageResourcesFromMarketInitialAction(Player player) {
        this.player = player;
    }

    @Override
    public String getActionMessage() {
        return Localization.getLocalization().getString(
            "manageResourcesFromMarketTurn.initialMessage",
            player.getName()
        );
    }
}
