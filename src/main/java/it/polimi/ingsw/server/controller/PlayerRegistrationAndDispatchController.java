package it.polimi.ingsw.server.controller;

import java.util.HashSet;

public class PlayerRegistrationAndDispatchController extends NewClientsAccepterClientHandler {

    public PlayerRegistrationAndDispatchController(ServerMessageSender messageSender) {
        super(new HashSet<>(), messageSender);
    }

    /**
     * Accept a socket connection from a new client
     * @param client new client to manage
     */
    @Override
    public void acceptAndRegisterNewClient(Client client) {
        registerClientWithThisHandler(client);
    }

    @Override
    public void run() {

    }

}
