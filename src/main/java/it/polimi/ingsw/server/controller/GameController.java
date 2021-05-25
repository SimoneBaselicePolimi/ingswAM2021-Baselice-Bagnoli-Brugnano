package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.servermessage.GameInitializationStartedServerMessage;
import it.polimi.ingsw.network.servermessage.InvalidClientMessageServerMessage;
import it.polimi.ingsw.network.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.GlobalPlayersManager;
import it.polimi.ingsw.server.controller.clientmessage.ClientMessage;
import it.polimi.ingsw.server.controller.clientmessage.GetInitialGameRepresentationClientMessage;
import it.polimi.ingsw.server.controller.clientmessage.PlayerRequestClientMessage;
import it.polimi.ingsw.server.controller.clientmessage.ReadyToStartGameClientMessage;
import it.polimi.ingsw.server.controller.servermessage.GameInitialRepresentationServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContextCreationError;
import it.polimi.ingsw.server.model.gameitems.cardstack.ForbiddenPushOnTopException;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.gamemanager.InvalidGameRules;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GameController extends ClientHandler {

    Set<Player> players;

    protected GlobalPlayersManager playersManager;

    protected GameManager gameManager;

    Set<Player> playersReadyToStart = new HashSet<>();

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
            Player player = playersManager.getPlayerAssociatedWithClient(message.client);
            sendMessage(
                new GameInitialRepresentationServerMessage(
                    gameManager.getGameItemsManager().getAllItemsOfType(LeaderCard.class).stream()
                        .map(leaderCard -> leaderCard.getServerRepresentationForPlayer(player))
                        .collect(Collectors.toSet()),
                    gameManager.getGameContext().getServerRepresentationForPlayer(player)
                ),
                message.client
            );
        } else if (message instanceof ReadyToStartGameClientMessage) {
            playersReadyToStart.add(playersManager.getPlayerAssociatedWithClient(message.client));
            if(players.equals(playersReadyToStart))
                gameManager.startGame();
        } else if (message instanceof PlayerRequestClientMessage) {
            PlayerRequestClientMessage request = (PlayerRequestClientMessage) message;
            if(!request.request.player.equals(playersManager.getPlayerAssociatedWithClient(message.client)))
                sendMessage(
                    new InvalidClientMessageServerMessage(
                        "Illegal request. A cliet can not send a message impersonating another player!"
                    ), message.client
                );
            else {
                try {
                    gameManager.handleClientRequest(request.request);
                } catch (ResourceStorageRuleViolationException | LeaderCardRequirementsNotSatisfiedException | NotEnoughResourcesException | ForbiddenPushOnTopException e) {
                    new InvalidRequestServerMessage("Invalid request. Unexpected error");
                }
            }
        } else {
            new InvalidClientMessageServerMessage("Invalid message");
        }
    }

    protected String generateGameName() {
        return players.stream().map(Player::getName).reduce("", (s, n) -> s + "#" + n);
    }

}
