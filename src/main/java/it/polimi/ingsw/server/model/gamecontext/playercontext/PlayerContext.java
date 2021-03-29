package it.polimi.ingsw.server.model.gamecontext.playercontext;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.MarbleColour;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.storage.ResourceStorage;

import java.util.*;

/**
 * The player context aggregates all the information relative to a specific Player.
 * The main components are:
 * <ul>
 *     <li> Leader cards the player chose at the begging of the game</li>
 *     <li> Development cards the player bought. Those cards are organized in card stacks with special rules (see
 *     {@link it.polimi.ingsw.server.model.gameitems.cardstack.PlayerOwnedDevelopmentCardStack}).</li>
 *     <li> Special shelves the player can use to store resources taken from the market </li>
 *     <li> An infinite chest that does not have a limit on the number of resources it can contain </li>
 * </ul>
 * <p>
 * The player context also offers some utility methods that aggregate together information obtained by multiple
 * subcomponents. For example the method {@link PlayerContext#getAllResources()} will return the combined resources
 * obtained from 3 different type of subcomponents: the shelves, leader cards with storage slots as special ability and
 * the infinite chest.
 */
public class PlayerContext {

	private final Player player;
	private Set<LeaderCard> leaderCardsPlayerOwns = new HashSet<>();

	/**
	 * Creates the player context associated to a specific player. At any moment after the beginning of the game there
	 * should be one and only one instance of this class for each player.
	 * @param player the player associated with this player context
	 */
	protected PlayerContext(Player player) {
		this.player = player;
	}

	/**
	 * This method will set the leader cards that a certain player owns.
	 * The method should be called only once at the beginning of the game when the player chooses which cards to keep.
	 *
	 * Note: there can not be multiple copies of a card with the same ID. This is enforced by using a Set for the
	 * parameter cards instead of a list (@see LeaderCard.equals())
	 * @param cards a set of cards to keep.
	 */
	public void setLeaderCards(Set<LeaderCard> cards) {
		leaderCardsPlayerOwns = new HashSet<>(cards);
	}

	public Set<LeaderCard> getLeaderCards() {
		return new HashSet<>(leaderCardsPlayerOwns);
	}

	public List<LeaderCard> getActiveLeaderCards() {
		return null;
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

	public Map<ResourceType, Integer> getAllResources() {
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
