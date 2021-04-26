package it.polimi.ingsw.server.model.gamehistory;
import it.polimi.ingsw.localization.Localization;

public class SetupFinalAction extends GameAction{

    @Override
    public String getActionMessage() {
        return Localization.getLocalization().getString("setup.finalMessage");
    }
}
