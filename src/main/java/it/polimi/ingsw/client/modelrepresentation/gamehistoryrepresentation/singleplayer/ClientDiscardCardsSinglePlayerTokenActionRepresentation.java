package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation.singleplayer;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation.ClientGameActionRepresentation;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.localization.LocalizationUtils;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;

import java.util.Map;

public class ClientDiscardCardsSinglePlayerTokenActionRepresentation extends ClientGameActionRepresentation {

    public final DevelopmentCardColour cardColour;
    public final Map<DevelopmentCardLevel, Long> cardDiscardedByLevel;

    public ClientDiscardCardsSinglePlayerTokenActionRepresentation(
        @JsonProperty("cardColour") DevelopmentCardColour cardColour,
        @JsonProperty("cardDiscardedByLevel") Map<DevelopmentCardLevel, Long> cardDiscardedByLevel
    ) {
        this.cardColour = cardColour;
        this.cardDiscardedByLevel = cardDiscardedByLevel;
    }

    @Override
    public String getActionMessage() {
        return Localization.getLocalizationInstance().getString(
            "gameHistory.faithPath.singlePlayer.tokenMessage"
        ) + Localization.getLocalizationInstance().getString(
            "gameHistory.faithPath.singlePlayer.discardCards.action",
            LocalizationUtils.getNumberAndLevelOfDevCardsAsCompactString(cardColour, cardDiscardedByLevel)
        );
    }

}
