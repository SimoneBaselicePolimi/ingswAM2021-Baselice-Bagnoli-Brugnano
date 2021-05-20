package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.logger.LogLevel;
import it.polimi.ingsw.logger.ProjectLogger;
import it.polimi.ingsw.server.controller.clientmessage.ClientMessage;
import it.polimi.ingsw.server.controller.clientmessage.CreateNewLobbyClientMessage;
import it.polimi.ingsw.network.servermessage.NewPlayerEnteredNewGameLobbyServerMessage;
import it.polimi.ingsw.server.GlobalPlayersManager;
import it.polimi.ingsw.server.model.Player;

import java.util.*;

public class NewGameLobbyController extends NewClientsAccepterClientHandler {

    protected boolean lobbyAlreadyCreated = false;

    protected Player playerThatShouldCreateTheLobby;

    protected List<Player> playersInLobby;

    protected int lobbySize;

    protected ProjectLogger logger = ProjectLogger.getLogger();

    protected GlobalPlayersManager playersManager;

    protected List<Runnable> onLobbyCreatedCallbacks = Collections.synchronizedList(new ArrayList<>());

    public NewGameLobbyController(ServerMessageSender messageSender, Client clientThatShouldCreateTheLobby) {
        super(new HashSet<>(), messageSender);
        playersManager = GlobalPlayersManager.getGlobalPlayerManager();
        registerClientWithThisHandler(clientThatShouldCreateTheLobby);
        playerThatShouldCreateTheLobby = playersManager.getPlayerAssociatedWithClient(clientThatShouldCreateTheLobby);
        playersInLobby = new ArrayList<>();
    }

    /**
     *
     * @param client new client to manage
     */
    @Override
    public void acceptNewClient(Client client) {
        if(canAcceptNewPlayers()) {
            registerClientWithThisHandler(client);
            Player newPlayer = playersManager.getPlayerAssociatedWithClient(client);
            logger.log(
                LogLevel.INFO,
                "Player %s has joined the lobby",
                newPlayer.getName()
            );
            playersInLobby.forEach(player -> sendMessage(
                new NewPlayerEnteredNewGameLobbyServerMessage(newPlayer, playersInLobby),
                playersManager.getClientForPlayer(player)
            ));
        } else {
            //TODO
        }

    }

    public boolean hasLobbyBeenCreated() {
        return lobbyAlreadyCreated;
    }

    public boolean isLobbyFull() {
        return playersInLobby.size() == lobbySize;
    }

    public boolean canAcceptNewPlayers() {
        return hasLobbyBeenCreated() && !isLobbyFull();
    }

    public void addOnLobbyCreatedCallback(Runnable callback) {
        onLobbyCreatedCallbacks.add(callback);
    }

    protected void runOnLobbyCreatedCallbacks() {
        onLobbyCreatedCallbacks.forEach(Runnable::run);
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
