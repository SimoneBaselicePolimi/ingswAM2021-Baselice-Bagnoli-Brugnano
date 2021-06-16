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

public class ResourcesRepositioningDashboardView extends AbstractPlayerDashboardView{

    protected final boolean playerCanLeaveResourcesInTempStorage;
    protected Map<ResourceType, Integer> resourcesInTemporaryStorage;
    protected Set<ClientResourceStorageRepresentation> storagesModified = new HashSet<>();
    protected BiConsumer<Set<ClientResourceStorageRepresentation>, Map<ResourceType, Integer>> onPositioningDone =
        (s, r) -> {};

    public ResourcesRepositioningDashboardView(
        Map<ResourceType, Integer> resourcesInTemporaryStorage,
        boolean playerCanLeaveResourcesInTempStorage,
        CliClientManager clientManager,
        GameView gameView,
        int rowSize,
        int columnSize
    ) {
        super(clientManager.getMyPlayer(), clientManager, gameView, rowSize, columnSize);
        this.resourcesInTemporaryStorage = resourcesInTemporaryStorage;
        this.playerCanLeaveResourcesInTempStorage = playerCanLeaveResourcesInTempStorage;

        arrangeResourcesDialog();
    }

    public ResourcesRepositioningDashboardView(
        Map<ResourceType, Integer> resourcesInTemporaryStorage,
        boolean playerCanLeaveResourcesInTempStorage,
        BiConsumer<Set<ClientResourceStorageRepresentation>, Map<ResourceType, Integer>> onPositioningDone,
        CliClientManager clientManager,
        GameView gameView,
        int rowSize,
        int columnSize
    ) {
        this(resourcesInTemporaryStorage,
            playerCanLeaveResourcesInTempStorage,
            clientManager, gameView, rowSize, columnSize);
        this.onPositioningDone = onPositioningDone;
    }

    public ResourcesRepositioningDashboardView(
        Map<ResourceType, Integer> resourcesInTemporaryStorage,
        boolean playerCanLeaveResourcesInTempStorage,
        CliClientManager clientManager,
        GameView gameView
    ) {
        this(
            resourcesInTemporaryStorage,
            playerCanLeaveResourcesInTempStorage,
            clientManager,
            gameView,
            0,
            0
        );
    }

    public ResourcesRepositioningDashboardView(
        Map<ResourceType, Integer> resourcesInTemporaryStorage,
        BiConsumer<Set<ClientResourceStorageRepresentation>, Map<ResourceType, Integer>> onPositioningDone,
        CliClientManager clientManager,
        GameView gameView,
        boolean playerCanLeaveResourcesInTempStorage
    ) {
        this(resourcesInTemporaryStorage,
            playerCanLeaveResourcesInTempStorage,
            onPositioningDone, clientManager, gameView, 0, 0);
    }

    public void setOnPositioningDoneCallback(
        BiConsumer<Set<ClientResourceStorageRepresentation>, Map<ResourceType, Integer>> onPositioningDone
    ) {
        this.onPositioningDone = onPositioningDone;
    }

    protected void arrangeResourcesDialog() {
        UserChoicesUtils.PossibleUserChoices choices = UserChoicesUtils.makeUserChoose(clientManager);

        if(resourcesInTemporaryStorage.values().stream().mapToInt(n -> n).sum() > 0) {
            clientManager.tellUserLocalized(
                "client.cli.resourcesRepositioning.temporaryResourcesInfo",
                LocalizationUtils.getResourcesListAsCompactString(resourcesInTemporaryStorage)
            );
            choices.addUserChoiceLocalized(
                this::putResourcesInStorageDialog,
                "client.cli.resourcesRepositioning.putResourcesInStorage"
            );
        } else {
            clientManager.tellUserLocalized(
                "client.cli.resourcesRepositioning.absentTemporaryResourcesInfo"
            );
        }

        choices.addUserChoiceLocalized(
            this::takeResourcesFromStorageDialog,
            "client.cli.resourcesRepositioning.removeResourcesFromStorage"
        );

        if(
            playerCanLeaveResourcesInTempStorage ||
            resourcesInTemporaryStorage.values().stream().mapToInt(n -> n).sum() == 0
        ) {
           choices.addUserChoiceLocalized(
                () -> onPositioningDone.accept(storagesModified, resourcesInTemporaryStorage),
                "client.cli.resourcesRepositioning.repositioningDone"
            );
        }

        choices.apply();

    }

    protected void putResourcesInStorageDialog() {

        DialogUtils.askPlayerForResourcesTypeAndNumber(clientManager)
            .thenCompose(resourceTypeAndNumEntry -> {
                ResourceType resourceType = resourceTypeAndNumEntry.getKey();
                int numOfResources = resourceTypeAndNumEntry.getValue();
                if(!ResourceUtils.areResourcesAContainedInB(
                    Map.of(resourceType, numOfResources),
                    resourcesInTemporaryStorage
                )) {
                    clientManager.tellUserLocalized(
                        "client.cli.resourcesRepositioning.notEnoughResourcesInTempStorage"
                    );
                    arrangeResourcesDialog();
                    return CompletableFuture.completedFuture(null);
                }

                UserChoicesUtils.PossibleUserChoices choices = UserChoicesUtils.makeUserChoose(clientManager);

                for (ClientResourceStorageRepresentation s : shelves) {
                    choices.addUserChoiceLocalized(
                        () -> {
                            if(s.getRuleErrorMessagesIfPresent(Map.of(resourceType, numOfResources)).isPresent()) {
                                clientManager.tellUser(
                                    s.getRuleErrorMessagesIfPresent(Map.of(resourceType, numOfResources)).get()
                                );
                            }
                            else {
                                putResourceInStorage(resourceType, numOfResources, s);
                            }
                            arrangeResourcesDialog();
                        },
                        "client.cli.resourcesRepositioning.specifyInWhichStorage",
                        Localization.getLocalizationInstance().getString("dashboard.shelf")
                            + " " + (shelves.indexOf(s) + 1)
                    );
                }

                for(ClientResourceStorageRepresentation ls : leaderStoragesFromActiveCards) {
                    choices.addUserChoiceLocalized(
                        () -> {
                            if(ls.getRuleErrorMessagesIfPresent(Map.of(resourceType, numOfResources)).isPresent()) {
                                clientManager.tellUser(
                                    ls.getRuleErrorMessagesIfPresent(Map.of(resourceType, numOfResources)).get()
                                );
                            }
                            else {
                                putResourceInStorage(resourceType, numOfResources, ls);
                            }                            arrangeResourcesDialog();
                        },
                        "client.cli.resourcesRepositioning.specifyInWhichStorage",
                        Localization.getLocalizationInstance().getString("dashboard.leaderStorages")
                            + " " + (shelves.size() + leaderStoragesFromActiveCards.indexOf(ls) + 1)
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
                            takeResourcesFromStorage(resourceType, numOfResources, s);
                            arrangeResourcesDialog();
                        },
                        "client.cli.resourcesRepositioning.specifyForWhichStorage",
                        Localization.getLocalizationInstance().getString("dashboard.shelf")
                            + " " + (shelves.indexOf(s) + 1)
                    );
                }

                for(ClientResourceStorageRepresentation ls : leaderStoragesFromActiveCards) {
                    choices.addUserChoiceLocalized(
                        () -> {
                            takeResourcesFromStorage(resourceType, numOfResources, ls);
                            arrangeResourcesDialog();
                        },
                        "client.cli.resourcesRepositioning.specifyForWhichStorage",
                        Localization.getLocalizationInstance().getString("dashboard.leaderStorages")
                            + " " + (shelves.size() + leaderStoragesFromActiveCards.indexOf(ls) + 1)
                    );
                }

                choices.apply();
                return CompletableFuture.completedFuture(null);
            });
    }

    protected void putResourceInStorage(
        ResourceType resourceType,
        int numOfResources,
        ClientResourceStorageRepresentation storage
    ) {
        Map<ResourceType,Integer> newResources= Map.of(resourceType, numOfResources);
        storage.getRules().forEach(r -> r.getErrorMessageIfPresent(storage, newResources));
        storage.setResources(ResourceUtils.sum(storage.getResources(), newResources));
        resourcesInTemporaryStorage = ResourceUtils.difference(
            resourcesInTemporaryStorage,
            newResources
        );
        storagesModified.add(storage);
    }

    protected void takeResourcesFromStorage(
        ResourceType resourceType,
        int numOfResources,
        ClientResourceStorageRepresentation storage
    ) {
        Map<ResourceType,Integer> resourcesFromStorage= Map.of(resourceType, numOfResources);
        if(!ResourceUtils.areResourcesAContainedInB(resourcesFromStorage, storage.getResources())) {
            clientManager.tellUserLocalized(
                "client.cli.resourcesRepositioning.notEnoughResourcesInStorage"
            );
        }
        else {
            storage.setResources(ResourceUtils.difference(storage.getResources(), resourcesFromStorage));
            resourcesInTemporaryStorage = ResourceUtils.sum(resourcesInTemporaryStorage, resourcesFromStorage);
            storagesModified.add(storage);
        }
    }

}
