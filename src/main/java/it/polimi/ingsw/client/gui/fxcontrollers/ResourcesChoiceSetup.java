package it.polimi.ingsw.client.gui.fxcontrollers;


import it.polimi.ingsw.client.gui.fxcontrollers.components.ResourcesChoice;
import it.polimi.ingsw.client.servermessage.InitialChoicesServerMessage;
import it.polimi.ingsw.client.servermessage.NewPlayerEnteredNewGameLobbyServerMessage;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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
            new ResourcesChoice(message.numberOfStarResources, Arrays.asList(ResourceType.values()))
        );

    }



}
