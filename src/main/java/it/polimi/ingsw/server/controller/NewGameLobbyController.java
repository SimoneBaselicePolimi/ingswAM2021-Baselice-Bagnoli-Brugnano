package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.logger.LogLevel;
import it.polimi.ingsw.logger.ProjectLogger;
import it.polimi.ingsw.server.controller.clientmessage.ClientMessage;
import it.polimi.ingsw.server.controller.clientmessage.CreateNewLobbyClientMessage;
import it.polimi.ingsw.network.servermessage.NewPlayerEnteredNewGameLobbyServerMessage;
import it.polimi.ingsw.server.GlobalPlayersManager;
import it.polimi.ingsw.server.model.Player;

import java.util.HashSet;
import java.util.Set;

public class NewGameLobbyController extends NewClientsAccepterClientHandler {

    protected boolean lobbyAlreadyCreated = false;

    protected Player playerThatShouldCreateTheLobby;

    protected Set<Player> playersInLobby;

    protected int lobbySize;

    protected ProjectLogger logger = ProjectLogger.getLogger();

    protected GlobalPlayersManager playersManager;

    public NewGameLobbyController(ServerMessageSender messageSender) {
        super(new HashSet<>(), messageSender);
        playersManager = GlobalPlayersManager.getGlobalPlayerManager();
    }

    /**
     *
     * @param client new client to manage
     */
    @Override
    public void acceptNewClient(Client client) {
        registerClientWithThisHandler(client);
        Player newPlayer = playersManager.getPlayerAssociatedWithClient(client);
        logger.log(
            LogLevel.INFO,
            "Player %s has joined the lobby",
            newPlayer.getName()
        );
//        playersInLobby.forEach(player -> sendMessage(
//            new NewPlayerEnteredNewGameLobbyServerMessage(newPlayer, playersInLobby),
//            playersManager.getClientForPlayer(player)
//        ));
    }

    @Override
    protected void handleNewMessage(ClientMessage message) {
        if(!lobbyAlreadyCreated &&
            message instanceof CreateNewLobbyClientMessage &&
            playersManager.getPlayerAssociatedWithClient(message.client).equals(playerThatShouldCreateTheLobby)
        ) {

        }
    }

}
