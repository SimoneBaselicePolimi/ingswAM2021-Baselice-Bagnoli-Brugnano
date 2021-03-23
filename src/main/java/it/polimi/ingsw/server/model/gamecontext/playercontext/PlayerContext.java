package it.polimi.ingsw.server.model.gamecontext.playercontext;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.MarbleColour;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.storage.ResourceStorage;

import java.util.List;
import java.util.Map;

public class PlayerContext {

	private Player player;

	public void setLeaderCards(List<LeaderCard> cards) {

	}

	public List<LeaderCard> getLeaderCards() {
		return null;
	}

	public void activeLeaderCard(DevelopmentCard card) {

	}

	public void discardLeaderCard(DevelopmentCard card) {

	}

	public Map<ResourceType, Integer> getDiscounts() {
		return null;
	}

	public List<MarbleColour> getWhiteMarblesMarketSubstitutions() {
		return null;
	}

	public List<ResourceStorage> getLeaderStorage() {
		return null;
	}

	public List<ResourceStorage> getShelves() {
		return null;
	}

	public List<ResourceStorage> getStoragesForResourcesFromMarket() {
		return null;
	}

	public ResourceStorage getInfiniteChest() {
		return null;
	}

	public ResourceStorage getTemporaryStorage() {
		return null;
	}

	public void setTemporaryStorageResources(Map<ResourceType, Integer> resources) {

	}

	public Map<ResourceType, Integer> clearTemporaryStorageResources() {
		return null;
	}

	public List<ResourceStorage> getAllResourceStorages() {
		return null;
	}

	public List<ResourceType> getAllResources() {
		return null;
	}

	public void addDevelopmentCard(DevelopmentCard card, int stackNumber) {

	}

	public List<DevelopmentCard> getAllDevelopmentCards() {
		return null;
	}

	public List<DevelopmentCard> getDevelopmentCardsOnTop() {
		return null;
	}

}
