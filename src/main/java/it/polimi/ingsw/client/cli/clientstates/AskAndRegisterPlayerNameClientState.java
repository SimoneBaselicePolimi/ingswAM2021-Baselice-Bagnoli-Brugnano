package it.polimi.ingsw.client.cli.clientstates;

import it.polimi.ingsw.client.cli.ClientManager;
import it.polimi.ingsw.client.clientmessage.RegisterPlayerNameClientMessage;
import it.polimi.ingsw.server.controller.servermessage.NewPlayerEnteredNewGameLobbyServerMessage;
import it.polimi.ingsw.server.controller.servermessage.PlayerCanCreateNewLobbyServerMessage;
import it.polimi.ingsw.server.controller.servermessage.PlayerNameAlreadyExistsServerMessage;
import it.polimi.ingsw.server.controller.servermessage.ServerMessage;

public class AskAndRegisterPlayerNameClientState extends ClientState {


    public AskAndRegisterPlayerNameClientState(ClientManager clientManager) {
        super(clientManager);
    }

    @Override
    public void _handleUserInput(String input) {
        userCannotSendNewInput();
        serverSender.sendMessageToServer(new RegisterPlayerNameClientMessage(input));
    }

    @Override
    public void handleServerMessage(ServerMessage serverMessage) {

        if (serverMessage instanceof PlayerNameAlreadyExistsServerMessage) {
            printLineLocalized(
                "client.cli.setup.notifyPlayerNameAlreadyInUse",
                ((PlayerNameAlreadyExistsServerMessage) serverMessage).invalidPlayerName
            );
            printLocalized("client.cli.setup.askPlayerName");
            userCanSendNewInput();
        } else {
            if (serverMessage instanceof PlayerCanCreateNewLobbyServerMessage)
                nextState(new CreateNewLobbyClientState(
                    clientManager,
                    (PlayerCanCreateNewLobbyServerMessage) serverMessage)
                );

            if (serverMessage instanceof NewPlayerEnteredNewGameLobbyServerMessage) {
                nextState(new JoinNewLobbyClientState(
                    clientManager,
                    (NewPlayerEnteredNewGameLobbyServerMessage) serverMessage)
                );
                clientManager.setPlayer(((NewPlayerEnteredNewGameLobbyServerMessage) serverMessage).newPlayer);
            }

        }
    }

    @Override
    public void onStateBegin() {
        printLocalized("client.cli.setup.askPlayerName");
        userCanSendNewInput();
    }

    @Override
    public void onStateDone() {

    }

}
