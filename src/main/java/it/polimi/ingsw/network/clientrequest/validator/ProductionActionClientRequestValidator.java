package it.polimi.ingsw.network.clientrequest.validator;

import it.polimi.ingsw.network.clientrequest.ProductionActionClientRequest;
import it.polimi.ingsw.network.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.ResourceUtils;
import it.polimi.ingsw.server.model.gamemanager.GameManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


public class ProductionActionClientRequestValidator extends ClientRequestValidator <ProductionActionClientRequest> {

    @Override
    public Optional<InvalidRequestServerMessage> getErrorMessage(
        ProductionActionClientRequest requestToValidate,
        GameManager gameManager
    ) {

        Set<Production> productionsThePlayerCanActivate = gameManager.getGameContext()
            .getPlayerContext(requestToValidate.player).getActiveProductions();

        Map<ResourceType, Integer> totalNonStarResourcesCost = new HashMap<>();
        int totalStarResourcesCost = 0;
        int totalStarResourcesAwards = 0;

        for (Production production : requestToValidate.productions) {

            //check if the production can be activate
            if (!productionsThePlayerCanActivate.contains(production))
                return createInvalidRequestServerMessage(
                    "The player cannot activate the production with ID: %s." +
                        "\nThe productions the player can activate are %s",
                    production.getItemId(),
                    productionsThePlayerCanActivate
                );

            totalNonStarResourcesCost = ResourceUtils.sum(
                totalNonStarResourcesCost,
                production.getProductionResourceCost()
            );
            totalStarResourcesCost += production.getProductionStarResourceCost();
            totalStarResourcesAwards += production.getProductionStarResourceReward();

        }

        //check if the number of star resources cost given is what expected
        if (totalStarResourcesCost != requestToValidate.starResourceCost.values().stream().mapToInt(i -> i).sum())
            return createInvalidRequestServerMessage(
                "The number of star resources chosen is different from the number of star resources needed " +
                    "to activate all the productions"
            );

        //check if the number of star resources award given is what expected
        if (totalStarResourcesAwards != requestToValidate.starResourceReward.values().stream().mapToInt(i -> i).sum())
            return createInvalidRequestServerMessage(
                "The number of star resources chosen as award is different from the number of " +
                    "star resources given by the activation of all the productions selected"
            );

        Map<ResourceType, Integer> totalResourcesCost = ResourceUtils.sum(
            totalNonStarResourcesCost,
            requestToValidate.starResourceCost
        );

        //check if the player has the necessary resources to activate the production
        if (!ResourceUtils.areResourcesAContainedInB(
            totalResourcesCost,
            gameManager.getGameContext().getPlayerContext(requestToValidate.player).getAllResources())
        )
            return createInvalidRequestServerMessage(
                "The player does not have the necessary resources to activate the production"
            );

        return Optional.empty();
    }
}

//rightProduction1
//- Cost : 0
//- StarResourceCost : 2
//- StarResourceReward : 3

//rightProduction2
//- Cost : 1 COINS, 2 SHIELDS
//- StarResourceCost : 0
//- StarResourceReward : 0

//The player has 1 COINS, 5 SHIELDS, 3 STONES