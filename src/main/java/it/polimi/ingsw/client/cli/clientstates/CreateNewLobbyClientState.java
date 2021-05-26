package it.polimi.ingsw.client.cli.clientstates;

import it.polimi.ingsw.client.cli.ClientManager;
import it.polimi.ingsw.client.clientmessage.CreateNewLobbyClientMessage;
import it.polimi.ingsw.client.servermessage.NewPlayerEnteredNewGameLobbyServerMessage;
import it.polimi.ingsw.client.servermessage.PlayerCanCreateNewLobbyServerMessage;
import it.polimi.ingsw.client.servermessage.ServerMessage;

public class CreateNewLobbyClientState extends ClientState{

    protected PlayerCanCreateNewLobbyServerMessage serverMessage;

    public CreateNewLobbyClientState(ClientManager clientManager, PlayerCanCreateNewLobbyServerMessage serverMessage) {
        super(clientManager);
        this.serverMessage = serverMessage;
    }

    @Override
    protected void _handleUserInput(String input) {
        int intInput = Integer.parseInt(input);
        int lobbyMinSize = serverMessage.singlePlayerEnabled ? 1 : 2;
        int lobbyMaxSize = serverMessage.maxLobbySize;
        if (intInput < lobbyMinSize || intInput > lobbyMaxSize){
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
