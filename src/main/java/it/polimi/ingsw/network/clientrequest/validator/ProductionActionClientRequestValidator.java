package it.polimi.ingsw.network.clientrequest.validator;

import it.polimi.ingsw.network.clientrequest.ProductionActionClientRequest;
import it.polimi.ingsw.network.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gamemanager.GameManager;

import java.util.Optional;

public class ProductionActionClientRequestValidator extends ClientRequestValidator <ProductionActionClientRequest> {
    @Override
    public Optional<InvalidRequestServerMessage> getErrorMessage(
        ProductionActionClientRequest requestToValidate,
        GameManager gameManager
    ) {

        //check if the player has the necessary resources to activate the production
        for (ResourceType resourceType : requestToValidate.starResourceCost.keySet()) {
            if (!gameManager.getGameContext().getPlayerContext(requestToValidate.player)
                .getAllResources().containsKey(resourceType)
                ||
                gameManager.getGameContext().getPlayerContext(requestToValidate.player)
                    .getAllResources().get(resourceType) < requestToValidate.starResourceCost.get(resourceType))
                return createInvalidRequestServerMessage(
                    "The player does not have the necessary resources to activate the production"
                );
        }
            return Optional.empty();
        }
    }
