package it.polimi.ingsw.client.cli.clientstates;

import it.polimi.ingsw.client.ConsoleWriter;
import it.polimi.ingsw.client.MessageSender;
import it.polimi.ingsw.client.cli.ClientManager;
import it.polimi.ingsw.network.servermessage.ServerMessage;

public class CreateNewGameClientState extends ClientState{


    public CreateNewGameClientState(ClientManager clientManager) {
        super(clientManager);
    }

    @Override
    protected void _handleUserInput(String input) {

    }

    @Override
    public void handleServerMessage(ServerMessage serverMessage) {

    }

    @Override
    public void onStateBegin() {

    }

    @Override
    public void onStateDone() {

    }
}
