package it.polimi.ingsw.server.model.gamehistory;

import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.localization.LocalizationUtils;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerGameActionRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerSetupChoiceActionRepresentation;

import java.util.Map;

public class SetupChoiceAction extends GameAction{
    private final Player player;
    private final Map<ResourceType,Integer> initialResources;

    public SetupChoiceAction(Player player, Map<ResourceType, Integer> initialResources) {
        this.player = player;
        this.initialResources = initialResources;
    }

    @Override
    public String getActionMessage() {
        return Localization.getLocalizationInstance().getString(
            "gameHistory.gameState.setup.initialMessage",
            player.getName(),
            LocalizationUtils.getResourcesListAsString(initialResources)
        );
    }

    @Override
    public ServerGameActionRepresentation getServerRepresentation() {
        return new ServerSetupChoiceActionRepresentation(
            player.getServerRepresentation(),
            initialResources
        );
    }

    @Override
    public ServerGameActionRepresentation getServerRepresentationForPlayer(Player player) {
        return getServerRepresentation();
    }
}
