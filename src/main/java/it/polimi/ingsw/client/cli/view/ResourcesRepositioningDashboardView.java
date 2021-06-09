package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.DialogUtils;
import it.polimi.ingsw.client.cli.UserChoicesUtils;
import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientResourceStorageRepresentation;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.ResourceUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

//TODO
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

//        DialogUtils.askPlayerForResourcesTypeAndNumber(clientManager)
//            .thenCompose(resourceTypeAndNumEntry -> {
//                ResourceType resourceType = resourceTypeAndNumEntry.getKey();
//                int numOfResources = resourceTypeAndNumEntry.getValue();
//                UserChoicesUtils.PossibleUserChoices choices = UserChoicesUtils.makeUserChoose();
//
//                for (ClientResourceStorageRepresentation s : ?) {
//                    choices.addUserChoiceLocalized(
//                        () -> {
//                            s.setResources(ResourceUtils.sum(s.getResources(), Map.of(resourceType, numOfResources)));
//                            arrangeResourcesDialog();
//                        },
//                        "client.cli.resourcesRepositioning.specifyInWhichStorage",
//                        NOME???
//                    )
//                }
//
//                choices.apply();
//        }

    }

    protected void takeResourcesFromStorageDialog() {

    }
}
