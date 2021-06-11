package it.polimi.ingsw.gameactionshistory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsSetOfIds;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.Set;

public class ProductionAction extends GameAction {

    @SerializeIdOnly
    private final Player player;

    @SerializeAsSetOfIds
    public final Set<Production> productions;

    public ProductionAction(
        @JsonProperty("player") Player player,
        @JsonProperty("productions") Set<Production> productions
    ) {
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
