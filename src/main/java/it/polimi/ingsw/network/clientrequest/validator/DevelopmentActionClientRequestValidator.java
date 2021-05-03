package it.polimi.ingsw.network.clientrequest.validator;

import it.polimi.ingsw.network.clientrequest.DevelopmentActionClientRequest;
import it.polimi.ingsw.network.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gamemanager.GameManager;

import java.util.Map;
import java.util.Optional;

public class DevelopmentActionClientRequestValidator extends ClientRequestValidator <DevelopmentActionClientRequest> {

    /**
     * Method that sends an error message if:
     * - The development card requested by the player is not available
     * - The player does not have the necessary resources to get the development card
     * - The player cannot add the development card on top of the deck specified by him
     * @param requestToValidate specific request sent by the client
     * @param gameManager GameManager, see {@link GameManager}
     * @return Optional<InvalidRequestServerMessage>, see {@link InvalidRequestServerMessage}
     */
    @Override
    public Optional<InvalidRequestServerMessage> getErrorMessage(
        DevelopmentActionClientRequest requestToValidate,
        GameManager gameManager
    ) {
        // check if the development card requested by the player is available
        if (!gameManager.getGameContext().getDevelopmentCardsTable().getAvailableCards().contains(requestToValidate.developmentCard))
            return createInvalidRequestServerMessage(
                "The development card requested by the player is not available"
            );

        //check if the player has the necessary resources to get the development card
        Map<ResourceType, Integer> playerResources = gameManager.getGameContext()
            .getPlayerContext(requestToValidate.player).getAllResources();
        for (ResourceType resourceType : requestToValidate.developmentCard.getPurchaseCost().keySet()) {
            if (!playerResources.keySet().contains(resourceType)
                || playerResources.get(resourceType) < requestToValidate.developmentCard.getPurchaseCost().get(resourceType)
            )
                return createInvalidRequestServerMessage(
                    "The player does not have the resources needed to obtain the development card"
                );
        }

        //check if the player can add the development card on top of the deck specified by him
        if (!gameManager.getGameContext().getPlayerContext(requestToValidate.player)
            .canAddDevelopmentCard(requestToValidate.developmentCard, requestToValidate.deckNumber)
        )
            return createInvalidRequestServerMessage(
                "The player can not add the development card on the top of this deck"
            );

        return Optional.empty();
    }
}
