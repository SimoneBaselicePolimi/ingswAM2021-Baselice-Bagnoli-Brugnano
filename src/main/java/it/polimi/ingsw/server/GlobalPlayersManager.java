package it.polimi.ingsw.server;

import it.polimi.ingsw.server.controller.Client;
import it.polimi.ingsw.server.model.Player;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class GlobalPlayersManager {

    protected static GlobalPlayersManager playersManagerInstance = null;

    public static GlobalPlayersManager getGlobalPlayerManager() {
        if(playersManagerInstance == null)
            playersManagerInstance = new GlobalPlayersManager();
        return playersManagerInstance;
    }

    Set<Player> players = ConcurrentHashMap.newKeySet();
    Map<Client, Player> clientToPlayer = new ConcurrentHashMap<>();
    Map<Player, Client> playerToClient = new ConcurrentHashMap<>();
    Map<Player, String> playerToGameID = new ConcurrentHashMap<>();

    public Player createNewPlayer(
        String playerName,
        Client clientAssociatedWithPlayer
    ) throws PlayerNameAlreadyInUseException {

        if(isPlayerNameAlreadyInUse(playerName))
            throw new PlayerNameAlreadyInUseException(String.format(
                "A player has already been registered with the name %s",
                playerName
            ));

        Player newPlayer = new Player(playerName);
        players.add(newPlayer);
        clientToPlayer.put(clientAssociatedWithPlayer, newPlayer);
        playerToClient.put(newPlayer, clientAssociatedWithPlayer);
        return newPlayer;

    }

    public boolean isPlayerNameAlreadyInUse(String playerName) {
        return players.stream().anyMatch(p -> p.getName().equals(playerName));
    }

    public Player getPlayerAssociatedWithClient(Client client) {
        return clientToPlayer.get(client);
    }

    public Client getClientForPlayer(Player client) {
        return playerToClient.get(client);
    }

}
