package it.polimi.ingsw.network.clientrequest.validator;

import it.polimi.ingsw.network.clientrequest.LeaderActionClientRequest;
import it.polimi.ingsw.network.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.server.model.gamemanager.GameManager;

import java.util.Optional;

public class LeaderActionClientRequestValidator extends ClientRequestValidator<LeaderActionClientRequest> {

    @Override
    public Optional<InvalidRequestServerMessage> getErrorMessage(
        LeaderActionClientRequest requestToValidate,
        GameManager gameManager
    ) {

        Player activePlayer = gameManager.getGameContext().getActivePlayer();

        //check if the leader card the player wants to discard is from the group of card he holds in his hand
        for (LeaderCard leaderCard : requestToValidate.leaderCardsThePlayerWantsToDiscard){
            if (!gameManager.getGameContext().getPlayerContext(activePlayer).getLeaderCards().contains(leaderCard))
                return createInvalidRequestServerMessage(
                    "The leader card cannot be discarded: the player does not own this card"
                );
            if (!leaderCard.getState().equals(LeaderCardState.HIDDEN))
                return createInvalidRequestServerMessage(
                    "The leader card cannot be discarded: " +
                        "the player no longer has the card in his hand (the state is not HIDDEN)"
                );
        }

        //check if the leader card the player wants to activate is from the group of card he holds in his hand
        for (LeaderCard leaderCard : requestToValidate.leaderCardsThePlayerWantsToActivate){
            if (!gameManager.getGameContext().getPlayerContext(activePlayer).getLeaderCards().contains(leaderCard))
                return createInvalidRequestServerMessage(
                    "The leader card cannot be activate: the player does not own this card"
                );
            if (!leaderCard.getState().equals(LeaderCardState.HIDDEN))
                return createInvalidRequestServerMessage(
                    "The leader card cannot be activate: " +
                        "the player no longer has the card in his hand (the state is not HIDDEN)"
                );
        }

        //check if the player meets the requirements for activating the leader card
        for (LeaderCard leaderCard : requestToValidate.leaderCardsThePlayerWantsToActivate){
            if(!leaderCard.areRequirementsSatisfied(gameManager.getGameContext().getPlayerContext(activePlayer)))
                return createInvalidRequestServerMessage(
                    "The leader card cannot be activate: " +
                        "the player does not meet the requirements to activate the card"
                );
        }

        return Optional.empty();
    }

}
