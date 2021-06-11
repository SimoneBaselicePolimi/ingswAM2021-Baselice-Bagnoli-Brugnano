package it.polimi.ingsw.gameactionshistory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.polimi.ingsw.localization.Localization;

public class SetupStartedAction extends GameAction{

    @JsonIgnore
    @Override
    public String getActionMessage() {
        return Localization.getLocalizationInstance().getString("gameHistory.gameState.setup.initialMessage");
    }

}
