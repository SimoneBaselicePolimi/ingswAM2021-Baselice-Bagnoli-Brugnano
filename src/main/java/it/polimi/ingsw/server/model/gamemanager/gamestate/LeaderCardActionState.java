package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.server.controller.clientrequest.ActivateLeaderCardClientRequest;
import it.polimi.ingsw.server.controller.clientrequest.DiscardLeaderCardClientRequest;
import it.polimi.ingsw.server.controller.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.gameactionshistory.ActivateLeaderCardsAction;
import it.polimi.ingsw.server.gameactionshistory.DiscardLeaderCardsAction;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;
import it.polimi.ingsw.server.model.gamemanager.GameManager;

import java.util.Map;

public abstract class LeaderCardActionState extends GameState {
    private final Player activePlayer;

    /**
     * GameState constructor
     * @param gameManager GameManager, see {@link GameManager}
     */
    protected LeaderCardActionState(GameManager gameManager) {
        super(gameManager);
        activePlayer = gameManager.getGameContext().getActivePlayer();
    }

    /**
     * Method to discard the leader card chosen by the player according to his request.
     * The leader card state changes from active to discarded.
     * @param request request of the player to discard the leader card, see {@link DiscardLeaderCardClientRequest}
     * @return messages sent to each player containing all changes made since the last game state update
     * @throws LeaderCardRequirementsNotSatisfiedException if a player wants to discard some leader cards but not
     * all card requirements have been satisfied
     */
    @Override
    public Map<Player, ServerMessage> handleRequestDiscardLeaderAction(DiscardLeaderCardClientRequest request) throws LeaderCardRequirementsNotSatisfiedException {

        if(!request.player.equals(activePlayer))
            return createInvalidRequestSenderIsNotActivePlayer(request.player, activePlayer);

        // discard leader card
        request.leaderCardThePlayerWantsToDiscard.discardLeaderCard();

        gameManager.getGameHistory().addAction(
            new DiscardLeaderCardsAction(activePlayer, request.leaderCardThePlayerWantsToDiscard)
        );

        return buildGameUpdateServerMessage();
    }

    /**
     * Method that activates the leader card chosen by the player according to his request.
     * The leader card state changes from hidden to active.
     * @param request request of the player to activate the leader card, see {@link ActivateLeaderCardClientRequest}
     * @return messages sent to each player containing all changes made since the last game state update
     * @throws LeaderCardRequirementsNotSatisfiedException if a player wants to activate some leader cards but not
     * all card requirements have been satisfied
     */
    @Override
    public Map<Player, ServerMessage> handleRequestActivateLeaderAction(ActivateLeaderCardClientRequest request) throws LeaderCardRequirementsNotSatisfiedException {

        if(!request.player.equals(activePlayer))
            return createInvalidRequestSenderIsNotActivePlayer(request.player, activePlayer);

        // activate leader card
       request.leaderCardThePlayerWantsToActivate.activateLeaderCard(gameManager.getGameContext().getPlayerContext(activePlayer));

        gameManager.getGameHistory().addAction(
            new ActivateLeaderCardsAction(activePlayer, request.leaderCardThePlayerWantsToActivate)
        );

        return buildGameUpdateServerMessage();
    }
}
