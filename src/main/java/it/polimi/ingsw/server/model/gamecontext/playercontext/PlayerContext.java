package it.polimi.ingsw.server.model.gamecontext.playercontext;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Representable;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.cardstack.ForbiddenPushOnTopException;
import it.polimi.ingsw.server.model.gameitems.cardstack.PlayerOwnedDevelopmentCardDeck;
import it.polimi.ingsw.server.model.gameitems.cardstack.PlayerOwnedDevelopmentCardDeckImp;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ServerPlayerContextRepresentation;

import java.util.Map;
import java.util.Set;

public interface PlayerContext extends Representable<ServerPlayerContextRepresentation> {
    /**
     * This method will set the leader cards that a certain player owns.
     * The method should be called only once at the beginning of the game when the player chooses which cards to keep.
     *
     * Note: there can not be multiple copies of a card with the same ID. This is enforced by using a Set for the
     * parameter `cards` instead of a list (@see LeaderCard.equals())
     * @param cards a set of cards to keep.
     */
    void setLeaderCards(Set<LeaderCard> cards);

    /**
     * @return player associated to this player context
     */
    Player getPlayer();

    /**
     * @return the leader cards owned by the player
     */
    Set<LeaderCard> getLeaderCards();

    /**
     * @return the leader cards owned by the player that have been activated
     */
    Set<LeaderCard> getActiveLeaderCards();

    /**
* After it has been activated, a leader card may award the player with one (or more, in games with custom rules)
     * development card discounts. This method returns the sum of all the discounts specified in active leader cards.
     * Note: only active leader card will be considered
     * @return the sum of all the discounts that will be applied when the player buys a new development card.
     */
    Map<ResourceType, Integer> getActiveLeaderCardsDiscounts();

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
    Set<ResourceType> getActiveLeaderCardsWhiteMarblesMarketSubstitutions();

    /**
     * After it has been activated, a leader card may award the player with one (or more, in games with custom rules)
     * special resource storages. Those special resource storages can be used (like the shelves) to store resources
     * obtained from the market.
     * <p>
     * Note: only active leader card will be considered
     * @return a set of all the resource storages from leader cards
     */
    Set<ResourceStorage> getActiveLeaderCardsResourceStorages();

    /**
     * After it has been activated, a leader card may award the player with one (or more, in games with custom rules)
     * productions that can be activated when executing an "Activate production" game action.
     * <p>
     * Note1: only active leader card will be considered
     * Note2: the player may not be able to use some of the productions returned by this method because he
     * may not have all the resources needed.
     * @return a set of all the productions from leader cards. Some of the production returned may not act
     */
    Set<Production> getActiveLeaderCardsProductions();

    //TODO JavaDoc and Test
    Set<Production> getActiveDevelopmentCardsProductions();

    //TODO JavaDoc and Test
    Set<Production> getActiveProductions();

    /**
     * The shelves are resource storages that can always be used to store resource from the market. When playing with
     * the standard rule every shelve will have a different limit on the number of resources it can contain.
     * @return the shelves
     */
    Set<ResourceStorage> getShelves();

    /**
     * Shelves and special leader card storages can store resources obtained from the market.
* <p>
     * Note1: will only return the leader card resource storages defined in `ACTIVE` leader cards.
     * Note2: the temporary resource storage is NOT included
     * @return returns the active resource storages that can hold resources obtained from the market
     */
    Set<ResourceStorage> getResourceStoragesForResourcesFromMarket();

    /**
     * The infinite chest is a chest that does not have a limit on the number of resources it can contain. This special
     * storage can NOT be used to store resources from the market.
     * @return the infinite chest
     */
    ResourceStorage getInfiniteChest();

    /**
     * @return a temporary storage used only to hold resources obtained from the market before the player repositions
     * them in the shelves and/or in the leader card storages
     */
    ResourceStorage getTemporaryStorage();

    /**
     * This method should be used to put the resources taken from the market into the temporary storage.
     * <p>
     * Note: this method is a setter, if there were resources into this temporary storage when this methods gets called
     * they will be overwritten.
     * @param resources resources to put into the temporary storage
     */
    void setTemporaryStorageResources(Map<ResourceType, Integer> resources)
        throws ResourceStorageRuleViolationException, NotEnoughResourcesException;

    /**
     * Utility method, same as PlayerContext.getTemporaryStorage().peekResources()
     * @return returns the resources in the temporary storage
     */
    Map<ResourceType, Integer> getTemporaryStorageResources();

    /**
     * This method will empty the temporary storage. It should be called when the player moves the resource to
     * the shelves/leader card storages.
* @return resources that were in the temporary storage
     */
    Map<ResourceType, Integer> clearTemporaryStorageResources() throws NotEnoughResourcesException;

    /**
     * @return the number of temporary star resources obtained from the market. Will be used before the player chooses
     * which resources to get and repositions them in the shelves and/or in the leader card storages
     */
    int getTempStarResources();

    /**
     * This method should be used to set number of the star resources taken from the market before the player
     * chooses which type of resources he wants to convert them into
     * @param tempStarResources number of temporary star resources
     */
    void setTempStarResources(int tempStarResources);

    /**
* All resource storages means shelves + leader card storages + infinite chest
     * <p>
     * Note1: will only return the leader card resource storages defined in `ACTIVE` leader cards.
     * Note2: the temporary resource storage is NOT included
     * @return returns all the active resource storages
     */
    Set<ResourceStorage> getAllResourceStorages();

    /**
     * Get all resources contained in any active resource storage
     * @return returns all resources a player owns.
     * @see PlayerContextImp#getAllResourceStorages()
     */
    Map<ResourceType, Integer> getAllResources();

    void removeResourcesBasedOnResourcesStoragesPriority(
        Map<ResourceType, Integer> resourcesToRemove
    ) throws NotEnoughResourcesException;

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
    PlayerOwnedDevelopmentCardDeck getDeck(int deckNumber) throws IllegalArgumentException;

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
    void addDevelopmentCard(DevelopmentCard card, int deckNumber) throws IllegalArgumentException, ForbiddenPushOnTopException;

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
    boolean canAddDevelopmentCard(DevelopmentCard card, int deckNumber) throws IllegalArgumentException;

    /**
     * @return returns all the development cards owned by the player
     */
    Set<DevelopmentCard> getAllDevelopmentCards();

    /**
     * Only the productions of development card that are currently on top of their deck can be activated
     * @return returns all the development cards owned by the player that are on top of the relative deck
     */
    Set<DevelopmentCard> getDevelopmentCardsOnTop();
}
