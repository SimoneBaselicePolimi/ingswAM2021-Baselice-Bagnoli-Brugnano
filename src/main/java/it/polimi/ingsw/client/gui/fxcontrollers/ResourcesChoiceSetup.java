package it.polimi.ingsw.client.gui.fxcontrollers;


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

public class ResourcesChoiceSetup extends AbstractController{

    @FXML
    public Label resourcesTitle;

    @FXML
    public Label coins;

    @FXML
    public Label stones;

    @FXML
    public Label servants;

    @FXML
    public Label shields;

    @FXML
    public Label selectedResources;

    @FXML
    public ChoiceBox choiceBoxCoins;

    @FXML
    public ChoiceBox choiceBoxStones;

    @FXML
    public ChoiceBox choiceBoxServants;

    @FXML
    public ChoiceBox choiceBoxShields;

    @FXML
    public Button confirmButton;

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

        confirmButton.setVisible(false);

        String str[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14"};

        choiceBoxCoins.getItems().addAll(FXCollections.observableArrayList(str));
        choiceBoxShields.getItems().addAll(FXCollections.observableArrayList(str));
        choiceBoxStones.getItems().addAll(FXCollections.observableArrayList(str));
        choiceBoxServants.getItems().addAll(FXCollections.observableArrayList(str));

        resourcesTitle.setText(Localization.getLocalizationInstance().getString("client.gui.resourcesChoice.titleLabel", message.numberOfStarResources));
        confirmButton.setText(Localization.getLocalizationInstance().getString("client.gui.resourcesChoice.confirmButton"));
        //selectedResources.setText(Localization.getLocalizationInstance().getString("client.gui.resourcesChoice.selectedResources", totalResourcesSelected, message.numberOfStarResources));
        coins.setText(Localization.getLocalizationInstance().getString("client.gui.resourcesChoice.coins"));
        stones.setText(Localization.getLocalizationInstance().getString("client.gui.resourcesChoice.stones"));
        servants.setText(Localization.getLocalizationInstance().getString("client.gui.resourcesChoice.servants"));
        shields.setText(Localization.getLocalizationInstance().getString("client.gui.resourcesChoice.shields"));
        choiceBoxCoins.setValue(0);
        choiceBoxStones.setValue(0);
        choiceBoxServants.setValue(0);
        choiceBoxShields.setValue(0);

        updateResourcesChoice(message, str);
    }

    private void updateResourcesChoice(InitialChoicesServerMessage newMessage, String[] str){

        // add a listener for choiceBoxCoins
        choiceBoxCoins.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value)
            {
                coinsCurrentSelected = Integer.parseInt(str[new_value.intValue()]);
            }
        });

        // add a listener for choiceBoxStones
        choiceBoxStones.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value)
            {
                stonesCurrentSelected = Integer.parseInt(str[new_value.intValue()]);
            }
        });

        // add a listener for choiceBoxServants
        choiceBoxServants.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value)
            {
                servantsCurrentSelected = Integer.parseInt(str[new_value.intValue()]);
            }
        });

        // add a listener for choiceBoxShields
        choiceBoxShields.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value)
            {
                shieldsCurrentSelected = Integer.parseInt(str[new_value.intValue()]);
            }
        });

        totalResourcesSelected = coinsCurrentSelected + stonesCurrentSelected + shieldsCurrentSelected + servantsCurrentSelected;

        selectedResources.setText(Localization.getLocalizationInstance().getString("client.gui.resourcesChoice.selectedResources", totalResourcesSelected, newMessage.numberOfStarResources));

        confirmButton.setVisible(totalResourcesSelected == newMessage.numberOfStarResources);

    }

    @FXML
    public void onConfirmButtonPressed(){

    }

}
