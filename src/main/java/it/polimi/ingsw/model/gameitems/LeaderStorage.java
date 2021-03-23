package it.polimi.ingsw.model.gameitems;

import it.polimi.ingsw.model.storage.ResourceStorage;

import java.util.List;

public interface LeaderStorage {

	List<ResourceStorage> getLeaderStorage();

	boolean isLeaderStorageActive();

}
