package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.logger.LogLevel;
import it.polimi.ingsw.logger.ProjectLogger;
import it.polimi.ingsw.server.PlayerNameAlreadyInUseException;
import it.polimi.ingsw.server.controller.clientmessage.ClientMessage;
import it.polimi.ingsw.server.controller.clientmessage.RegisterPlayerNameClientMessage;
import it.polimi.ingsw.network.servermessage.InvalidClientMessageServerMessage;
import it.polimi.ingsw.network.servermessage.PlayerNameAlreadyExistsServerMessage;
import it.polimi.ingsw.server.GlobalPlayersManager;

import java.util.HashSet;
import java.util.Optional;

public class PlayerRegistrationAndDispatchController extends NewClientsAccepterClientHandler {

    protected ProjectLogger logger = ProjectLogger.getLogger();

    protected Optional<NewGameLobbyController> newGameLobby = Optional.empty();

    public PlayerRegistrationAndDispatchController(ServerMessageSender messageSender) {
        super(new HashSet<>(), messageSender);
    }

    /**
     * Accept a socket connection from a new client
     * @param client new client to manage
     */
    @Override
    public void acceptNewClient(Client client) {
        registerClientWithThisHandler(client);
    }

    @Override
    protected void handleNewMessage(ClientMessage message) {

        if(!(message instanceof RegisterPlayerNameClientMessage)) {
            sendMessage(
                new InvalidClientMessageServerMessage(
                "Invalid request. The client should send his username for authentication first."
                ),
                message.client
            );
            logger.log(LogLevel.ERROR, "Client %s sent an invalid request", message.client.getClientId());
            return;
        }
        RegisterPlayerNameClientMessage registerPlayerMessage = (RegisterPlayerNameClientMessage) message;

        GlobalPlayersManager playersManager = GlobalPlayersManager.getGlobalPlayerManager();
        if(playersManager.isPlayerNameAlreadyInUse(registerPlayerMessage.playerName)) {
            logger.log(
                LogLevel.ERROR,
                "A client tried to register with an already used player name %s",
                registerPlayerMessage.playerName
            );
            sendMessage(
                new PlayerNameAlreadyExistsServerMessage(registerPlayerMessage.playerName),
                registerPlayerMessage.client
            );
            return;
        }

        try {
            playersManager.createNewPlayer(registerPlayerMessage.playerName, registerPlayerMessage.client);
        } catch (PlayerNameAlreadyInUseException e) {
            //TODO
            e.printStackTrace();
        }

        if(newGameLobby.isEmpty()) {
            initNewGameLobby(registerPlayerMessage.client);
        } else {
        newGameLobby.get().acceptNewClient(message.client);
        }

    }

    protected void initNewGameLobby(Client clientThatShouldCreateLobby) {
        newGameLobby = Optional.of(new NewGameLobbyController(messageSender, clientThatShouldCreateLobby));
        newGameLobby.get().start();
    }

}
