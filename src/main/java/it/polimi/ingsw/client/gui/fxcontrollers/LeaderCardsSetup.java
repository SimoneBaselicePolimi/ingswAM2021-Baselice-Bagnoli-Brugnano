package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.ServerMessageUtils;
import it.polimi.ingsw.client.clientmessage.CreateNewLobbyClientMessage;
import it.polimi.ingsw.client.gui.fxcontrollers.components.LeaderCard;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.client.servermessage.InitialChoicesServerMessage;
import it.polimi.ingsw.client.servermessage.NewPlayerEnteredNewGameLobbyServerMessage;
import it.polimi.ingsw.localization.Localization;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class LeaderCardsSetup extends AbstractController{

    @FXML
    public HBox cardsContainer;

    @FXML
    Button confirmButton;

    @FXML
    Label cardsSelection;

    List<LeaderCard> selectedCards = new ArrayList<>();

    @FXML
    void initialize() {

        confirmButton.setVisible(false);
        InitialChoicesServerMessage message =
            (InitialChoicesServerMessage) clientManager.getEntryInContextInfoMap("initialChoicesServerMessage");

        cardsSelection.setText(Localization.getLocalizationInstance().getString(
            "client.gui.leaderCardsSetup.selection",
            message.numberOfLeaderCardsToKeep
        ));

        confirmButton.setText(Localization.getLocalizationInstance().getString("client.gui.leaderCardsSetup.confirmButton"));

        List<LeaderCard> cardsComp = message.leaderCardsGivenToThePlayer.stream()
            .map(LeaderCard::new)
            .collect(Collectors.toList());

        cardsComp.forEach(c -> cardsContainer.getChildren().add(c));

        updateLeaderCardsSetup(cardsComp, message);


    }

    private void updateLeaderCardsSetup(List<LeaderCard> cardsComp, InitialChoicesServerMessage message){

        for(LeaderCard card : cardsComp){
            if(selectedCards.contains(card)){
                card.setOnMouseClicked(c -> card.deselect());
                selectedCards.remove(card);
            }
            else{
                card.setOnMouseClicked(c -> card.select());
                selectedCards.add(card);
            }
        }

        if(selectedCards.size() == message.numberOfLeaderCardsToKeep)
            confirmButton.setVisible(true);
    }

    @FXML
    void onConfirmButtonPressed(){

        clientManager.addEntryToContextInfoMap("selectedLeaderCards", selectedCards);
        clientManager.loadScene("ResourcesChoiceSetup.fxml");

    }

}
