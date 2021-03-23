package it.polimi.ingsw.server.model.gameitems;

import it.polimi.ingsw.server.model.storage.ResourceStorage;

import java.util.List;

public interface LeaderStorage {

	List<ResourceStorage> getLeaderStorage();

	boolean isLeaderStorageActive();

}
