package it.polimi.ingsw.client.cli.clientstates;

import it.polimi.ingsw.client.cli.ClientManager;
import it.polimi.ingsw.server.controller.servermessage.ServerMessage;

public class GameSetupClientState extends ClientState {

    public GameSetupClientState(ClientManager clientManager) {
        super(clientManager);
        userCannotSendNewInput();
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
