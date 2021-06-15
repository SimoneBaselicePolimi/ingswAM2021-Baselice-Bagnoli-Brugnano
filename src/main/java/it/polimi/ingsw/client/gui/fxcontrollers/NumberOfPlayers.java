package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.ServerMessageUtils;
import it.polimi.ingsw.client.clientmessage.CreateNewLobbyClientMessage;
import it.polimi.ingsw.client.clientmessage.RegisterPlayerNameClientMessage;
import it.polimi.ingsw.client.servermessage.NewPlayerEnteredNewGameLobbyServerMessage;
import it.polimi.ingsw.client.servermessage.PlayerCanCreateNewLobbyServerMessage;
import it.polimi.ingsw.client.servermessage.PlayerNameAlreadyExistsServerMessage;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class NumberOfPlayers extends AbstractController{

    @FXML
    Label titleLabel;

    @FXML
    Label descLabel;

    @FXML
    TextField nameField;

    @FXML
    Button confirmButton;

    @FXML
    ChoiceBox choiceBox;

    public NumberOfPlayers() {

    }
    @FXML
    private void initialize() {
        titleLabel.setText(Localization.getLocalizationInstance().getString("client.gui.playerRegistration.titleLabel"));
        descLabel.setText(Localization.getLocalizationInstance().getString("client.gui.numberOfPlayers.descLabel"));
        confirmButton.setText(Localization.getLocalizationInstance().getString("client.gui.numberOfPlayers.confirmButton"));

        boolean isSinglePlayerEnabled = (boolean) clientManager.getEntryInContextInfoMap("isSinglePlayerEnabled");
        int lobbyMinSize = isSinglePlayerEnabled ? 1 : 2;
        int lobbyMaxSize = (int) clientManager.getEntryInContextInfoMap("maxLobbySize");

        for (int i = lobbyMinSize; i <= lobbyMaxSize; i++)
            choiceBox.getItems().add(i);
    }

    @FXML
    private void onConfirmButtonPressed() {

        int numOfPlayers = (int) choiceBox.getValue();

        clientManager.sendMessageAndGetAnswer(new CreateNewLobbyClientMessage(numOfPlayers))
            .thenCompose(
                serverMessage -> ServerMessageUtils.ifMessageTypeCompute(
                    serverMessage,
                    NewPlayerEnteredNewGameLobbyServerMessage.class,
                    message -> {
                        clientManager.addEntryToContextInfoMap("lobbySize", message.lobbySize);
                        clientManager.addEntryToContextInfoMap("newPlayerEnteredMessage", message);
                        clientManager.loadScene("Lobby.fxml");
                        return CompletableFuture.completedFuture(message);
                    }
                ).elseCompute(
                    m -> CompletableFuture.failedFuture(new Exception("Ooops"))
                ).apply()
            );
    }

}
