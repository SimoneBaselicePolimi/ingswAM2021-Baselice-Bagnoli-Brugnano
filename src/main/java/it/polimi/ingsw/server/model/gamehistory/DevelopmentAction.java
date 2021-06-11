package it.polimi.ingsw.server.model.gamehistory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;

public class DevelopmentAction extends GameAction {
    private final Player player;
    private final DevelopmentCard developmentCard;

    public DevelopmentAction(
        @JsonProperty("player") Player player,
        @JsonProperty("developmentCard") DevelopmentCard developmentCard
    ) {
        this.player = player;
        this.developmentCard = developmentCard;
    }

    @JsonIgnore
    @Override
    public String getActionMessage() {
        return Localization.getLocalizationInstance().getString(
            "gameState.mainTurn.developmentAction",
            player,
            developmentCard
        );
    }

}
