package it.polimi.ingsw.server.model.gamehistory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.Production;

import java.util.Set;

public class ProductionAction extends GameAction {
    private final Player player;
    public final Set<Production> productions;

    public ProductionAction(Player player, Set<Production> productions) {
        this.player = player;
        this.productions = productions;
    }

    @JsonIgnore
    @Override
    public String getActionMessage() {
        return Localization.getLocalizationInstance().getString(
            "gameState.mainTurn.developmentAction",
            player,
            productions
        );
    }

}
