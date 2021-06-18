package it.polimi.ingsw.server.controller.clientrequest.validator;

import it.polimi.ingsw.server.controller.clientrequest.ManageResourcesFromMarketClientRequest;
import it.polimi.ingsw.server.controller.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.ResourceUtils;
import it.polimi.ingsw.server.model.gamemanager.GameManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
        //TODO
        PlayerContext playerContext = gameManager.getGameContext().getPlayerContext(requestToValidate.player);
        Set<ResourceType> possibleStarMarbleSubstitution = playerContext
            .getActiveLeaderCardsWhiteMarblesMarketSubstitutions();

        Map<ResourceType, Integer> deltaResources = requestToValidate.resourcesInModifiedStorages.entrySet().stream()
            .map(entry -> {
                    Map<ResourceType, Integer> oldResources = entry.getKey().peekResources();
                    Map<ResourceType, Integer> newResources = entry.getValue();
                    return ResourceUtils.relativeDifference(newResources, oldResources);
                }
            ).reduce(new HashMap<>(), ResourceUtils::sum);

        Map<ResourceType, Integer> starResourcesChosen = ResourceUtils.difference(
            ResourceUtils.sum(deltaResources, requestToValidate.resourcesLeftInTemporaryStorage),
            playerContext.getTemporaryStorageResources()
        );

        // check if star resources chosen by the player are from those possible to choose
            for (ResourceType resourceType : starResourcesChosen.keySet()){
                if (!possibleStarMarbleSubstitution.contains(resourceType)) {
                    return createInvalidRequestServerMessage(
                        "The resource type chosen by the player as star resources substitution " +
                            "is not present among those provided by the special abilities of the active leader cards"
                    );
                }
            }

        // check if the number of star resources is the same as what is expected
        if (starResourcesChosen.size() != playerContext.getTempStarResources()
            && !possibleStarMarbleSubstitution.isEmpty())
            return createInvalidRequestServerMessage(
                "The number of star resources chosen by the player is not the same of the one required. " +
                    "The number of star resources required is %s",
                playerContext.getTempStarResources()
            );

//        // check if the resources the player wants to add in storages are the same as those assigned to him
//        Map<ResourceType, Integer> sumOfResourcesFromThePlayer = ResourceUtils.sum(
//            ResourceUtils.sum(requestToValidate.resourcesInModifiedStorages.values()),
//            requestToValidate.resourcesLeftInTemporaryStorage
//        );
//        if(!sumOfResourcesFromThePlayer.equals(playerContext.getTemporaryStorageResources())){
//            return createInvalidRequestServerMessage(
//                "The resources the player wants to add in storage are not present in the group " +
//                    "of resources assigned to him"
//            );
//        }
//
//        // check if the resources the player wants to add in storages meet the requirements of the storages
//        // in which they are to be added
//        Map<ResourceStorage, Map<ResourceType, Integer>> totalResourcesByStorage = Stream.concat(
//            requestToValidate.resourcesInModifiedStorages.entrySet().stream(),
//            requestToValidate.starResourcesChosenToAddByStorage.entrySet().stream()
//        ).collect(Collectors.groupingBy(
//            Map.Entry::getKey,
//            Collectors.reducing(new HashMap<>(), Map.Entry::getValue, ResourceUtils::sum))
//        );
//        for (ResourceStorage storage : totalResourcesByStorage.keySet()){
//            if (!storage.canAddResources(totalResourcesByStorage.get(storage))){
//                return createInvalidRequestServerMessage(
//                    "The resources chosen by the player do not check the storage requirements " +
//                        "and therefore cannot be added to the storage %s.",
//                    storage
//                );
//            }
//        }

        return Optional.empty();
    }
}

