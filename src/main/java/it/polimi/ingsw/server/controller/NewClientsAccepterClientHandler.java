package it.polimi.ingsw.server.controller;

import java.util.Set;

public abstract class NewClientsAccepterClientHandler extends ClientHandler {

    NewClientsAccepterClientHandler(Set<Client> clientsToManage, ServerMessageSender messageSender) {
        super(clientsToManage, messageSender);
    }

    /**
     * Accept a socket connection from a new client
     * @param client new client to manage
     */
    public abstract void acceptAndRegisterNewClient(Client client);

}
