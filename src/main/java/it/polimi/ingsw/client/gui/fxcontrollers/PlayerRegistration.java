package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.localization.LocalizationUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PlayerRegistration {

    @FXML
    Label testLabel;

    public PlayerRegistration() {
    }

    @FXML
    private void initialize() {
        this.testLabel.setText(Localization.getLocalizationInstance().getString("client.gui.testLoc"));
    }

}
