package it.polimi.ingsw.server.model.gamehistory;

import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;

public class DiscardedResourcesMarketAction extends GameAction {
    private final Player player;
    private final int numberOfResourcesDiscarded;

    public DiscardedResourcesMarketAction(
        Player player,
        int numberOfResourcesDiscarded
    ) {
        this.player = player;
        this.numberOfResourcesDiscarded = numberOfResourcesDiscarded;
    }

    @Override
    public String getActionMessage() {
        if (numberOfResourcesDiscarded == 1)
            return Localization.getLocalization().getString(
                "gameHistory.gameState.manageResourcesFromMarketTurn. discardedResourcesMarketAction.singular",
                player
            );
        else
            return Localization.getLocalization().getString(
                "gameHistory.gameState.manageResourcesFromMarketTurn. discardedResourcesMarketAction.plural",
                player,
                numberOfResourcesDiscarded
            );
    }
}
