package it.polimi.ingsw.server.model.gamehistory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.MarbleColour;

public class ObtainedMarblesMarketAction extends GameAction {
    private final Player player;
    private final MarbleColour [] marbleColours;

    public ObtainedMarblesMarketAction(
        @JsonProperty("player") Player player,
        @JsonProperty("marbleColours") MarbleColour[] marbleColours
    ) {
        this.player = player;
        this.marbleColours = marbleColours;
    }

    @JsonIgnore
    @Override
    public String getActionMessage() {
        return Localization.getLocalizationInstance().getString(
            "gameState.mainTurn.obtainedMarblesMarketAction",
            player,
            marbleColours
        );
    }

}
