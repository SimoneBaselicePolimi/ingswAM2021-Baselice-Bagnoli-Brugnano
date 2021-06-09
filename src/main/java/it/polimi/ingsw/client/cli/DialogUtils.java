package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.ResourceUtils;

import java.util.HashMap;
import java.util.List;
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
            return clientManager.askUserLocalized("client.cli.playerDashboard.notifyPlayerNumberOfResourcesLeftToPay", numOfStarResourcesLeftToPay)
                .thenCompose(resources -> {

                    if(resources.matches("\\G\\d+\\s+\\w+\\s*$")) {
                        int numOfResources = Integer.parseInt(resources.split("\\s+")[0]);
                        ResourceType resourceType =
                            ResourceType.getResourceTypeFromLocalizedName(resources.split("\\s+")[1]);
                        if (
                            resourceType != null &&
                                numOfResources > 0 &&
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
                        }

                    }

                    clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerResourcesAreInvalid");
                    return askPlayerForStarResourcesCost(
                        production,
                        numOfStarResourcesLeftToPay,
                        prodInfo,
                        clientManager
                    );

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
            return clientManager.askUserLocalized("client.cli.playerDashboard.notifyPlayerNumberOfResourcesLeftToObtain", numOfStarResourcesLeftToObtain)
                .thenCompose(resources -> {

                    if(resources.matches("\\G\\d+\\s+\\w+\\s*$")) {
                        int numOfResources = Integer.parseInt(resources.split("\\s+")[0]);
                        ResourceType resourceType = ResourceType.getResourceTypeFromLocalizedName(resources.split("\\s+")[1]);
                        if(resourceType != null && numOfResources > 0 && numOfStarResourcesLeftToObtain >= numOfResources) {
                            prodInfo.getTotalStarResourcesProductionReward().put(resourceType, numOfResources);
                            return askPlayerForStarResourceReward(
                                production,
                                numOfStarResourcesLeftToObtain - numOfResources,
                                prodInfo,
                                clientManager
                            );
                        }
                    }

                    clientManager.tellUserLocalized("client.cli.playerDashboard.notifyPlayerResourcesAreInvalid");
                    return askPlayerForStarResourceReward(
                        production,
                        numOfStarResourcesLeftToObtain,
                        prodInfo,
                        clientManager
                    );

                });
        } else {
            prodInfo.addNewSelectedProduction(production);
            return CompletableFuture.completedFuture(null);
        }

    }

}
