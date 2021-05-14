package it.polimi.ingsw.server.controller;

import java.util.HashSet;

public class GameController extends ClientHandler {

    public GameController(ServerMessageSender sender) {
        super(new HashSet<>(), sender);
    }

    @Override
    public void run() {

    }

}
