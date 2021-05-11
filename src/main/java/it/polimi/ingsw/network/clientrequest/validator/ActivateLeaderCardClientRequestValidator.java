package it.polimi.ingsw.network.clientrequest.validator;

import it.polimi.ingsw.network.clientrequest.ActivateLeaderCardClientRequest;
import it.polimi.ingsw.network.clientrequest.DiscardLeaderCardClientRequest;
import it.polimi.ingsw.network.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.server.model.gamemanager.GameManager;

import java.util.Optional;

public class ActivateLeaderCardClientRequestValidator extends ClientRequestValidator<ActivateLeaderCardClientRequest> {

    /**
     * Method that sends an error message if:
     * - The leader card the player wants to activate is not from the group of card he holds in his hand
     * - The player does not meet the requirements for activating the leader card
     * @param requestToValidate, see {@link ActivateLeaderCardClientRequest}
     * @param gameManager GameManager, see {@link GameManager}
     * @return Optional<InvalidRequestServerMessage>, see {@link InvalidRequestServerMessage}
     */
    @Override
    public Optional<InvalidRequestServerMessage> getErrorMessage(
        ActivateLeaderCardClientRequest requestToValidate,
        GameManager gameManager
    ) {

        Player activePlayer = gameManager.getGameContext().getActivePlayer();

        //check if the leader card the player wants to activate is from the group of card he holds in his hand
        if (!gameManager.getGameContext().getPlayerContext(activePlayer).getLeaderCards()
            .contains(requestToValidate.leaderCardThePlayerWantsToActivate))
            return createInvalidRequestServerMessage(
                "The leader card cannot be activate: the player does not own this card"
            );

        if (!requestToValidate.leaderCardThePlayerWantsToActivate.getState().equals(LeaderCardState.HIDDEN))
            return createInvalidRequestServerMessage(
                "The leader card cannot be activate: " +
                    "the player no longer has the card in his hand (the state is not HIDDEN)"
            );

        //check if the player meets the requirements for activating the leader card
        if(!requestToValidate.leaderCardThePlayerWantsToActivate
            .areRequirementsSatisfied(gameManager.getGameContext().getPlayerContext(activePlayer)))
                return createInvalidRequestServerMessage(
                    "The leader card cannot be activate: " +
                        "the player does not meet the requirements to activate the card"
                );

        return Optional.empty();
    }

}
