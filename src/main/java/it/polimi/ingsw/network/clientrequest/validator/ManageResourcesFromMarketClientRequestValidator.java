package it.polimi.ingsw.network.clientrequest.validator;

import it.polimi.ingsw.network.clientrequest.ManageResourcesFromMarketClientRequest;
import it.polimi.ingsw.server.controller.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.ResourceUtils;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.storage.ResourceStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ManageResourcesFromMarketClientRequestValidator extends ClientRequestValidator <ManageResourcesFromMarketClientRequest>{

    /**
     * Method that sends an error message if:
     * - Star resources chosen by the player are not from those possible to choose
     * @param requestToValidate specific request sent by the client
     * @param gameManager GameManager, see {@link GameManager}
     * @return Optional<InvalidRequestServerMessage>, see {@link InvalidRequestServerMessage}
     */
    @Override
    public Optional<InvalidRequestServerMessage> getErrorMessage(
        ManageResourcesFromMarketClientRequest requestToValidate,
        GameManager gameManager
    ) {

        PlayerContext playerContext = gameManager.getGameContext().getPlayerContext(requestToValidate.player);
        Set<ResourceType> possibleStarMarbleSubstitution = playerContext
            .getActiveLeaderCardsWhiteMarblesMarketSubstitutions();
        int sumOfStarResources = 0;
        Map<ResourceStorage, Map<ResourceType, Integer>> totalResources = new HashMap<>();

        // check if star resources chosen by the player are from those possible to choose
        for (Map<ResourceType, Integer> starResourcesChosenToAdd : requestToValidate.starResourcesChosenToAddByStorage.values()){
            for (ResourceType resourceType : starResourcesChosenToAdd.keySet()){
                if (!possibleStarMarbleSubstitution.contains(resourceType)) {
                    return createInvalidRequestServerMessage(
                        "The resource type chosen by the player as star resources substitution " +
                            "is not present among those provided by the special abilities of the active leader cards"
                    );
                }
                sumOfStarResources += starResourcesChosenToAdd.get(resourceType);
            }
        }

        // check if the number of star resources is the same as what is expected
        if (sumOfStarResources != playerContext.getTempStarResources())
            return createInvalidRequestServerMessage(
                "The number of star resources chosen by the player is not the same of the one required. " +
                    "The number of star resources required is %s",
                playerContext.getTempStarResources()
            );

        // check if the resources the player wants to add in storages are the same as those assigned to him
        Map<ResourceType, Integer> sumOfResourcesFromThePlayer = ResourceUtils.sum(
            ResourceUtils.sum(requestToValidate.resourcesToAddByStorage.values()),
            requestToValidate.resourcesLeftInTemporaryStorage
        );
        if(!sumOfResourcesFromThePlayer.equals(playerContext.getTemporaryStorageResources())){
            return createInvalidRequestServerMessage(
                "The resources the player wants to add in storage are not present in the group " +
                    "of resources assigned to him"
            );
        }

        // check if the resources the player wants to add in storages meet the requirements of the storages
        // in which they are to be added
        Map<ResourceStorage, Map<ResourceType, Integer>> totalResourcesByStorage = Stream.concat(
            requestToValidate.resourcesToAddByStorage.entrySet().stream(),
            requestToValidate.starResourcesChosenToAddByStorage.entrySet().stream()
        ).collect(Collectors.groupingBy(
            Map.Entry::getKey,
            Collectors.reducing(new HashMap<>(), Map.Entry::getValue, ResourceUtils::sum))
        );
        for (ResourceStorage storage : totalResourcesByStorage.keySet()){
            if (!storage.canAddResources(totalResourcesByStorage.get(storage))){
                return createInvalidRequestServerMessage(
                    "The resources chosen by the player do not check the storage requirements " +
                        "and therefore cannot be added to the storage %s.",
                    storage
                );
            }
        }

        return Optional.empty();
    }
}

