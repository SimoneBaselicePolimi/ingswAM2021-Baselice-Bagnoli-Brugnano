package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.localization.Localization;

import java.util.List;

public class ClientSetupStartedActionRepresentation extends ClientGameActionRepresentation {

    @JsonIgnore
    @Override
    public List<FormattedChar> getActionMessage() {
        return FormattedChar.convertStringToFormattedCharList(
            Localization.getLocalizationInstance().getString("gameHistory.gameState.setup.initialMessage")
        );
    }

}
