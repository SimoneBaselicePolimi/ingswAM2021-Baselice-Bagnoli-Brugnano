package it.polimi.ingsw.client.gui.fxcontrollers;


import it.polimi.ingsw.client.servermessage.InitialChoicesServerMessage;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class ResourcesChoiceSetup extends AbstractController{

    public Label resourcesTitle;

    public Button confirmButton;

    public Label coins;

    public Label stones;

    public Label servants;

    public Label shields;

    public ChoiceBox choiceBoxCoins;

    public ChoiceBox choiceBoxStones;

    public ChoiceBox choiceBoxServants;

    public ChoiceBox choiceBoxShields;

    public ResourcesChoiceSetup() {


    }

    public void initialize() {

        InitialChoicesServerMessage message =
            (InitialChoicesServerMessage) clientManager.getEntryInContextInfoMap("initialChoicesServerMessage");

        resourcesTitle.setText(Localization.getLocalizationInstance().getString("client.gui.resourcesChoice.titleLabel", message.numberOfStarResources));
        confirmButton.setText(Localization.getLocalizationInstance().getString("client.gui.resourcesChoice.confirmButton"));
        coins.setText(Localization.getLocalizationInstance().getString("client.gui.resourcesChoice.coins"));
        stones.setText(Localization.getLocalizationInstance().getString("client.gui.resourcesChoice.stones"));
        servants.setText(Localization.getLocalizationInstance().getString("client.gui.resourcesChoice.servants"));
        shields.setText(Localization.getLocalizationInstance().getString("client.gui.resourcesChoice.shields"));
        choiceBoxCoins.setValue(0);
        choiceBoxStones.setValue(0);
        choiceBoxServants.setValue(0);
        choiceBoxShields.setValue(0);
        choiceBoxCoins.getItems().addAll("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14");
        choiceBoxStones.getItems().addAll("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14");
        choiceBoxServants.getItems().addAll("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14");
        choiceBoxShields.getItems().addAll("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14");

    }

    private void updateResourcesChoice(InitialChoicesServerMessage newMessage){

    }

    public void onConfirmButtonPressed(){

    }
}
