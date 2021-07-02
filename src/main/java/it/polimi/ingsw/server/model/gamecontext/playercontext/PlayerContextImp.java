package it.polimi.ingsw.server.model.gamecontext.playercontext;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Representable;
import it.polimi.ingsw.server.model.gameitems.*;
import it.polimi.ingsw.server.model.gameitems.cardstack.ForbiddenPushOnTopException;
import it.polimi.ingsw.server.model.gameitems.cardstack.PlayerOwnedDevelopmentCardDeck;
import it.polimi.ingsw.server.model.gameitems.cardstack.PlayerOwnedDevelopmentCardDeckImp;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ServerPlayerContextRepresentation;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The player context aggregates all the information relative to a specific Player.
 * The main components are:
 * <ul>
 *     <li> Leader cards the player chose at the beginning of the game</li>
 *     <li> Development cards the player bought. Those cards are organized in card stacks with special rules (see
 *     {@link PlayerOwnedDevelopmentCardDeckImp}).</li>
 *     <li> Shelves, special storages the player can use to store resources taken from the market </li>
 *     <li> An infinite chest is a storage that does not have a limit on the number of resources it can contain </li>
 *     <li> A temporary storage used only to hold resources obtained from the market before the player repositions them
 *     in the shelves and/or in the leader card storages </li>
 * </ul>
 * <p>
 * The player context also offers some utility methods that aggregate together information obtained by multiple
 * subcomponents. For example the method {@link PlayerContextImp#getAllResources()} will return the combined resources
 * obtained from 3 different type of subcomponents: the shelves, leader cards with storage slots as special ability and
 * the infinite chest.
 */
public class PlayerContextImp implements PlayerContext {

	private final Player player;
	private Set<ResourceStorage> shelves;
	private ResourceStorage infiniteChest;
	private ResourceStorage tempStorage;
	private int tempStarResources;
	private Set<LeaderCard> leaderCardsPlayerOwns = new HashSet<>();
	private List<PlayerOwnedDevelopmentCardDeck> developmentCardDecks;
	private final Set<Production> baseProductions;

	/**
	 * Creates the player context associated to a specific player. At any moment after the beginning of the game there
	 * should be one and only one instance of this class for each player.
	 * <p>
	 * Note: this constructor is marked as protected because this class should only ever be initialized by the
	 *  {@link it.polimi.ingsw.server.model.gamecontext.GameContextBuilder} and thus the constructor should never be
	 * 	called from outside this package
	 * @param player the player associated with this player context
	 * @param shelves shelves the player can use to store resources taken from the market
	 * @param decks decks (dependency injection)
	 * @param infiniteChest infinite chest (dependency injection)
	 * @param temporaryStorage temporaryStorage (dependency injection)
	 * @param baseProductions
	 */
	public PlayerContextImp(
		Player player,
		Set<ResourceStorage> shelves,
		List<PlayerOwnedDevelopmentCardDeck> decks,
		ResourceStorage infiniteChest,
		ResourceStorage temporaryStorage,
		Set<Production> baseProductions
	) {
		this.player = player;
		this.shelves = new HashSet<>(shelves);
		this.infiniteChest = infiniteChest;
		this.tempStorage = temporaryStorage;
		this.developmentCardDecks = new ArrayList<>(decks);
		this.baseProductions = new HashSet<>(baseProductions);
	}


	/**
	 * This method will set the leader cards that a certain player owns.
	 * The method should be called only once at the beginning of the game when the player chooses which cards to keep.
	 *
	 * Note: there can not be multiple copies of a card with the same ID. This is enforced by using a Set for the
	 * parameter `cards` instead of a list (@see LeaderCard.equals())
	 * @param cards a set of cards to keep.
	 */
	@Override
	public void setLeaderCards(Set<LeaderCard> cards) {
		leaderCardsPlayerOwns = new HashSet<>(cards);
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	/**
	 * @return the leader cards owned by the player
	 */
	@Override
	public Set<LeaderCard> getLeaderCards() {
		return new HashSet<>(leaderCardsPlayerOwns);
	}

	/**
	 * @return the leader cards owned by the player that have been activated
	 */
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
	public Set<Production> getActiveLeaderCardsProductions() {
		return getActiveLeaderCards().stream()
				.flatMap(leaderCard -> leaderCard.getProductions().stream())
				.collect(Collectors.toSet());
	}

	//TODO JavaDoc and Test
	@Override
	public Set<Production> getActiveDevelopmentCardsProductions(){
		return getDevelopmentCardsOnTop().stream()
			.map(DevelopmentCard::getProduction)
			.collect(Collectors.toSet());
	}

	//TODO JavaDoc and Test
	@Override
	public Set<Production> getActiveProductions() {
		return Stream.concat(Stream.concat(
			baseProductions.stream(),
			getActiveLeaderCardsProductions().stream()),
			getActiveDevelopmentCardsProductions().stream()
		).collect(Collectors.toSet());
	}

	/**
	 * The shelves are resource storages that can always be used to store resource from the market. When playing with
	 * the standard rule every shelve will have a different limit on the number of resources it can contain.
	 * @return the shelves
	 */
	@Override
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
	@Override
	public Set<ResourceStorage> getResourceStoragesForResourcesFromMarket() {
		return Stream.concat(shelves.stream(), getActiveLeaderCardsResourceStorages().stream()).collect(Collectors.toSet());
	}

	/**
	 * The infinite chest is a chest that does not have a limit on the number of resources it can contain. This special
	 * storage can NOT be used to store resources from the market.
	 * @return the infinite chest
	 */
	@Override
	public ResourceStorage getInfiniteChest() {
		return infiniteChest;
	}

	/**
	 * @return a temporary storage used only to hold resources obtained from the market before the player repositions
	 * them in the shelves and/or in the leader card storages
	 */
	@Override
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
	@Override
	public void setTemporaryStorageResources(Map<ResourceType, Integer> resources)
		throws ResourceStorageRuleViolationException, NotEnoughResourcesException {
		clearTemporaryStorageResources();
		tempStorage.addResources(resources);
	}

	/**
	 * Utility method, same as PlayerContext.getTemporaryStorage().peekResources()
	 * @return returns the resources in the temporary storage
	 */
	@Override
	public Map<ResourceType, Integer> getTemporaryStorageResources() {
		return tempStorage.peekResources();
	}

	/**
	 * This method will empty the temporary storage. It should be called when the player moves the resource to
	 * the shelves/leader card storages.
     * @return resources that were in the temporary storage
	 */
	@Override
	public Map<ResourceType, Integer> clearTemporaryStorageResources() throws NotEnoughResourcesException {
		return tempStorage.removeResources(tempStorage.peekResources());
	}

	//TODO test
	/**
	 * @return the number of temporary star resources obtained from the market. Will be used before the player chooses
	 * which resources to get and repositions them in the shelves and/or in the leader card storages
	 */
	@Override
	public int getTempStarResources() {
		return tempStarResources;
	}

	//TODO test
	/**
	 * This method should be used to set number of the star resources taken from the market before the player
	 * chooses which type of resources he wants to convert them into
	 * @param tempStarResources number of temporary star resources
	 */
	@Override
	public void setTempStarResources(int tempStarResources) {
		this.tempStarResources = tempStarResources;
	}

	/**
     * All resource storages means shelves + leader card storages + infinite chest
	 * <p>
	 * Note1: will only return the leader card resource storages defined in `ACTIVE` leader cards.
	 * Note2: the temporary resource storage is NOT included
	 * @return returns all the active resource storages
	 */
	@Override
	public Set<ResourceStorage> getAllResourceStorages() {
	    return Stream.concat(
	    		getResourceStoragesForResourcesFromMarket().stream(),
				Stream.of(infiniteChest)
		).collect(Collectors.toSet());
	}

	/**
	 * Get all resources contained in any active resource storage
	 * @return returns all resources a player owns.
	 * @see PlayerContextImp#getAllResourceStorages()
	 */
	@Override
	public Map<ResourceType, Integer> getAllResources() {
		return getAllResourceStorages().stream()
				.flatMap(resourceStorage -> resourceStorage.peekResources().entrySet().stream())
				.collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingInt(Map.Entry::getValue)));
	}

	@Override
	public void removeResourcesBasedOnResourcesStoragesPriority(
		Map<ResourceType, Integer> resourcesToRemove
	) throws NotEnoughResourcesException {

		Map<ResourceType, Integer> resourceCostLeftToRemove = new HashMap<>(resourcesToRemove);

		for (ResourceStorage storage : getResourceStoragesForResourcesFromMarket()) {
			Map<ResourceType, Integer> resourcesRemovableFromStorage = ResourceUtils.intersection(
				storage.peekResources(),
				resourceCostLeftToRemove
			);
			storage.removeResources(resourcesRemovableFromStorage);
			resourceCostLeftToRemove = ResourceUtils.difference(
				resourceCostLeftToRemove,
				resourcesRemovableFromStorage
			);
		}

		getInfiniteChest().removeResources(resourceCostLeftToRemove);
	}


	/**
	 * This method allows to get one of the decks of development cards owned by the player.
	 * <p>
	 * Note2: when playing with the standard rules there will be 3 decks of development cards for every player. (Those
	 * deck will be empty when the game starts)
	 * @param deckNumber the number of the deck that will be returned
	 * @return returns the deck with the specified deckNumber
	 * @throws IllegalArgumentException if there is no deck with that deckNumber. (if deckNumber >= numberOfDecks)
	 * @see PlayerOwnedDevelopmentCardDeckImp
	 */
	@Override
	public PlayerOwnedDevelopmentCardDeck getDeck(int deckNumber) throws IllegalArgumentException {
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
	 * @throws ForbiddenPushOnTopException if you try to add a development card of level N on top of a card of level
	 * that is not N - 1. (You can add card of level 1 only on empty decks)
	 * @see PlayerOwnedDevelopmentCardDeckImp
	 * @see it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel
	 */
	@Override
	public void addDevelopmentCard(DevelopmentCard card, int deckNumber) throws IllegalArgumentException, ForbiddenPushOnTopException {
		getDeck(deckNumber).pushOnTop(card);
	}

	/**
	 * This method allow to check if it is possible to add a new development card on top of one of the decks of
	 * development cards owned by the player.
	 * <p>
	 * Note1: you can only add a card with level N on top of a card on level N - 1.
	 * Note2: when playing with the standard rules there will be 3 decks of development cards for every player. (Those
	 * deck will be empty when the game starts)
	 * @param card development card to add.
	 * @param deckNumber the number of the deck to add the development card on top
	 * @throws IllegalArgumentException if there is no deck with that deckNumber. (if deckNumber >= numberOfDecks)
	 * @see PlayerOwnedDevelopmentCardDeckImp
	 * @see it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel
	 */
	@Override
	public boolean canAddDevelopmentCard(DevelopmentCard card, int deckNumber) throws IllegalArgumentException {
		return getDeck(deckNumber).isPushOnTopValid(card);
	}

	/**
	 * @return returns all the development cards owned by the player
	 */
	@Override
	public Set<DevelopmentCard> getAllDevelopmentCards() {
		return developmentCardDecks.stream().flatMap(deck -> deck.peekAll().stream()).collect(Collectors.toSet());
	}

	/**
	 * Only the productions of development card that are currently on top of their deck can be activated
	 * @return returns all the development cards owned by the player that are on top of the relative deck
	 */
	@Override
	public Set<DevelopmentCard> getDevelopmentCardsOnTop() {
		return developmentCardDecks.stream()
			.filter(d -> !d.isEmpty())
			.map(PlayerOwnedDevelopmentCardDeck::peek)
			.collect(Collectors.toSet());
	}

	@Override
	public List<PlayerOwnedDevelopmentCardDeck> getPlayerDevCardsDecks() {
		return developmentCardDecks;
	}

	@Override
	public ServerPlayerContextRepresentation getServerRepresentation() {
		return new ServerPlayerContextRepresentation(
			player,
			shelves.stream().map(Representable::getServerRepresentation).collect(Collectors.toSet()),
			infiniteChest.getServerRepresentation(),
			tempStorage.getServerRepresentation(),
			tempStarResources,
			leaderCardsPlayerOwns.stream().map(Representable::getServerRepresentation).collect(Collectors.toSet()),
			developmentCardDecks.stream().map(Representable::getServerRepresentation).collect(Collectors.toList()),
			baseProductions.stream().map(Representable::getServerRepresentation).collect(Collectors.toSet()),
			leaderCardsPlayerOwns.size()
		);
	}

	@Override
	public ServerPlayerContextRepresentation getServerRepresentationForPlayer(Player player) {
		if (this.player.equals(player))
			return getServerRepresentation();
		else {
			return new ServerPlayerContextRepresentation(
				player,
				shelves.stream().map(Representable::getServerRepresentation).collect(Collectors.toSet()),
				infiniteChest.getServerRepresentation(),
				tempStorage.getServerRepresentation(),
				tempStarResources,
				null,
				developmentCardDecks.stream().map(Representable::getServerRepresentation).collect(Collectors.toList()),
				baseProductions.stream().map(Representable::getServerRepresentation).collect(Collectors.toSet()),
				leaderCardsPlayerOwns.size()
			);
		}
	}
}
