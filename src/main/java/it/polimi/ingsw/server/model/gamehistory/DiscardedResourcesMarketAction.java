package it.polimi.ingsw.server.model.gamehistory;

import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerDiscardedResourcesMarketActionRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerGameActionRepresentation;

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
            return Localization.getLocalizationInstance().getString(
                "gameHistory.gameState.manageResourcesFromMarketTurn. discardedResourcesMarketAction.singular",
                player
            );
        else
            return Localization.getLocalizationInstance().getString(
                "gameHistory.gameState.manageResourcesFromMarketTurn. discardedResourcesMarketAction.plural",
                player,
                numberOfResourcesDiscarded
            );
    }

    @Override
    public ServerGameActionRepresentation getServerRepresentation() {
        return new ServerDiscardedResourcesMarketActionRepresentation(
            player,
            numberOfResourcesDiscarded
        );
    }

    @Override
    public ServerGameActionRepresentation getServerRepresentationForPlayer(Player player) {
        return getServerRepresentation();
    }
}
