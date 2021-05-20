package it.polimi.ingsw.client.CLI.clientstates;

import it.polimi.ingsw.client.ConsoleWriter;
import it.polimi.ingsw.client.MessageSender;
import it.polimi.ingsw.client.clientmessage.CreateNewLobbyClientMessage;
import it.polimi.ingsw.client.clientmessage.RegisterPlayerNameClientMessage;
import it.polimi.ingsw.network.servermessage.PlayerNameAlreadyExistsServerMessage;
import it.polimi.ingsw.network.servermessage.ServerMessage;

public class AskAndRegisterPlayerNameClientState extends ClientState {

    public AskAndRegisterPlayerNameClientState(
        ConsoleWriter consoleWriter,
        MessageSender serverSender
    ) {
        super(consoleWriter, serverSender);
    }

    @Override
    public void _handleUserInput(String input) {
        userCannotSendNewInput();
        serverSender.sendMessageToServer(new RegisterPlayerNameClientMessage(input));
    }

    @Override
    public void handleServerMessage(ServerMessage serverMessage) {

        if(serverMessage instanceof PlayerNameAlreadyExistsServerMessage) {
            printLineLocalized(
                "client.cli.setup.notifyPlayerNameAlreadyInUse",
                ((PlayerNameAlreadyExistsServerMessage) serverMessage).invalidPlayerName
            );
            printLocalized("client.cli.setup.askPlayerName");
            userCanSendNewInput();
        } else {
            System.out.println("whuodwhiouwdhudiow");
//            if(serverMessage instanceof)
//                nextState();
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
