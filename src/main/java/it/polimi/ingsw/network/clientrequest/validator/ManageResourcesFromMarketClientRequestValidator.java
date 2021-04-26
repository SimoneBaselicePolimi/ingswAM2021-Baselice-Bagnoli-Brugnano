package it.polimi.ingsw.network.clientrequest.validator;

import it.polimi.ingsw.network.clientrequest.ManageResourcesFromMarketClientRequest;
import it.polimi.ingsw.network.clientrequest.MarketActionFetchColumnClientRequest;
import it.polimi.ingsw.network.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.market.Market;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gamemanager.GameManager;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ManageResourcesFromMarketClientRequestValidator extends ClientRequestValidator <ManageResourcesFromMarketClientRequest>{

    @Override
    public Optional<InvalidRequestServerMessage> getErrorMessage(
        ManageResourcesFromMarketClientRequest requestToValidate,
        GameManager gameManager
    ) {

        PlayerContext playerContext = gameManager.getGameContext().getPlayerContext(requestToValidate.player);
        Set<ResourceType> activeLeaderCardsSpecialMarblesLeft = playerContext
            .getActiveLeaderCardsWhiteMarblesMarketSubstitutions();
        int sumOfStarResources = 0;

        // check if star resources chosen by the player are from those possible to choose
        for (Map<ResourceType, Integer> starResourcesChosenToAdd: requestToValidate.starResourcesChosenToAddByStorage.values()){
            for (ResourceType resource : starResourcesChosenToAdd.keySet()){
                if (!activeLeaderCardsSpecialMarblesLeft.contains(resource)) {
                    return createInvalidRequestServerMessage(
                        "The star resource chosen by the player is not present among those provided by the " +
                            "special abilities of the active leader cards"
                    );
                }
                activeLeaderCardsSpecialMarblesLeft.remove(resource);
                sumOfStarResources += starResourcesChosenToAdd.get(resource);
            }
        }

        // check if the number of star resources is the same of what is expected
        if (sumOfStarResources != playerContext.getTempStarResources())
            return createInvalidRequestServerMessage(
                "The number of star resources chosen by the player is not the same of the one required. " +
                    "The number of star resources required is %s",
                playerContext.getTempStarResources()
            );

        // check if the number of resources the player wants to add in storages is the same as the one assigned to him
        int sumOfResourcesToAdd = 0;
        for (Map<ResourceType, Integer> resourcesToAdd: requestToValidate.resourcesToAddByStorage.values()) {
            for (ResourceType resource : resourcesToAdd.keySet()) {
                sumOfResourcesToAdd = resourcesToAdd.get(resource);
            }
        }
        if (sumOfStarResources != playerContext.)



        return createInvalidRequestServerMessage(
            "The number of  resources chosen by the player is not the same of the one required." +
                "The number of star resources required is %s"
        );

                activeLeaderCardsSpecialMarblesLeft.remove(resource);
                sumOfStarResources += starResourcesChosenToAdd.get(resource);
            }
        }

        // check if the number of star resources is the same of what is expected
        if (sumOfStarResources != playerContext.getTempStarResources())
            return createInvalidRequestServerMessage(
                "The number of star resources chosen by the player is not the same of the one required. " +
                    "The number of star resources required is %s",
                playerContext.getTempStarResources()
            );
        return Optional.empty();
    }
}

//public final Map<ResourceStorage, Map<ResourceType, Integer>> resourcesToAddByStorage;
//
//    public final Map<ResourceStorage, Map<ResourceType, Integer>> starResourcesChosenToAddByStorage;
