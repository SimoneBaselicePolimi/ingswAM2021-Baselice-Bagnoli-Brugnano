package it.polimi.ingsw.server.model.notifier.gameupdate;

public class ServerTempStarResourcesUpdate extends ServerGameUpdate{

    public final int tempStarResources;

    public ServerTempStarResourcesUpdate(int tempStarResources) {
        this.tempStarResources = tempStarResources;
    }
}
