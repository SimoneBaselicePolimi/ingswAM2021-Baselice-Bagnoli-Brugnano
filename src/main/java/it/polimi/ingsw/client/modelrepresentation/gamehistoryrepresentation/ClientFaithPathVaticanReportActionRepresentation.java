package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.polimi.ingsw.localization.Localization;

public class ClientFaithPathVaticanReportActionRepresentation extends ClientGameActionRepresentation {

    @JsonIgnore
    @Override
    public String getActionMessage() {
        return Localization.getLocalizationInstance().getString(
            "gameHistory.faithPath.vaticanReport"
        );
    }

}
