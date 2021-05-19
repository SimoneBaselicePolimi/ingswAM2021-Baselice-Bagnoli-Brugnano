package it.polimi.ingsw.server.model.gamehistory;

import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.localization.LocalizationUtils;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerGameActionRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerObtainedResourcesMarketActionRepresentation;

import java.util.Map;

public class ObtainedResourcesMarketAction extends GameAction {
    private final Player player;
    private final Map<ResourceType, Integer> resourcesObtained;

    public ObtainedResourcesMarketAction(Player player, Map<ResourceType, Integer> resourcesObtained) {
        this.player = player;
        this.resourcesObtained = resourcesObtained;
    }

    @Override
    public String getActionMessage() {
        return Localization.getLocalization().getString(
            "gameHistory.gameState.manageResourcesFromMarketTurn.initialMessage",
            player.getName(),
            LocalizationUtils.getResourcesListAsString(resourcesObtained)
        );
    }

    @Override
    public ServerGameActionRepresentation getServerRepresentation() {
        return new ServerObtainedResourcesMarketActionRepresentation(
            player.getServerRepresentation(),
            resourcesObtained
        );
    }

    @Override
    public ServerGameActionRepresentation getServerRepresentationForPlayer(Player player) {
        return getServerRepresentation();
    }
}
