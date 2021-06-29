package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.GameState;
import it.polimi.ingsw.client.ServerMessageUtils;
import it.polimi.ingsw.client.clientmessage.CreateNewLobbyClientMessage;
import it.polimi.ingsw.client.clientmessage.GetInitialGameRepresentationClientMessage;
import it.polimi.ingsw.client.clientmessage.ReadyToStartGameClientMessage;
import it.polimi.ingsw.client.servermessage.*;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.logger.LogLevel;
import it.polimi.ingsw.logger.ProjectLogger;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class Lobby extends AbstractController {

    @FXML
    Label titleLabel;

    @FXML
    Label numOfPlayerLabel;

    @FXML
    Label currentPlayersInLobby;


    public Lobby() {

    }

    @FXML
    private void initialize() {

        NewPlayerEnteredNewGameLobbyServerMessage message = (NewPlayerEnteredNewGameLobbyServerMessage) clientManager.getEntryInContextInfoMap("newPlayerEnteredMessage");
        titleLabel.setText(Localization.getLocalizationInstance().getString("client.gui.playerRegistration.titleLabel"));


        updateLobby(message);


    }

    private void updateLobby(NewPlayerEnteredNewGameLobbyServerMessage newMessage) {

        StringBuilder msg = new StringBuilder();
        for (Player player : newMessage.playersInLobby) {
            msg.append(" - ");
            msg.append(player.getName());
            msg.append("\n");
        }

        Platform.runLater(() -> {
            numOfPlayerLabel.setText(Localization.getLocalizationInstance().getString(
                "client.gui.preGame.numOfPlayers",
                newMessage.playersInLobby.size(),
                newMessage.lobbySize
            ));
            currentPlayersInLobby.setText(msg.toString());

            if (newMessage.playersInLobby.size() == newMessage.lobbySize) {
                clientManager.getNewMessageFromServer()
                    .thenCompose( serverMessage ->
                        ServerMessageUtils.ifMessageTypeCompute(
                            serverMessage,
                            GameInitializationStartedServerMessage.class,
                            message -> {
                                completePreGameSetup(newMessage.playersInLobby);
                                return CompletableFuture.completedFuture(null);
                            }
                        ).elseCompute(message -> {
                            ProjectLogger.getLogger().log(LogLevel.ERROR, "Unexpected message from server. Message type: %s", message);
                            return CompletableFuture.failedFuture(new Exception());
                        }).apply()
                    );
            } else {
                clientManager.getNewMessageFromServer()
                    .thenCompose(serverMessage ->
                        ServerMessageUtils.ifMessageTypeCompute(
                            serverMessage,
                            NewPlayerEnteredNewGameLobbyServerMessage.class,
                            message -> {
                                updateLobby(message);
                                return CompletableFuture.completedFuture(null);
                            }
                        ).elseCompute(
                            m -> CompletableFuture.failedFuture(new Exception("Ooops"))
                        ).apply()
                    );
            }

        });
    }


    private void completePreGameSetup(List<Player> players) {

        clientManager.setGameItemsManager(new GameItemsManager());
        clientManager.addEntryToContextInfoMap("gameItemsManager", clientManager.getGameItemsManager());
        players.forEach(player -> clientManager.getGameItemsManager().addItem(player));

        clientManager.sendMessageAndGetAnswer(new GetInitialGameRepresentationClientMessage())
            .thenCompose(
                serverMessage -> ServerMessageUtils.ifMessageTypeCompute(
                    serverMessage,
                    GameInitialRepresentationServerMessage.class,
                    representationServerMessage -> {
                        clientManager.setGameContextRepresentation(representationServerMessage.gameContextRepresentation);
                        return clientManager.sendMessageAndGetAnswer(new ReadyToStartGameClientMessage())
                            .thenCompose(serverMessage2 ->
                                ServerMessageUtils.ifMessageTypeCompute(
                                    serverMessage2,
                                    InitialChoicesServerMessage.class,
                                    initialChoicesServerMessage -> {
                                        clientManager.addEntryToContextInfoMap("initialChoicesServerMessage", serverMessage2);
                                        clientManager.setGameState(GameState.GAME_SETUP);
                                        clientManager.handleGameUpdates(initialChoicesServerMessage.gameUpdates);
                                        clientManager.loadScene("LeaderCardsSetup.fxml");
                                        return CompletableFuture.completedFuture(null);
                                    }
                                ).elseCompute(
                                    m2 -> CompletableFuture.failedFuture(new Exception())
                                ).apply()
                            );
                    }
                ).elseCompute(
                    m -> CompletableFuture.failedFuture(new Exception("Ooops"))
                ).apply()
            );
    }
}





