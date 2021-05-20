package it.polimi.ingsw.client.cli.clientstates;

import it.polimi.ingsw.client.ConsoleWriter;
import it.polimi.ingsw.client.MessageSender;
import it.polimi.ingsw.client.cli.ClientManager;
import it.polimi.ingsw.client.clientmessage.RegisterPlayerNameClientMessage;
import it.polimi.ingsw.network.servermessage.NewPlayerEnteredNewGameLobbyServerMessage;
import it.polimi.ingsw.network.servermessage.PlayerNameAlreadyExistsServerMessage;
import it.polimi.ingsw.network.servermessage.ServerMessage;

public class JoinNewLobbyClientState extends ClientState{

    protected NewPlayerEnteredNewGameLobbyServerMessage serverMessage;

    public JoinNewLobbyClientState(ClientManager clientManager, NewPlayerEnteredNewGameLobbyServerMessage serverMessage) {
        super(clientManager);
        this.serverMessage = serverMessage;
    }

    @Override
    protected void _handleUserInput(String input) {

    }

    @Override
    public void handleServerMessage(ServerMessage serverMessage) {
    }

    @Override
    public void onStateBegin() {
        if(!clientManager.getPlayer().equals(serverMessage.newPlayer))
            printLineLocalized("client.cli.setup.notifyEntryPlayerInLobby", serverMessage.newPlayer);
        else
            printLineLocalized("client.cli.setup.notifyYouEntryInLobby");
    }

    @Override
    public void onStateDone() {
    }
}
