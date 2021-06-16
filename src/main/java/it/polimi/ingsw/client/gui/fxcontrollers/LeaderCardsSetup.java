package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.ServerMessageUtils;
import it.polimi.ingsw.client.clientmessage.CreateNewLobbyClientMessage;
import it.polimi.ingsw.client.gui.fxcontrollers.components.LeaderCard;
import it.polimi.ingsw.client.servermessage.InitialChoicesServerMessage;
import it.polimi.ingsw.client.servermessage.NewPlayerEnteredNewGameLobbyServerMessage;
import it.polimi.ingsw.localization.Localization;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

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

    @FXML
    void initialize() {

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

        cardsComp.forEach(c -> c.setOnMouseClicked(e -> c.select()));

    }

    @FXML
    void onConfirmButtonPressed(){
//        clientManager.sendMessageAndGetAnswer(new CreateNewLobbyClientMessage(numOfPlayers))
//            .thenCompose(
//                serverMessage -> ServerMessageUtils.ifMessageTypeCompute(
//                    serverMessage,
//                    NewPlayerEnteredNewGameLobbyServerMessage.class,
//                    message -> {
//                        clientManager.addEntryToContextInfoMap("newPlayerEnteredMessage", message);
//                        clientManager.loadScene("Lobby.fxml");
//                        return CompletableFuture.completedFuture(message);
//                    }
//                ).elseCompute(
//                    m -> CompletableFuture.failedFuture(new Exception("Ooops"))
//                ).apply()
//            );

    }

}
