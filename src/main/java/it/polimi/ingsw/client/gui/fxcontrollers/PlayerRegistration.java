package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.client.ServerMessageUtils;
import it.polimi.ingsw.client.clientmessage.RegisterPlayerNameClientMessage;
import it.polimi.ingsw.client.servermessage.NewPlayerEnteredNewGameLobbyServerMessage;
import it.polimi.ingsw.client.servermessage.PlayerCanCreateNewLobbyServerMessage;
import it.polimi.ingsw.client.servermessage.PlayerNameAlreadyExistsServerMessage;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.localization.LocalizationUtils;
import it.polimi.ingsw.server.model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class PlayerRegistration extends AbstractController {

    @FXML
    Label titleLabel;

    @FXML
    Label descLabel;

    @FXML
    TextField nameField;

    @FXML
    Label errorLabel;

    @FXML
    Button confirmButton;

    public PlayerRegistration() {


    }

    @FXML
    private void initialize() {
        titleLabel.setText(Localization.getLocalizationInstance().getString("client.gui.playerRegistration.titleLabel"));
        descLabel.setText(Localization.getLocalizationInstance().getString("client.gui.playerRegistration.descLabel"));
        errorLabel.setText(Localization.getLocalizationInstance().getString("client.gui.playerRegistration.errorLabel"));
        confirmButton.setText(Localization.getLocalizationInstance().getString("client.gui.playerRegistration.confirmButton"));

        //Make errorLabel invisible again when field text changes
        nameField.textProperty().addListener(obs -> errorLabel.setVisible(false));
    }

    @FXML
    private void onConfirmButtonPressed() {

        clientManager.sendMessageAndGetAnswer(new RegisterPlayerNameClientMessage(nameField.getText()))
            .thenCompose(
                serverMessage -> ServerMessageUtils.ifMessageTypeCompute(
                    serverMessage,
                    PlayerNameAlreadyExistsServerMessage .class,
                    message -> {
                        errorLabel.setVisible(true);
                        return CompletableFuture.completedFuture(null);
                    }
                ).elseIfMessageTypeCompute(
                    PlayerCanCreateNewLobbyServerMessage.class,
                    message -> {
                        clientManager.setMyPlayer(new Player(nameField.getText()));
                        clientManager.loadScene("preGame.fxml");
                        return CompletableFuture.completedFuture(message);
                    }
                ).elseIfMessageTypeCompute(
                    NewPlayerEnteredNewGameLobbyServerMessage.class,
                    message -> {
                        clientManager.setMyPlayer(new Player(nameField.getText()));
                        clientManager.loadScene("preGame.fxml");
                        return CompletableFuture.completedFuture(message);
                    }
                ).elseCompute(
                    m -> CompletableFuture.failedFuture(new Exception("Ooops"))
                ).apply()
            );

    }

}
