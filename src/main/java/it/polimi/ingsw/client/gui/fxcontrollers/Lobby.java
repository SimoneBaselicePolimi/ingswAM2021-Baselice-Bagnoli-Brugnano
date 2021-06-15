package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.ServerMessageUtils;
import it.polimi.ingsw.client.clientmessage.CreateNewLobbyClientMessage;
import it.polimi.ingsw.client.clientmessage.ReadyToStartGameClientMessage;
import it.polimi.ingsw.client.servermessage.*;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import javafx.fxml.FXML;

import java.awt.*;
import java.util.concurrent.CompletableFuture;

public class Lobby extends AbstractController {

    @FXML
    Label titleLabel;

    @FXML
    Label numOfPlayerLabel;

    @FXML
    Label currentPlayersInLobby;

    @FXML
    Button startGameButton;

    StringBuilder msg = new StringBuilder();

    public Lobby() {

    }

    @FXML
    private void initialize() {

        startGameButton.setVisible(false);

        NewPlayerEnteredNewGameLobbyServerMessage message = (NewPlayerEnteredNewGameLobbyServerMessage)clientManager.getEntryInContextInfoMap("newPlayerEnteredMessage");
        titleLabel.setText(Localization.getLocalizationInstance().getString("client.gui.playerRegistration.titleLabel"));
        numOfPlayerLabel.setText(Localization.getLocalizationInstance().getString(
            "client.gui.preGame.numOfPlayers",
            message.lobbySize,
            message.playersInLobby.size()
        ));

        for(Player player : message.playersInLobby){
            msg.append(player.getName());
            msg.append(",\n");
        }
        currentPlayersInLobby.setText(msg.toString());

        if(message.playersInLobby.equals(message.lobbySize))
            startGameButton.setVisible(true);
    }

    @FXML
    private void onStartGameButtonPressed(){

        clientManager.sendMessageAndGetAnswer(new ReadyToStartGameClientMessage())
            .thenCompose(
                serverMessage -> ServerMessageUtils.ifMessageTypeCompute(
                    serverMessage,
                    GameInitialRepresentationServerMessage.class,
                    message -> {
                        clientManager.loadScene("InitialLeaderCardChoices.fxml");
                        return CompletableFuture.completedFuture(message);
                    }
                ).elseCompute(
                    m -> CompletableFuture.failedFuture(new Exception("Ooops"))
                ).apply()
            );
    }
}





