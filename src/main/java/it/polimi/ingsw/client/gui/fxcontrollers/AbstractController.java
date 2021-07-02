package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.gui.GuiClientManager;

public class AbstractController {

    GuiClientManager clientManager;

    public AbstractController() {
        clientManager = GuiClientManager.getInstance();
    }

}
