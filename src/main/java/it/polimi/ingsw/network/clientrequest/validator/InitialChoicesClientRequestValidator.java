package it.polimi.ingsw.network.clientrequest.validator;

import it.polimi.ingsw.configfile.GameInfoConfig;
import it.polimi.ingsw.network.clientrequest.InitialChoicesClientRequest;
import it.polimi.ingsw.network.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceUtils;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameSetupState;
import it.polimi.ingsw.server.model.storage.ResourceStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class InitialChoicesClientRequestValidator extends ClientRequestValidator <InitialChoicesClientRequest> {

    @Override
    public Optional<InvalidRequestServerMessage> getErrorMessage(
        InitialChoicesClientRequest requestToValidate,
        GameManager gameManager
    ) {
        GameInfoConfig gameInfo = gameManager.getGameRules().gameInfoConfig;
        int numberOfLeadersCardsGivenToThePlayer = gameInfo.gameSetup.numberOfLeadersCardsGivenToThePlayer;
        int numberOfLeadersCardsThePlayerKeeps = gameInfo.gameSetup.numberOfLeadersCardsThePlayerKeeps;

        int playerTurnOrder = gameManager.getGameContext().getPlayersTurnOrder().indexOf(requestToValidate.player) + 1;
        int numOfStarResourcesGivenToThePlayer = gameManager.getGameRules().gameInfoConfig.gameSetup.
            initialPlayerResourcesBasedOnPlayOrder.get(playerTurnOrder).starResources;

        // check if the player is trying to add a number of resources different from the number of star resources
        // assigned to him
        int numOfTotalResourcesInRequest = ResourceUtils.sumResources(requestToValidate.chosenResourcesToAdd.values()).values()
            .stream().mapToInt(e -> e).sum();
        if (numOfTotalResourcesInRequest != numOfStarResourcesGivenToThePlayer)
            return createInvalidRequestServerMessage(
                "The number of resources sent is invalid. The number of resources assigned to this " +
                    "player is: %s",
                numOfStarResourcesGivenToThePlayer
            );

        Set<ResourceStorage> validResourceStorages =
            gameManager.getGameContext().getPlayerContext(requestToValidate.player).getShelves();
        for(ResourceStorage storage : requestToValidate.chosenResourcesToAdd.keySet()) {

            // check if it is possible to add initial resources to the storages specified by the player (only shelves
            // are valid)
            if (!validResourceStorages.contains(storage))
                return createInvalidRequestServerMessage(
                    "Invalid request: the player cannot add initial resources to the storage with ID: %s. " +
                        "Valid storages for initial resources are: %s",
                    storage.getStorageID(),
                    validResourceStorages.stream()
                        .map(ResourceStorage::getStorageID)
                        .collect(Collectors.toList())
                );

            // check if adding the specified resources to this storage would violate a storage rule.
            if(!storage.canAddResources(requestToValidate.chosenResourcesToAdd.get(storage)))
                return createInvalidRequestServerMessage(
                    "Invalid request: it is not possible to add the specified resources to the storage with " +
                        "ID: %s. Resource storage rules violation.",
                    storage.getStorageID()
                );

        }

        // check if the number of card chosen by the player is what is expected.
        if (requestToValidate.leaderCardsChosenByThePlayer.size() != numberOfLeadersCardsThePlayerKeeps)
            return createInvalidRequestServerMessage(
                "Invalid request: the number of leader cards chosen by the player is different from the " +
                    "number specified in the rules for this game. Number of cards to chose: %s",
                numberOfLeadersCardsThePlayerKeeps
            );

        // check if the player choose a leader card that was not from the group of leader cards assigned to him.
        if (!leaderCardsGivenToThePlayers.get(requestToValidate.player).containsAll(requestToValidate.leaderCardsChosenByThePlayer))
            return createInvalidRequestServerMessage(
                "Invalid request: the player must chose from the group of leader cards assigned to him"
            );
        return Optional.empty();
    }
}
