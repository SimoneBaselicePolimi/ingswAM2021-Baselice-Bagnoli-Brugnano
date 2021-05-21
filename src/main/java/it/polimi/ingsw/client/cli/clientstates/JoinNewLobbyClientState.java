package it.polimi.ingsw.client.cli.clientstates;

import it.polimi.ingsw.client.cli.ClientManager;
import it.polimi.ingsw.network.servermessage.GameInitializationStartedServerMessage;
import it.polimi.ingsw.network.servermessage.NewPlayerEnteredNewGameLobbyServerMessage;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.Player;

public class JoinNewLobbyClientState extends ClientState{

    protected NewPlayerEnteredNewGameLobbyServerMessage initialMessageFromServer;

    public JoinNewLobbyClientState(ClientManager clientManager, NewPlayerEnteredNewGameLobbyServerMessage initialMessageFromServer) {
        super(clientManager);
        this.initialMessageFromServer = initialMessageFromServer;
        userCannotSendNewInput();
    }

    @Override
    protected void _handleUserInput(String input) {}

    @Override
    public void handleServerMessage(ServerMessage serverMessage) {
        if(serverMessage instanceof NewPlayerEnteredNewGameLobbyServerMessage)
            printPlayerEnteredInLobbyMessage((NewPlayerEnteredNewGameLobbyServerMessage) serverMessage);
        else if(serverMessage instanceof GameInitializationStartedServerMessage)
            nextState(new GameInitializationClientState(clientManager));
        else
            throw new RuntimeException();   //TODO
    }

    @Override
    public void onStateBegin() {
        printPlayerEnteredInLobbyMessage(initialMessageFromServer);
    }

    @Override
    public void onStateDone() {}

    protected void printPlayerEnteredInLobbyMessage(NewPlayerEnteredNewGameLobbyServerMessage message) {
        if(!clientManager.getPlayer().equals(message.newPlayer))
            printLineLocalized("client.cli.setup.notifyPlayerEnteredInLobby", message.newPlayer);
        else
            printLineLocalized("client.cli.setup.notifyCurrentPlayerEnteredInLobby");
        printLineLocalized(
            "client.cli.setup.notifyLobbyPlayersList",
            message.playersInLobby.size(),
            message.lobbySize,
            message.playersInLobby.stream().map(Player::getName).reduce("", (a, n) -> a + " - " + n + "\n")
        );
    }
}
