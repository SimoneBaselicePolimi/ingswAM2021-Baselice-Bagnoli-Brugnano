package it.polimi.ingsw.server.controller;

import java.util.HashSet;

public class NewGameLobbyController extends NewClientsAccepterClientHandler {

    public NewGameLobbyController(ServerMessageSender messageSender) {
        super(new HashSet<>(), messageSender);
    }

    @Override
    public void run() {

    }

    /**
     *
     * @param client new client to manage
     */
    @Override
    public void acceptAndRegisterNewClient(Client client) {
        registerClientWithThisHandler(client);
        //TODO
    }

}
