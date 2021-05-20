package it.polimi.ingsw.client.cli.clientstates;

import it.polimi.ingsw.client.ConsoleWriter;
import it.polimi.ingsw.client.MessageSender;
import it.polimi.ingsw.client.cli.ClientManager;
import it.polimi.ingsw.client.clientmessage.CreateNewLobbyClientMessage;
import it.polimi.ingsw.network.servermessage.NewPlayerEnteredNewGameLobbyServerMessage;
import it.polimi.ingsw.network.servermessage.PlayerCanCreateNewLobbyServerMessage;
import it.polimi.ingsw.network.servermessage.PlayerNameAlreadyExistsServerMessage;
import it.polimi.ingsw.network.servermessage.ServerMessage;

public class CreateNewLobbyClientState extends ClientState{

    protected PlayerCanCreateNewLobbyServerMessage serverMessage;

    public CreateNewLobbyClientState(ClientManager clientManager, PlayerCanCreateNewLobbyServerMessage serverMessage) {
        super(clientManager);
        this.serverMessage = serverMessage;
    }

    @Override
    protected void _handleUserInput(String input) {
        int intInput = Integer.parseInt(input);
        int lobbyMinSize = serverMessage.singlePlayerEnabled ? 0 : 1;
        int lobbyMaxSize = serverMessage.maxLobbySize;
        if (lobbyMaxSize <= 0 || lobbyMaxSize > serverMessage.maxLobbySize){
            printLineLocalized("client.cli.setup.notifyPlayerNumberOfPlayersIsInvalid");
            printLocalized("client.cli.setup.askNumberOfPlayers");
        } else
            serverSender.sendMessageToServer(new CreateNewLobbyClientMessage(intInput));
    }

    @Override
    public void handleServerMessage(ServerMessage serverMessage) {
        if (serverMessage instanceof NewPlayerEnteredNewGameLobbyServerMessage) {
            nextState(
                new JoinNewLobbyClientState(
                    clientManager,
                    (NewPlayerEnteredNewGameLobbyServerMessage) serverMessage
                )
            );
            clientManager.setPlayer(((NewPlayerEnteredNewGameLobbyServerMessage) serverMessage).newPlayer);
        }
        //TODO
    }

    @Override
    public void onStateBegin() {
        printLocalized("client.cli.setup.askNumberOfPlayers");
    }

    @Override
    public void onStateDone() {

    }
}
