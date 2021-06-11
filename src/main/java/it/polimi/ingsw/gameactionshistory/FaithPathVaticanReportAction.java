package it.polimi.ingsw.gameactionshistory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.polimi.ingsw.localization.Localization;

public class FaithPathVaticanReportAction extends GameAction {

    @JsonIgnore
    @Override
    public String getActionMessage() {
        return Localization.getLocalizationInstance().getString(
            "gameHistory.faithPath.vaticanReport"
        );
    }

}
