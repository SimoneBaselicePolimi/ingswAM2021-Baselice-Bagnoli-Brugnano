package it.polimi.ingsw.client.gui.fxcontrollers;


import it.polimi.ingsw.client.gui.fxcontrollers.components.ResourcesChoice;
import it.polimi.ingsw.client.servermessage.InitialChoicesServerMessage;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.util.Arrays;

public class ResourcesChoiceSetup extends AbstractController{

    @FXML
    public AnchorPane container;

    private int totalResourcesSelected = 0;

    int coinsCurrentSelected = 0;
    int stonesCurrentSelected = 0;
    int servantsCurrentSelected = 0;
    int shieldsCurrentSelected = 0;

    public ResourcesChoiceSetup() {


    }

    @FXML
    public void initialize() {

        InitialChoicesServerMessage message =
            (InitialChoicesServerMessage) clientManager.getEntryInContextInfoMap("initialChoicesServerMessage");


        container.getChildren().add(
            new ResourcesChoice(message.numberOfStarResources, Arrays.asList(ResourceType.values()), e -> {})
        );

    }



}
