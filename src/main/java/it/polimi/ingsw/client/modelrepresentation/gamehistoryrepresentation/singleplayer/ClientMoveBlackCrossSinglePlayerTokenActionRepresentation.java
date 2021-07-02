package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation.singleplayer;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation.ClientGameActionRepresentation;
import it.polimi.ingsw.localization.Localization;

public class ClientMoveBlackCrossSinglePlayerTokenActionRepresentation extends ClientGameActionRepresentation {

    public final int steps;

    public ClientMoveBlackCrossSinglePlayerTokenActionRepresentation(
        @JsonProperty("steps") int steps
    ) {
        this.steps = steps;
    }

    @Override
    public String getActionMessage() {
        return Localization.getLocalizationInstance().getString(
            "gameHistory.faithPath.singlePlayer.tokenMessage"
        ) + (steps == 1 ?
            Localization.getLocalizationInstance().getString(
                "gameHistory.faithPath.singlePlayer.moveBlackCross.oneStep"
            )
            : Localization.getLocalizationInstance().getString(
            "gameHistory.faithPath.singlePlayer.moveBlackCross.multipleSteps",
            steps
        ));
    }

}
