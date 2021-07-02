package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.configfile.GameRules;
import it.polimi.ingsw.logger.LogLevel;
import it.polimi.ingsw.logger.ProjectLogger;
import it.polimi.ingsw.server.controller.servermessage.InvalidClientMessageServerMessage;
import it.polimi.ingsw.server.controller.servermessage.PlayerCanCreateNewLobbyServerMessage;
import it.polimi.ingsw.server.controller.clientmessage.ClientMessage;
import it.polimi.ingsw.server.controller.clientmessage.CreateNewLobbyClientMessage;
import it.polimi.ingsw.server.controller.servermessage.NewPlayerEnteredNewGameLobbyServerMessage;
import it.polimi.ingsw.server.GlobalPlayersManager;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContextCreationError;
import it.polimi.ingsw.server.model.gamemanager.InvalidGameRules;
import it.polimi.ingsw.utils.FileManager;

import java.io.IOException;
import java.util.*;

public class NewGameLobbyController extends NewClientsAccepterClientHandler {

    //TODO handle client disconnected

    protected boolean lobbyAlreadyCreated = false;

    protected Player playerThatShouldCreateTheLobby;

    protected List<Player> playersInLobby = new ArrayList<>();

    protected int lobbySize;

    protected ProjectLogger logger = ProjectLogger.getLogger();

    protected GlobalPlayersManager playersManager;

    protected List<Runnable> onLobbyCreatedCallbacks = Collections.synchronizedList(new ArrayList<>());

    protected final int lobbyMinSize, lobbyMaxSize;

    public NewGameLobbyController(ServerMessageSender messageSender, Client clientThatShouldCreateTheLobby){
        super(new HashSet<>(), messageSender);
        playersManager = GlobalPlayersManager.getGlobalPlayerManager();
        registerClientWithThisHandler(clientThatShouldCreateTheLobby);
        playerThatShouldCreateTheLobby = playersManager.getPlayerAssociatedWithClient(clientThatShouldCreateTheLobby);

        GameRules rules;
        try {
            rules = FileManager.getFileManagerInstance().getGameRules(FileManager.DEFAULT_RULES_PATH);
            lobbyMinSize = rules.gameInfoConfig.singlePlayerEnabled ? 1 : 0;
            lobbyMaxSize = rules.gameInfoConfig.maxNumberOfPlayers;
        } catch (IOException e) {
            logger.log(LogLevel.ERROR, "Error!");
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        sendMessage(
            new PlayerCanCreateNewLobbyServerMessage(
                lobbyMinSize == 1,
                rules.gameInfoConfig.maxNumberOfPlayers
            ),
            clientThatShouldCreateTheLobby
        );
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
            playersInLobby.add(newPlayer);
            playersInLobby.forEach(player -> sendMessage(
                new NewPlayerEnteredNewGameLobbyServerMessage(newPlayer, playersInLobby, lobbySize),
                playersManager.getClientForPlayer(player)
            ));
            if(isLobbyFull()) {
                try {
                    GameController gameController = new GameController(messageSender, new HashSet<>(playersInLobby));
                    gameController.start();
                } catch (GameContextCreationError gameContextCreationError) {
                    //TODO
                    gameContextCreationError.printStackTrace();
                } catch (InvalidGameRules invalidGameRules) {
                    //TODO
                    invalidGameRules.printStackTrace();
                }
            }
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
            CreateNewLobbyClientMessage lobbyCreationInfoMessage = (CreateNewLobbyClientMessage) message;
            lobbySize = lobbyCreationInfoMessage.lobbySize;
            if(lobbySize>=lobbyMinSize && lobbySize<=lobbyMaxSize) {
                lobbyAlreadyCreated = true;
                acceptNewClient(message.client);
            } else {
                sendMessage(new InvalidClientMessageServerMessage("Invalid lobby size"), message.client);
            }
        }
    }

}
