package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.gui.GuiClientManager;
import javafx.stage.Stage;

public class AbstractController {

    GuiClientManager clientManager;


    public AbstractController() {
        clientManager = GuiClientManager.getInstance();
    }


}
