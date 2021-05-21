package it.polimi.ingsw.client.cli.clientstates;

import it.polimi.ingsw.client.cli.ClientManager;
import it.polimi.ingsw.client.clientmessage.GetInitialGameRepresentationClientMessage;
import it.polimi.ingsw.client.servermessage.GameInitialRepresentationServerMessage;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;

public class GameInitializationClientState extends ClientState {


    GameItemsManager itemsManager;

    public GameInitializationClientState(
        ClientManager clientManager
    ) {
        super(clientManager);
        itemsManager = new GameItemsManager();
        clientManager.addEntryToDeserializationContextMap("GameItemsManager", itemsManager);
        serverSender.sendMessageToServer(new GetInitialGameRepresentationClientMessage());
        userCannotSendNewInput();
    }

    @Override
    protected void _handleUserInput(String input) {

    }

    @Override
    public void handleServerMessage(ServerMessage serverMessage) {
        if(serverMessage instanceof GameInitialRepresentationServerMessage) {
            GameInitialRepresentationServerMessage representationServerMessage =
                (GameInitialRepresentationServerMessage) serverMessage;
            clientManager.setGameComponents(itemsManager, representationServerMessage.gameContextRepresentation);
            printLineLocalized("client.cli.setup.notifyRepresentationDownloaded");
            nextState(new GameSetupClientState(clientManager));
        }
    }

    @Override
    public void onStateBegin() {

    }

    @Override
    public void onStateDone() {

    }

}
