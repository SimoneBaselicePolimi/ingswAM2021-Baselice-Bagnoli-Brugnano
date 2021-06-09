package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.ResourceUtils;

import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class DialogUtils {

    public static CompletableFuture<Void> onSelectedProductionDialog(
        ClientProductionRepresentation selectedProduction,
        ProductionsSelectionInfo prodInfo,
        CliClientManager clientManager
    ) {
        if(prodInfo.getAlreadySelectedProductions().contains(selectedProduction)) {
            clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerProductionAlreadyChosen");
            return CompletableFuture.failedFuture(new IllegalAccessException());
        } else {
            return checkAndRemoveResourcesCost(
                selectedProduction,
                prodInfo,
                clientManager
            );
        }

    }

    public static CompletableFuture<Map.Entry<ResourceType, Integer>> askPlayerForResourcesTypeAndNumber(
        CliClientManager clientManager,
        String customAskForResourceTypeAndNumberMessage
    ) {
        return clientManager.askUserLocalized(customAskForResourceTypeAndNumberMessage)
            .thenCompose(input -> {
                if(input.matches("\\G\\d+\\s+\\w+\\s*$")) {
                    int numOfResources = Integer.parseInt(input.split("\\s+")[0]);
                    ResourceType resourceType =
                        ResourceType.getResourceTypeFromLocalizedName(input.split("\\s+")[1]);
                    if (resourceType != null && numOfResources > 0)
                        return CompletableFuture.completedFuture(new AbstractMap.SimpleEntry<>(
                            resourceType,
                            numOfResources
                        ));
                }
                clientManager.tellUserLocalized("client.cli.resourcesChoiceDialog.invalidChoice");
                return askPlayerForResourcesTypeAndNumber(clientManager, customAskForResourceTypeAndNumberMessage);
            });
    }

    public static CompletableFuture<Map.Entry<ResourceType, Integer>> askPlayerForResourcesTypeAndNumber(
        CliClientManager clientManager
    ) {
        return askPlayerForResourcesTypeAndNumber(
            clientManager,
            Localization.getLocalizationInstance().getString(
                "client.cli.resourcesChoiceDialog.specifyResources"
            )
        );
    }

    public static CompletableFuture<Map.Entry<ResourceType, Integer>> askPlayerForResourcesTypeAndNumberLocalized(
        CliClientManager clientManager,
        String customAskForResourceTypeAndNumberMessagePlaceholder,
        Object... args
    ) {
        return askPlayerForResourcesTypeAndNumber(
            clientManager,
            Localization.getLocalizationInstance().getString(customAskForResourceTypeAndNumberMessagePlaceholder, args)
        );
    }




    static boolean checkIfThePlayerHasNecessaryResources(
        ClientProductionRepresentation production,
        ProductionsSelectionInfo prodInfo
    ) {
        int numOfStarResources = production.getStarResourceCost();
        int totalResourcesCost = production.getResourceCost().values().stream().reduce(0, Integer::sum);
        return
            (ResourceUtils.areResourcesAContainedInB(
                production.getResourceCost(),
                prodInfo.getResourcesLeftToThePlayer())
            ) && (
                totalResourcesCost + numOfStarResources >=
                    prodInfo.getResourcesLeftToThePlayer().values().stream().reduce(0, Integer::sum)
            );
    }

    static CompletableFuture<Void> checkAndRemoveResourcesCost(
        ClientProductionRepresentation production,
        ProductionsSelectionInfo prodInfo,
        CliClientManager clientManager
    ) {
        if (checkIfThePlayerHasNecessaryResources(production, prodInfo)) {
            prodInfo.setResourcesLeftToThePlayer(
                ResourceUtils.difference(prodInfo.getResourcesLeftToThePlayer(), production.getResourceCost())
            );
            return askPlayerForStarResourcesCost(
                production,
                production.getStarResourceCost(),
                prodInfo,
                clientManager
            );
        } else {
            clientManager.tellUserLocalized(
                "client.cli.playerDashboard.notifyPlayerHeDoesNotHaveNeededResources"
            );
            return CompletableFuture.failedFuture(new IllegalAccessException());
        }
    }

    static CompletableFuture<Void> askPlayerForStarResourcesCost(
        ClientProductionRepresentation production,
        int numOfStarResourcesLeftToPay,
        ProductionsSelectionInfo prodInfo,
        CliClientManager clientManager
    ) {

        if(numOfStarResourcesLeftToPay != 0){
            return DialogUtils.askPlayerForResourcesTypeAndNumberLocalized(
                clientManager,
                "client.cli.playerDashboard.notifyPlayerNumberOfResourcesLeftToPay",
                numOfStarResourcesLeftToPay
            ).thenCompose(resourceTypeAndNumEntry -> {

                ResourceType resourceType = resourceTypeAndNumEntry.getKey();
                int numOfResources = resourceTypeAndNumEntry.getValue();

                if (
                    numOfStarResourcesLeftToPay >= numOfResources &&
                    ResourceUtils.areResourcesAContainedInB(
                        Map.of(resourceType, numOfResources),
                        prodInfo.getResourcesLeftToThePlayer()
                    )
                ) {
                    prodInfo.setResourcesLeftToThePlayer(ResourceUtils.difference(
                        prodInfo.getResourcesLeftToThePlayer(),
                        Map.of(resourceType, numOfResources)
                    ));
                    prodInfo.setTotalStarResourcesProductionCost(ResourceUtils.sum(
                        prodInfo.getTotalStarResourcesProductionCost(),
                        Map.of(resourceType, numOfResources)
                    ));
                    return askPlayerForStarResourcesCost(
                        production,
                        numOfStarResourcesLeftToPay - numOfResources,
                        prodInfo,
                        clientManager
                    );
                } else {
                    clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerResourcesAreInvalid");
                    return askPlayerForStarResourcesCost(
                        production,
                        numOfStarResourcesLeftToPay,
                        prodInfo,
                        clientManager
                    );
                }

            });
        } else {
            return askPlayerForStarResourceReward(
                production,
                production.getStarResourceReward(),
                prodInfo,
                clientManager
            );
        }
    }

    static CompletableFuture<Void> askPlayerForStarResourceReward(
        ClientProductionRepresentation production,
        int numOfStarResourcesLeftToObtain,
        ProductionsSelectionInfo prodInfo,
        CliClientManager clientManager
    ) {

        if(numOfStarResourcesLeftToObtain != 0){
            return DialogUtils.askPlayerForResourcesTypeAndNumberLocalized(
                clientManager,
                "client.cli.playerDashboard.notifyPlayerNumberOfResourcesLeftToObtain",
                numOfStarResourcesLeftToObtain
            ).thenCompose(resourceTypeAndNumEntry -> {

                ResourceType resourceType = resourceTypeAndNumEntry.getKey();
                int numOfResources = resourceTypeAndNumEntry.getValue();

                    if(numOfStarResourcesLeftToObtain >= numOfResources) {
                        prodInfo.getTotalStarResourcesProductionReward().put(resourceType, numOfResources);
                        return askPlayerForStarResourceReward(
                            production,
                            numOfStarResourcesLeftToObtain - numOfResources,
                            prodInfo,
                            clientManager
                        );
                    } else {
                        clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerResourcesAreInvalid");
                        return askPlayerForStarResourceReward(
                            production,
                            numOfStarResourcesLeftToObtain,
                            prodInfo,
                            clientManager
                        );
                    }

            });
        } else {
            prodInfo.addNewSelectedProduction(production);
            return CompletableFuture.completedFuture(null);
        }

    }

}
