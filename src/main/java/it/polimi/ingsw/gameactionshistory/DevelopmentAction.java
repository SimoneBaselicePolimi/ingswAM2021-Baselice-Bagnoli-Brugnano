package it.polimi.ingsw.gameactionshistory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class DevelopmentAction extends GameAction {

    @SerializeIdOnly
    private final Player player;

    @SerializeIdOnly
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
