package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.DialogUtils;
import it.polimi.ingsw.client.cli.UserChoicesUtils;
import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientResourceStorageRepresentation;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.localization.LocalizationUtils;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.ResourceUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class ResourcesRepositioningDashboardView extends AbstractPlayerDashboardView{

    protected Map<ResourceType, Integer> resourcesInTemporaryStorage;
    protected Set<ClientResourceStorageRepresentation> storagesModified = new HashSet<>();
    protected BiConsumer<Set<ClientResourceStorageRepresentation>, Map<ResourceType, Integer>> onPositioningDone =
        (s, r) -> {};

    public ResourcesRepositioningDashboardView(
        Map<ResourceType, Integer> resourcesInTemporaryStorage,
        CliClientManager clientManager,
        GameView gameView,
        int rowSize,
        int columnSize
    ) {
        super(clientManager.getMyPlayer(), clientManager, gameView, rowSize, columnSize);
        this.resourcesInTemporaryStorage = resourcesInTemporaryStorage;

        arrangeResourcesDialog();
    }

    public ResourcesRepositioningDashboardView(
        Map<ResourceType, Integer> resourcesInTemporaryStorage,
        BiConsumer<Set<ClientResourceStorageRepresentation>, Map<ResourceType, Integer>> onPositioningDone,
        CliClientManager clientManager,
        GameView gameView,
        int rowSize,
        int columnSize
    ) {
        this(resourcesInTemporaryStorage, clientManager, gameView, rowSize, columnSize);
        this.onPositioningDone = onPositioningDone;
    }

    public ResourcesRepositioningDashboardView(
        Map<ResourceType, Integer> resourcesInTemporaryStorage,
        CliClientManager clientManager,
        GameView gameView
    ) {
        this(resourcesInTemporaryStorage, clientManager, gameView, 0, 0);
    }

    public ResourcesRepositioningDashboardView(
        Map<ResourceType, Integer> resourcesInTemporaryStorage,
        BiConsumer<Set<ClientResourceStorageRepresentation>, Map<ResourceType, Integer>> onPositioningDone,
        CliClientManager clientManager,
        GameView gameView
    ) {
        this(resourcesInTemporaryStorage, onPositioningDone, clientManager, gameView, 0, 0);
    }

    public void setOnPositioningDoneCallback(
        BiConsumer<Set<ClientResourceStorageRepresentation>, Map<ResourceType, Integer>> onPositioningDone
    ) {
        this.onPositioningDone = onPositioningDone;
    }

    protected void arrangeResourcesDialog() {
        if(resourcesInTemporaryStorage.values().stream().mapToInt(n -> n).sum() > 0)
            clientManager.tellUserLocalized(
                "client.cli.resourcesRepositioning.temporaryResourcesInfo",
                LocalizationUtils.getResourcesListAsCompactString(resourcesInTemporaryStorage)
            );
        else
            clientManager.tellUserLocalized(
                "client.cli.resourcesRepositioning.absentTemporaryResourcesInfo"
            );
        UserChoicesUtils.makeUserChoose(clientManager)
            .addUserChoiceLocalized(
                this::putResourcesInStorageDialog,
                "client.cli.resourcesRepositioning.putResourcesInStorage"
            ).addUserChoiceLocalized(
                this::takeResourcesFromStorageDialog,
                "client.cli.resourcesRepositioning.removeResourcesFromStorage"
            ).addUserChoiceLocalized(
                () -> onPositioningDone.accept(storagesModified, resourcesInTemporaryStorage),
                "client.cli.resourcesRepositioning.repositioningDone"
            ).apply();
    }

    protected void putResourcesInStorageDialog() {

        DialogUtils.askPlayerForResourcesTypeAndNumber(clientManager)
            .thenCompose(resourceTypeAndNumEntry -> {
                ResourceType resourceType = resourceTypeAndNumEntry.getKey();
                int numOfResources = resourceTypeAndNumEntry.getValue();
                UserChoicesUtils.PossibleUserChoices choices = UserChoicesUtils.makeUserChoose(clientManager);

                for (ClientResourceStorageRepresentation s : shelves) {
                    choices.addUserChoiceLocalized(
                        () -> {
                            s.setResources(ResourceUtils.sum(s.getResources(), Map.of(resourceType, numOfResources)));
                            resourcesInTemporaryStorage = ResourceUtils.difference(
                                resourcesInTemporaryStorage,
                                Map.of(resourceType, numOfResources)
                            );
                            storagesModified.add(s);
                            arrangeResourcesDialog();
                        },
                        "client.cli.resourcesRepositioning.specifyInWhichStorage",
                        Localization.getLocalizationInstance().getString("dashboard.shelf")
                            + (shelves.indexOf(s) + 1)
                    );
                }

                for(ClientResourceStorageRepresentation ls : leaderStoragesFromActiveCards) {
                    choices.addUserChoiceLocalized(
                        () -> {
                            ls.setResources(ResourceUtils.sum(ls.getResources(), Map.of(resourceType, numOfResources)));
                            resourcesInTemporaryStorage = ResourceUtils.difference(
                                resourcesInTemporaryStorage,
                                Map.of(resourceType, numOfResources)
                            );
                            storagesModified.add(ls);
                            arrangeResourcesDialog();
                        },
                        "client.cli.resourcesRepositioning.specifyInWhichStorage",
                        Localization.getLocalizationInstance().getString("dashboard.leaderStorages")
                            + (shelves.size() + leaderStoragesFromActiveCards.indexOf(ls) + 1)
                    );
                }

                choices.apply();
                return CompletableFuture.completedFuture(null);
        });
    }

    protected void takeResourcesFromStorageDialog() {

        DialogUtils.askPlayerForResourcesTypeAndNumber(clientManager)
            .thenCompose(resourceTypeAndNumEntry -> {
                ResourceType resourceType = resourceTypeAndNumEntry.getKey();
                int numOfResources = resourceTypeAndNumEntry.getValue();
                UserChoicesUtils.PossibleUserChoices choices = UserChoicesUtils.makeUserChoose(clientManager);

                for (ClientResourceStorageRepresentation s : shelves) {
                    choices.addUserChoiceLocalized(
                        () -> {
                            s.setResources(ResourceUtils.difference(s.getResources(), Map.of(resourceType, numOfResources)));
                            resourcesInTemporaryStorage = ResourceUtils.sum(
                                resourcesInTemporaryStorage,
                                Map.of(resourceType, numOfResources)
                            );
                            storagesModified.add(s);
                            arrangeResourcesDialog();
                        },
                        "client.cli.resourcesRepositioning.specifyInWhichStorage",
                        Localization.getLocalizationInstance().getString("dashboard.shelf")
                            + (shelves.indexOf(s) + 1)
                    );
                }

                for(ClientResourceStorageRepresentation ls : leaderStoragesFromActiveCards) {
                    choices.addUserChoiceLocalized(
                        () -> {
                            ls.setResources(ResourceUtils.difference(ls.getResources(), Map.of(resourceType, numOfResources)));
                            resourcesInTemporaryStorage = ResourceUtils.sum(
                                resourcesInTemporaryStorage,
                                Map.of(resourceType, numOfResources)
                            );
                            storagesModified.add(ls);
                            arrangeResourcesDialog();
                        },
                        "client.cli.resourcesRepositioning.specifyInWhichStorage",
                        Localization.getLocalizationInstance().getString("dashboard.leaderStorages")
                            + (shelves.size() + leaderStoragesFromActiveCards.indexOf(ls) + 1)
                    );
                }

                choices.apply();
                return CompletableFuture.completedFuture(null);
            });

    }
}
