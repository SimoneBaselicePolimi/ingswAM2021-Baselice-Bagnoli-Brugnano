package it.polimi.ingsw.server.model.gamehistory;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.MarbleColour;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerGameActionRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerObtainedMarblesMarketActionRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerMarbleColourRepresentation;

import java.util.Arrays;

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

    @Override
    public String getActionMessage() {
        return Localization.getLocalizationInstance().getString(
            "gameState.mainTurn.obtainedMarblesMarketAction",
            player,
            marbleColours
        );
    }

    @Override
    public ServerGameActionRepresentation getServerRepresentation() {
        return new ServerObtainedMarblesMarketActionRepresentation(
            player,
            Arrays.stream(marbleColours)
                .map(MarbleColour::getServerRepresentation)
                .toArray(ServerMarbleColourRepresentation[]::new)
        );
    }

    @Override
    public ServerGameActionRepresentation getServerRepresentationForPlayer(Player player) {
        return getServerRepresentation();
    }
}
