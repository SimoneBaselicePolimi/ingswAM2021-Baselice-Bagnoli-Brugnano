package it.polimi.ingsw.server.controller;

import java.util.HashSet;

public class ResumeGameLobbyController extends NewClientsAccepterClientHandler {

    public ResumeGameLobbyController(ServerMessageSender messageSender) {
        super(new HashSet<>(), messageSender);
    }

    @Override
    public void run() {

    }

    /**
     * Accept a socket connection from a new client
     *
     * @param client new client to manage
     */
    @Override
    public void acceptAndRegisterNewClient(Client client) {

    }

}
