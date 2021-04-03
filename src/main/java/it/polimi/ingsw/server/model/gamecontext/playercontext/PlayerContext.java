package it.polimi.ingsw.server.model.gamecontext.playercontext;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.*;
import it.polimi.ingsw.server.model.gameitems.cardstack.CannotPushCardOnTopException;
import it.polimi.ingsw.server.model.gameitems.cardstack.PlayerOwnedDevelopmentCardStack;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.model.storage.ResourceStorageBuilder;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The player context aggregates all the information relative to a specific Player.
 * The main components are:
 * <ul>
 *     <li> Leader cards the player chose at the begging of the game</li>
 *     <li> Development cards the player bought. Those cards are organized in card stacks with special rules (see
 *     {@link it.polimi.ingsw.server.model.gameitems.cardstack.PlayerOwnedDevelopmentCardStack}).</li>
 *     <li> Shelves, special storages the player can use to store resources taken from the market </li>
 *     <li> An infinite chest is a storage that does not have a limit on the number of resources it can contain </li>
 *     <li> A temporary storage used only to hold resources obtained from the market before the player repositions them
 *     in the shelves and/or in the leader card storages </li>
 * </ul>
 * <p>
 * The player context also offers some utility methods that aggregate together information obtained by multiple
 * subcomponents. For example the method {@link PlayerContext#getAllResources()} will return the combined resources
 * obtained from 3 different type of subcomponents: the shelves, leader cards with storage slots as special ability and
 * the infinite chest.
 */
public class PlayerContext {

	private final Player player;
	private Set<ResourceStorage> shelves;
	private ResourceStorage infiniteChest;
	private ResourceStorage tempStorage;
	private Set<LeaderCard> leaderCardsPlayerOwns = new HashSet<>();
	private List<PlayerOwnedDevelopmentCardStack> developmentCardDecks = new ArrayList<>();

	/**
	 * Creates the player context associated to a specific player. At any moment after the beginning of the game there
	 * should be one and only one instance of this class for each player.
	 * <p>
	 * Note: this constructor is marked as protected because this class should only ever be initialized by the
	 *  {@link it.polimi.ingsw.server.model.gamecontext.GameContextBuilder} and thus the constructor should never be
	 *  called from outside this package
	 * @param player the player associated with this player context
	 * @param numberOfDevelopmentCardDecks number of decks of development cards the player bought
	 * @param shelves shelves the player can use to store resources taken from the market
	 */
	protected PlayerContext(Player player, int numberOfDevelopmentCardDecks, Set<ResourceStorage> shelves) {
		this.player = player;
		this.shelves = new HashSet<>(shelves);
		infiniteChest = ResourceStorageBuilder.initResourceStorageBuilder().createResourceStorage();
		tempStorage = ResourceStorageBuilder.initResourceStorageBuilder().createResourceStorage();
		for (int i = 0; i < numberOfDevelopmentCardDecks; i++) {
			developmentCardDecks.add(new PlayerOwnedDevelopmentCardStack(new ArrayList<>()));
		}
	}

	/**
	 * This constructor should be used only for testing.
	 * Creates the player context associated to a specific player. At any moment after the beginning of the game there
	 * should be one and only one instance of this class for each player.
	 * @param player the player associated with this player context
	 * @param shelves shelves the player can use to store resources taken from the market
	 * @param decks decks (dependency injection)
	 * @param infiniteChest infinite chest (dependency injection)
	 * @param temporaryStorage temporaryStorage (dependency injection)
	 */
	protected PlayerContext(
			Player player,
			Set<ResourceStorage> shelves,
			List<PlayerOwnedDevelopmentCardStack> decks,
			ResourceStorage infiniteChest,
			ResourceStorage temporaryStorage
	) {
		this.player = player;
		this.shelves = new HashSet<>(shelves);
		this.infiniteChest = infiniteChest;
		this.tempStorage = temporaryStorage;
		this.developmentCardDecks = new ArrayList<>(decks);
	}


	/**
	 * This method will set the leader cards that a certain player owns.
	 * The method should be called only once at the beginning of the game when the player chooses which cards to keep.
	 *
	 * Note: there can not be multiple copies of a card with the same ID. This is enforced by using a Set for the
	 * parameter `cards` instead of a list (@see LeaderCard.equals())
	 * @param cards a set of cards to keep.
	 */
	public void setLeaderCards(Set<LeaderCard> cards) {
		leaderCardsPlayerOwns = new HashSet<>(cards);
	}

	/**
	 * @return the leader cards owned by the player
	 */
	public Set<LeaderCard> getLeaderCards() {
		return new HashSet<>(leaderCardsPlayerOwns);
	}

	/**
	 * @return the leader cards owned by the player that have been activated
	 */
	public Set<LeaderCard> getActiveLeaderCards() {
		return leaderCardsPlayerOwns.stream()
				.filter(leaderCard -> leaderCard.getState() == LeaderCardState.ACTIVE)
				.collect(Collectors.toSet());
	}

	/**
     * After it has been activated, a leader card may award the player with one (or more, in games with custom rules)
	 * development card discounts. This method returns the sum of all the discounts specified in active leader cards.
	 * Note: only active leader card will be considered
	 * @return the sum of all the discounts that will be applied when the player buys a new development card.
	 */
	public Map<ResourceType, Integer> getActiveLeaderCardsDiscounts() {
		return getActiveLeaderCards().stream()
				.flatMap(leaderCard -> leaderCard.getDevelopmentCardCostDiscount().stream())
				.collect(Collectors.groupingBy(
						DevelopmentCardCostDiscount::getResourceTypeToDiscount,
						Collectors.summingInt(DevelopmentCardCostDiscount::getAmountToDiscount)
				));
	}

	/**
	 * After it has been activated, a leader card may award the player with the ability to exchange a "special marble"
	 * for a resource of the type specified by the leader card (in games with custom rules a leader card may give the
	 * player the possibility to choose from multiple possible substitutions).
	 * <p>
	 * Note1: only active leader card will be considered
	 * Note2: white marbles are one example of "special marbles" (when playing with the standard rules)."
	 * @return this methods returns a set of all the possible resource types the player can choose from when
	 * substituting a "special marble".
	 */
	public Set<ResourceType> getActiveLeaderCardsWhiteMarblesMarketSubstitutions() {
		return getActiveLeaderCards().stream()
				.flatMap(leaderCard -> leaderCard.getWhiteMarbleSubstitutions().stream())
				.map(WhiteMarbleSubstitution::getResourceTypeToSubstitute)
				.collect(Collectors.toSet());
	}

	/**
	 * After it has been activated, a leader card may award the player with one (or more, in games with custom rules)
	 * special resource storages. Those special resource storages can be used (like the shelves) to store resources
	 * obtained from the market.
	 * <p>
	 * Note: only active leader card will be considered
	 * @return a set of all the resource storages from leader cards
	 */
	public Set<ResourceStorage> getActiveLeaderCardsResourceStorages() {
		return getActiveLeaderCards().stream()
				.flatMap(leaderCard -> leaderCard.getResourceStorages().stream())
				.collect(Collectors.toSet());
	}

	/**
	 * After it has been activated, a leader card may award the player with one (or more, in games with custom rules)
	 * productions that can be activated when executing an "Activate production" game action.
	 * <p>
	 * Note1: only active leader card will be considered
	 * Note2: the player may not be able to use some of the productions returned by this method because he
	 * may not have all the resources needed.
	 * @return a set of all the productions from leader cards. Some of the production returned may not act
	 */
	public Set<Production> getActiveLeaderCardsProductions() {
		return getActiveLeaderCards().stream()
				.flatMap(leaderCard -> leaderCard.getProductions().stream())
				.collect(Collectors.toSet());
	}

	/**
	 * The shelves are resource storages that can always be used to store resource from the market. When playing with
	 * the standard rule every shelve will have a different limit on the number of resources it can contain.
	 * @return the shelves
	 */
	public Set<ResourceStorage> getShelves() {
		return new HashSet<>(shelves);
	}

	/**
	 * Shelves and special leader card storages can store resources obtained from the market.
     * <p>
	 * Note1: will only return the leader card resource storages defined in `ACTIVE` leader cards.
	 * Note2: the temporary resource storage is NOT included
	 * @return returns the active resource storages that can hold resources obtained from the market
	 */
	public Set<ResourceStorage> getResourceStoragesForResourcesFromMarket() {
		return Stream.concat(shelves.stream(), getActiveLeaderCardsResourceStorages().stream()).collect(Collectors.toSet());
	}

	/**
	 * The infinite chest is a chest that does not have a limit on the number of resources it can contain. This special
	 * storage can NOT be used to store resources from the market.
	 * @return the infinite chest
	 */
	public ResourceStorage getInfiniteChest() {
		return infiniteChest;
	}

	/**
	 * @return a temporary storage used only to hold resources obtained from the market before the player repositions
	 * them in the shelves and/or in the leader card storages
	 */
	public ResourceStorage getTemporaryStorage() {
		return tempStorage;
	}


	/**
	 * This method should be used to put the resources taken from the market into the temporary storage.
	 * <p>
	 * Note: this method is a setter, if there were resources into this temporary storage when this methods gets called
	 * they will be overwritten.
	 * @param resources resources to put into the temporary storage
	 */
	public void setTemporaryStorageResources(Map<ResourceType, Integer> resources) {

	}

	/**
	 * Utility method, same as PlayerContext.getTemporaryStorage().peekResources()
	 * @return returns the resources in the temporary storage
	 */
	public Map<ResourceType, Integer> getTemporaryStorageResources() {
		return tempStorage.peekResources();
	}

	/**
	 * This method will empty the temporary storage. It should be called when the player moves the resource to
	 * the shelves/leader card storages.
     * @return resources that were in the temporary storage
	 */
	public Map<ResourceType, Integer> clearTemporaryStorageResources() {
		return null;
	}

	/**
     * All resource storages means shelves + leader card storages + infinite chest
	 * <p>
	 * Note1: will only return the leader card resource storages defined in `ACTIVE` leader cards.
	 * Note2: the temporary resource storage is NOT included
	 * @return returns all the active resource storages
	 */
	public Set<ResourceStorage> getAllResourceStorages() {
	    return Stream.concat(
	    		getResourceStoragesForResourcesFromMarket().stream(),
				Stream.of(infiniteChest)
		).collect(Collectors.toSet());
	}

	/**
	 * Get all resources contained in any active resource storage
	 * @return returns all resources a player owns.
	 * @see PlayerContext#getAllResourceStorages()
	 */
	public Map<ResourceType, Integer> getAllResources() {
		return getAllResourceStorages().stream()
				.flatMap(resourceStorage -> resourceStorage.peekResources().entrySet().stream())
				.collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingInt(Map.Entry::getValue)));
	}

	/**
	 * This method allows to get one of the decks of development cards owned by the player.
	 * <p>
	 * Note2: when playing with the standard rules there will be 3 decks of development cards for every player. (Those
	 * deck will be empty when the game starts)
	 * @param deckNumber the number of the deck that will be returned
	 * @return returns the deck with the specified deckNumber
	 * @throws IllegalArgumentException if there is no deck with that deckNumber. (if deckNumber >= numberOfDecks)
	 * @see it.polimi.ingsw.server.model.gameitems.cardstack.PlayerOwnedDevelopmentCardStack
	 */
	public PlayerOwnedDevelopmentCardStack getDeck(int deckNumber) throws IllegalArgumentException {
		if(deckNumber >= developmentCardDecks.size() || deckNumber < 0)
			throw new IllegalArgumentException(String.format(
					"deckNumber %d is not valid. The range of valid deck numbers for this game is 0 to %d",
					deckNumber,
					developmentCardDecks.size()
			));
		return developmentCardDecks.get(deckNumber);
	}

	/**
	 * This method allow to add a new development card on top of one of the decks of development cards owned by the
	 * player. This method should be called when a player buys a development card
	 * <p>
	 * Note1: you can only add a card with level N on top of a card on level N - 1.
	 * Note2: when playing with the standard rules there will be 3 decks of development cards for every player. (Those
	 * deck will be empty when the game starts)
	 * @param card development card to add.
	 * @param deckNumber the number of the deck to add the development card on top
	 * @throws IllegalArgumentException if there is no deck with that deckNumber. (if deckNumber >= numberOfDecks)
	 * @throws CannotPushCardOnTopException if you try to add a development card of level N on top of a card of level
	 * that is not N - 1. (You can add card of level 1 only on empty decks)
	 * @see it.polimi.ingsw.server.model.gameitems.cardstack.PlayerOwnedDevelopmentCardStack
	 * @see it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel
	 */
	public void addDevelopmentCard(DevelopmentCard card, int deckNumber) throws IllegalArgumentException, CannotPushCardOnTopException {
		getDeck(deckNumber).pushOnTop(card);
	}

	/**
	 * @return returns all the development cards owned by the player
	 */
	public Set<DevelopmentCard> getAllDevelopmentCards() {
		return developmentCardDecks.stream().flatMap(deck -> deck.peekAll().stream()).collect(Collectors.toSet());
	}

	/**
	 * Only the productions of development card that are currently on top of their deck can be activated
	 * @return returns all the development cards owned by the player that are on top of the relative deck
	 */
	public Set<DevelopmentCard> getDevelopmentCardsOnTop() {
		return developmentCardDecks.stream().map(PlayerOwnedDevelopmentCardStack::peek).collect(Collectors.toSet());
	}

}
