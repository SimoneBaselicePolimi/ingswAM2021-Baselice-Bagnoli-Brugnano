package it.polimi.ingsw.server.model.gamehistory;

import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;

public class FaithPathVaticanReportAction extends GameAction {

    @Override
    public String getActionMessage() {
        return Localization.getLocalization().getString(
            "gameHistory.faithPath.vaticanReport"
        );
    }
}
