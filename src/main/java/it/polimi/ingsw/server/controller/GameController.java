package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.servermessage.GameInitializationStartedServerMessage;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.GlobalPlayersManager;
import it.polimi.ingsw.server.controller.clientmessage.ClientMessage;
import it.polimi.ingsw.server.controller.clientmessage.GetInitialGameRepresentationClientMessage;
import it.polimi.ingsw.server.controller.servermessage.GameInitialRepresentationServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContextCreationError;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.gamemanager.InvalidGameRules;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GameController extends ClientHandler {

    Set<Player> players;

    protected GlobalPlayersManager playersManager;

    protected GameManager gameManager;

    public GameController(ServerMessageSender sender, Set<Player> players) throws GameContextCreationError,
        InvalidGameRules {
        super(new HashSet<>(), sender);
        this.players = players;
        playersManager = GlobalPlayersManager.getGlobalPlayerManager();
        players.stream()
            .map(p -> playersManager.getClientForPlayer(p))
            .forEach(this::registerClientWithThisHandler);
        gameManager = new GameManager(generateGameName(), players, this, null);
        players.stream()
            .map(p -> playersManager.getClientForPlayer(p))
            .forEach(client ->
                client.addEntryToDeserializationContextMap("GameItemsManager", gameManager.getGameItemsManager())
            );
        players.forEach(player -> sendMessage(
            new GameInitializationStartedServerMessage(),
            playersManager.getClientForPlayer(player))
        );
    }

    public void sendMessagesToClients(Map<Player, ServerMessage> messageForClients) {
        messageForClients.forEach((key, value) -> sendMessage(value, playersManager.getClientForPlayer(key)));

    }

    @Override
    protected void handleNewMessage(ClientMessage message) {
        if(message instanceof GetInitialGameRepresentationClientMessage) {
            players.forEach(player -> sendMessage(
                new GameInitialRepresentationServerMessage(
                    gameManager.getGameItemsManager().getAllItemsOfType(LeaderCard.class).stream()
                        .map(leaderCard -> leaderCard.getServerRepresentationForPlayer(player))
                        .collect(Collectors.toSet()),
                    gameManager.getGameContext().getServerRepresentationForPlayer(player)
                ),
                playersManager.getClientForPlayer(player)
            ));
        }
    }

    protected String generateGameName() {
        return players.stream().map(Player::getName).reduce("", (s, n) -> s + "#" + n);
    }

}
