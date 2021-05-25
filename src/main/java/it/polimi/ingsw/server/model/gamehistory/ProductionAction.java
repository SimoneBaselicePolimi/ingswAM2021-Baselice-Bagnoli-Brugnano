package it.polimi.ingsw.server.model.gamehistory;

import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerGameActionRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerProductionActionRepresentation;

import java.util.Set;
import java.util.stream.Collectors;

public class ProductionAction extends GameAction {
    private final Player player;
    public final Set<Production> productions;

    public ProductionAction(Player player, Set<Production> productions) {
        this.player = player;
        this.productions = productions;
    }

    @Override
    public String getActionMessage() {
        return Localization.getLocalizationInstance().getString(
            "gameState.mainTurn.developmentAction",
            player,
            productions
        );
    }

    @Override
    public ServerGameActionRepresentation getServerRepresentation() {
        return new ServerProductionActionRepresentation(
            player,
            productions.stream().map(Production::getServerRepresentation).collect(Collectors.toSet())
        );
    }

    @Override
    public ServerGameActionRepresentation getServerRepresentationForPlayer(Player player) {
        return getServerRepresentation();
    }
}
