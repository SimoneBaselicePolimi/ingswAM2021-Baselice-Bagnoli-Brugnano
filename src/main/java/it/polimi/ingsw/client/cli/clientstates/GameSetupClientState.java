package it.polimi.ingsw.client.cli.clientstates;

import it.polimi.ingsw.client.cli.ClientManager;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.ClientGameContextRepresentation;
import it.polimi.ingsw.network.servermessage.GameInitialRepresentationServerMessage;
import it.polimi.ingsw.network.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.utils.serialization.SerializationHelper;

public class GameSetupClientState extends ClientState {

    GameInitialRepresentationServerMessage gameRepresentationServerMessage;

    GameItemsManager itemsManager;

    ClientGameContextRepresentation gameContextRepresentation;

    public GameSetupClientState(
        ClientManager clientManager,
        GameInitialRepresentationServerMessage gameRepresentationServerMessage
    ) {
        super(clientManager);
        this.gameRepresentationServerMessage = gameRepresentationServerMessage;
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

        }
    }

    @Override
    public void onStateBegin() {

    }

    @Override
    public void onStateDone() {

    }

}
