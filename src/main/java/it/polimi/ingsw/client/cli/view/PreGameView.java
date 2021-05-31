package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.ServerMessageUtils;
import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.UnexpectedServerMessage;
import it.polimi.ingsw.client.cli.view.grid.GridView;
import it.polimi.ingsw.client.clientmessage.CreateNewLobbyClientMessage;
import it.polimi.ingsw.client.clientmessage.GetInitialGameRepresentationClientMessage;
import it.polimi.ingsw.client.clientmessage.ReadyToStartGameClientMessage;
import it.polimi.ingsw.client.clientmessage.RegisterPlayerNameClientMessage;
import it.polimi.ingsw.client.servermessage.*;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

public class PreGameView extends CliView {

    public PreGameView(CliClientManager clientManager, int rowSize, int columnSize) {
        super(clientManager, rowSize, columnSize);

        GridView bordersContainer =
            new GridView(clientManager, 1, 1, 1, rowSize, columnSize);
        addChildView(bordersContainer, 0, 0);

        UserConsole consoleView = new UserConsole(clientManager);
        bordersContainer.setView(0, 0, consoleView);
        clientManager.setNewUserIOLogger(consoleView);


        startPreGameSetup();

    }

    protected void startPreGameSetup() {
        registerPlayerNameAndGetNextMessage()
            .thenCompose(serverMessage ->
                ServerMessageUtils.ifMessageTypeCompute(serverMessage,
                    PlayerCanCreateNewLobbyServerMessage.class,
                    this::createLobbyAndGetEnteredLobbyServerMessage
                ).elseIfMessageTypeCompute(
                    NewPlayerEnteredNewGameLobbyServerMessage.class,
                    CompletableFuture::completedFuture
                ).elseCompute(message -> {
                    clientManager.tellUserLocalized("client.errors.unexpectedServerMessage");
                    return CompletableFuture.failedFuture(new UnexpectedServerMessage());
                }).apply()
            ).thenCompose(lobbyMessage -> {
            clientManager.setGameItemsManager(new GameItemsManager());
            clientManager.addEntryToDeserializationContextMap("gameItemsManager", clientManager.getGameItemsManager());
            return handleLobbyMessagesUntilGameInitialization(lobbyMessage);
        }).thenCompose(gameInitializationStartedServerMessage ->
            clientManager.sendMessageAndGetAnswer(new GetInitialGameRepresentationClientMessage())
        ).thenCompose(serverMessage ->
            ServerMessageUtils.ifMessageTypeCompute(serverMessage,
                GameInitialRepresentationServerMessage.class,
                representationServerMessage -> {
                    clientManager.setGameContextRepresentation(representationServerMessage.gameContextRepresentation);
                    clientManager.tellUserLocalized("client.cli.setup.notifyRepresentationDownloaded");
                    return clientManager.sendMessageAndGetAnswer(new ReadyToStartGameClientMessage());
                }
            ).elseCompute(message -> {
                clientManager.tellUserLocalized("client.errors.unexpectedServerMessage");
                return CompletableFuture.failedFuture(new UnexpectedServerMessage());
            }).apply()
        ).thenCompose(serverMessage -> ServerMessageUtils.ifMessageTypeCompute(
            serverMessage,
            InitialChoicesServerMessage.class,
            initialChoicesServerMessage -> {
                clientManager.tellUserLocalized("client.cli.setup.notifySetupIsStarting");
                this.destroyView();
                GameView gameView = new GameView(
                    clientManager,
                    clientManager.getConsoleDisplayHeight(),
                    clientManager.getConsoleDisplayWidth()
                );
                gameView.print();
                return CompletableFuture.completedFuture(null);
            }).elseCompute(message -> {
                clientManager.tellUserLocalized("client.errors.unexpectedServerMessage");
                return CompletableFuture.failedFuture(new UnexpectedServerMessage());
            }).apply()
        );
    }

    CompletableFuture<ServerMessage> registerPlayerNameAndGetNextMessage() {
        AtomicReference<String> newPlayerName = new AtomicReference<>();
        //Ask user player name
        return clientManager.askUserLocalized("client.cli.setup.askPlayerName")
            .thenCompose(userInput -> {
                newPlayerName.set(userInput);
                return clientManager.sendMessageAndGetAnswer(new RegisterPlayerNameClientMessage(userInput));
            }).thenCompose(
                serverMessage -> ServerMessageUtils.ifMessageTypeCompute(serverMessage,
                    PlayerNameAlreadyExistsServerMessage.class,
                    message -> {
                        clientManager.tellUserLocalized(
                            "client.cli.setup.notifyPlayerNameAlreadyInUse",
                            message.invalidPlayerName
                        );
                        return registerPlayerNameAndGetNextMessage();
                    }
                ).elseIfMessageTypeCompute(
                    PlayerCanCreateNewLobbyServerMessage.class,
                    message -> {
                        clientManager.setPlayer(new Player(newPlayerName.get()));
                        return CompletableFuture.completedFuture(message);
                    }
                ).elseIfMessageTypeCompute(
                    NewPlayerEnteredNewGameLobbyServerMessage.class,
                    message -> {
                        clientManager.setPlayer(new Player(newPlayerName.get()));
                        return CompletableFuture.completedFuture(message);
                    }
                ).elseCompute(
                    m -> CompletableFuture.failedFuture(new Exception("Ooops"))
                ).apply()
            );
    }

    CompletableFuture<Integer> askPlayerForLobbySize(int lobbyMinSize, int lobbyMaxSize) {
        return clientManager.askUserLocalized("client.cli.setup.askNumberOfPlayers")
            .thenCompose(input -> {
                int intInput = Integer.parseInt(input);
                if (intInput >= lobbyMinSize && intInput <= lobbyMaxSize){
                    return CompletableFuture.completedFuture(intInput);
                } else {
                    clientManager.tellUserLocalized("client.cli.setup.notifyPlayerNumberOfPlayersIsInvalid");
                    return askPlayerForLobbySize(lobbyMinSize, lobbyMaxSize);
                }
            });
    }

    CompletableFuture<NewPlayerEnteredNewGameLobbyServerMessage> createLobbyAndGetEnteredLobbyServerMessage(
        PlayerCanCreateNewLobbyServerMessage message
    ) {
        return askPlayerForLobbySize(message.singlePlayerEnabled ? 1 : 2, message.maxLobbySize)
            .thenCompose(lobbySizeFromUser ->
                clientManager.sendMessageAndGetAnswer(new CreateNewLobbyClientMessage(lobbySizeFromUser))
            ).thenCompose(serverMessageLobbyCreationAns ->
                ServerMessageUtils.ifMessageTypeCompute(serverMessageLobbyCreationAns,
                    NewPlayerEnteredNewGameLobbyServerMessage.class,
                    CompletableFuture::completedFuture
                ).elseCompute(unexpectedMessage -> {
                    clientManager.tellUserLocalized("client.errors.unexpectedServerMessage");
                    return createLobbyAndGetEnteredLobbyServerMessage(message);
                }).apply()
            );
    }

    public CompletableFuture<GameInitializationStartedServerMessage> handleLobbyMessagesUntilGameInitialization(
        NewPlayerEnteredNewGameLobbyServerMessage message
    ) {
        message.playersInLobby.forEach(player -> clientManager.getGameItemsManager().addItem(player));
        if(!clientManager.getPlayer().equals(message.newPlayer))
            clientManager.tellUserLocalized(
                "client.cli.setup.notifyPlayerEnteredInLobby",
                message.newPlayer
            );
        else
            clientManager.tellUserLocalized("client.cli.setup.notifyCurrentPlayerEnteredInLobby");
        clientManager.tellUserLocalized(
            "client.cli.setup.notifyLobbyPlayersList",
            message.playersInLobby.size(),
            message.lobbySize,
            message.playersInLobby.stream().map(Player::getName).reduce("", (a, n) -> a + " - " + n + "\n")
        );
        return clientManager.getNewMessageFromServer()
            .thenCompose(newMessage ->
                ServerMessageUtils.ifMessageTypeCompute(newMessage,
                    NewPlayerEnteredNewGameLobbyServerMessage.class,
                    this::handleLobbyMessagesUntilGameInitialization
                ).elseIfMessageTypeCompute(
                    GameInitializationStartedServerMessage.class,
                    CompletableFuture::completedFuture
                ).apply()
            );
    }

}
