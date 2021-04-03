package it.polimi.ingsw.server.model.gamecontext.playercontext;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.DevelopmentCardCostDiscount;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.WhiteMarbleSubstitution;
import it.polimi.ingsw.server.model.gameitems.cardstack.PlayerOwnedDevelopmentCardStack;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerContextTest {

    @Mock
    Player player;

    @Test
    void testSetLeaderCards() {

        PlayerContext playerContext = new PlayerContext(player, 2, new HashSet<>());

        assertEquals(new HashSet<>(), playerContext.getLeaderCards());

        Set<LeaderCard> cards = Set.of(
                mock(LeaderCard.class),
                mock(LeaderCard.class),
                mock(LeaderCard.class),
                mock(LeaderCard.class)
        );
        playerContext.setLeaderCards(cards);
        assertEquals(cards, playerContext.getLeaderCards());

    }

    @Test
    void testGetActiveLeaderCards() {

        LeaderCard activeCard1 = mock(LeaderCard.class);
        when(activeCard1.getState()).thenReturn(LeaderCardState.ACTIVE);

        LeaderCard activeCard2 = mock(LeaderCard.class);
        when(activeCard2.getState()).thenReturn(LeaderCardState.ACTIVE);

        LeaderCard hiddenCard1 = mock(LeaderCard.class);
        when(activeCard1.getState()).thenReturn(LeaderCardState.IN_HAND);

        LeaderCard hiddenCard2 = mock(LeaderCard.class);
        when(activeCard1.getState()).thenReturn(LeaderCardState.IN_HAND);

        LeaderCard discardedCard1 = mock(LeaderCard.class);
        when(activeCard1.getState()).thenReturn(LeaderCardState.DISCARDED);

        Set<LeaderCard> cards = Set.of(activeCard1, hiddenCard1, discardedCard1, activeCard2, hiddenCard2);
        Set<LeaderCard> activeCards = Set.of(activeCard1, activeCard2);

        PlayerContext playerContext = new PlayerContext(player, 2, new HashSet<>());
        playerContext.setLeaderCards(cards);
        assertEquals(activeCards, playerContext.getActiveLeaderCards());

    }

    @Test
    void testGetActiveLeaderCardsDiscounts() {

        LeaderCard cardWithCoinsDiscount = mock(LeaderCard.class);
        when(cardWithCoinsDiscount.getDevelopmentCardCostDiscount()).thenReturn(List.of(
                new DevelopmentCardCostDiscount(ResourceType.COINS, 2)
        ));
        when(cardWithCoinsDiscount.getState()).thenReturn(LeaderCardState.ACTIVE);

        LeaderCard cardHiddenWithShieldsDiscount = mock(LeaderCard.class);
        when(cardHiddenWithShieldsDiscount.getDevelopmentCardCostDiscount()).thenReturn(List.of(
                new DevelopmentCardCostDiscount(ResourceType.STONES, 1)
        ));
        when(cardHiddenWithShieldsDiscount.getState()).thenReturn(LeaderCardState.IN_HAND);

        LeaderCard cardDiscardedWithShieldsDiscount = mock(LeaderCard.class);
        when(cardDiscardedWithShieldsDiscount.getDevelopmentCardCostDiscount()).thenReturn(List.of(
                new DevelopmentCardCostDiscount(ResourceType.STONES, 1)
        ));
        when(cardDiscardedWithShieldsDiscount.getState()).thenReturn(LeaderCardState.DISCARDED);

        LeaderCard cardWithCoinsAndStonesDiscount = mock(LeaderCard.class);
        when(cardWithCoinsAndStonesDiscount.getDevelopmentCardCostDiscount()).thenReturn(List.of(
                new DevelopmentCardCostDiscount(ResourceType.STONES, 5),
                new DevelopmentCardCostDiscount(ResourceType.COINS, 1)
        ));
        when(cardWithCoinsAndStonesDiscount.getState()).thenReturn(LeaderCardState.ACTIVE);

        LeaderCard cardWithNoDiscount = mock(LeaderCard.class);
        when(cardWithNoDiscount.getDevelopmentCardCostDiscount()).thenReturn(new ArrayList<>());
        when(cardWithNoDiscount.getState()).thenReturn(LeaderCardState.ACTIVE);

        PlayerContext playerContext = new PlayerContext(player, 2, new HashSet<>());
        playerContext.setLeaderCards(Set.of(
                cardWithNoDiscount,
                cardWithCoinsAndStonesDiscount,
                cardWithCoinsDiscount,
                cardDiscardedWithShieldsDiscount,
                cardHiddenWithShieldsDiscount
        ));
        assertEquals(
            Map.of(
                ResourceType.STONES, 5,
                ResourceType.COINS, 3
            ),
            playerContext.getActiveLeaderCardsDiscounts()
        );

    }

    @Test
    void testGetActiveLeaderCardsWhiteMarblesMarketSubstitutions() {

        LeaderCard cardWithShieldsSub = mock(LeaderCard.class);
        when(cardWithShieldsSub.getWhiteMarbleSubstitutions()).thenReturn(List.of(
                new WhiteMarbleSubstitution(ResourceType.SHIELDS)
        ));
        when(cardWithShieldsSub.getState()).thenReturn(LeaderCardState.ACTIVE);

        LeaderCard cardWithShieldStoneSub = mock(LeaderCard.class);
        when(cardWithShieldStoneSub.getWhiteMarbleSubstitutions()).thenReturn(List.of(
                new WhiteMarbleSubstitution(ResourceType.SHIELDS),
                new WhiteMarbleSubstitution(ResourceType.STONES)
        ));
        when(cardWithShieldStoneSub.getState()).thenReturn(LeaderCardState.ACTIVE);

        LeaderCard cardWithServantsSub = mock(LeaderCard.class);
        when(cardWithServantsSub.getWhiteMarbleSubstitutions()).thenReturn(List.of(
                new WhiteMarbleSubstitution(ResourceType.SERVANTS)
        ));
        when(cardWithServantsSub.getState()).thenReturn(LeaderCardState.ACTIVE);

        LeaderCard cardHiddenWithCoinsSub = mock(LeaderCard.class);
        when(cardHiddenWithCoinsSub.getWhiteMarbleSubstitutions()).thenReturn(List.of(
                new WhiteMarbleSubstitution(ResourceType.COINS)
        ));
        when(cardHiddenWithCoinsSub.getState()).thenReturn(LeaderCardState.IN_HAND);

        LeaderCard cardDiscardedWithCoinsSub = mock(LeaderCard.class);
        when(cardDiscardedWithCoinsSub.getWhiteMarbleSubstitutions()).thenReturn(List.of(
                new WhiteMarbleSubstitution(ResourceType.COINS)
        ));
        when(cardDiscardedWithCoinsSub.getState()).thenReturn(LeaderCardState.DISCARDED);

        PlayerContext playerContext = new PlayerContext(player, 2, new HashSet<>());
        playerContext.setLeaderCards(Set.of(
                cardWithShieldsSub,
                cardWithShieldStoneSub,
                cardWithServantsSub,
                cardHiddenWithCoinsSub,
                cardDiscardedWithCoinsSub
        ));
        assertEquals(
                Set.of(ResourceType.SHIELDS, ResourceType.STONES, ResourceType.SERVANTS),
                playerContext.getActiveLeaderCardsWhiteMarblesMarketSubstitutions()
        );

    }

    @Test
    void testGetActiveLeaderCardsResourceStorages() {

        ResourceStorage storage1 = mock(ResourceStorage.class);
        ResourceStorage storage2 = mock(ResourceStorage.class);
        ResourceStorage storage3 = mock(ResourceStorage.class);
        ResourceStorage storage4 = mock(ResourceStorage.class);

        LeaderCard cardWithStorage1 = mock(LeaderCard.class);
        when(cardWithStorage1.getResourceStorages()).thenReturn(List.of(storage1));
        when(cardWithStorage1.getState()).thenReturn(LeaderCardState.ACTIVE);

        LeaderCard cardWithStorage2 = mock(LeaderCard.class);
        when(cardWithStorage2.getResourceStorages()).thenReturn(List.of(storage2, storage3));
        when(cardWithStorage2.getState()).thenReturn(LeaderCardState.ACTIVE);

        LeaderCard cardHiddenWithStorage = mock(LeaderCard.class);
        when(cardHiddenWithStorage.getResourceStorages()).thenReturn(List.of(storage4));
        when(cardHiddenWithStorage.getState()).thenReturn(LeaderCardState.IN_HAND);

        PlayerContext playerContext = new PlayerContext(player, 2, new HashSet<>());
        playerContext.setLeaderCards(Set.of(cardWithStorage1, cardWithStorage2, cardHiddenWithStorage));
        assertEquals(
                Set.of(storage1, storage2, storage3),
                playerContext.getActiveLeaderCardsResourceStorages()
        );

    }

    @Test
    void testGetActiveLeaderCardsProductions() {

        Production production1 = mock(Production.class);
        Production production2 = mock(Production.class);
        Production production3 = mock(Production.class);
        Production production4 = mock(Production.class);

        LeaderCard cardWithProd1 = mock(LeaderCard.class);
        when(cardWithProd1.getProductions()).thenReturn(List.of(production1));
        when(cardWithProd1.getState()).thenReturn(LeaderCardState.ACTIVE);

        LeaderCard cardWithProd2 = mock(LeaderCard.class);
        when(cardWithProd2.getProductions()).thenReturn(List.of(production2, production3));
        when(cardWithProd2.getState()).thenReturn(LeaderCardState.ACTIVE);

        LeaderCard cardHiddenWithProd = mock(LeaderCard.class);
        when(cardHiddenWithProd.getProductions()).thenReturn(List.of(production4));
        when(cardHiddenWithProd.getState()).thenReturn(LeaderCardState.IN_HAND);

        PlayerContext playerContext = new PlayerContext(player, 2, new HashSet<>());
        playerContext.setLeaderCards(Set.of(cardWithProd1, cardWithProd2, cardHiddenWithProd));
        assertEquals(
                Set.of(production1, production2, production3),
                playerContext.getActiveLeaderCardsProductions()
        );

    }

    @Test
    void testGetShelves() {
        Set<ResourceStorage> shelves = Set.of(
                mock(ResourceStorage.class),
                mock(ResourceStorage.class),
                mock(ResourceStorage.class)
        );
        PlayerContext playerContext = new PlayerContext(player, 2, shelves);
        assertEquals(shelves, playerContext.getShelves());
    }

    @Test
    void testGetResourceStoragesForResourcesFromMarket() {

        ResourceStorage shelve1 = mock(ResourceStorage.class);
        ResourceStorage shelve2 = mock(ResourceStorage.class);
        ResourceStorage leaderCardStorage1 = mock(ResourceStorage.class);
        ResourceStorage leaderCardStorage2 = mock(ResourceStorage.class);
        Set<ResourceStorage> shelves = Set.of(shelve1, shelve2);

        LeaderCard leaderCard1 = mock(LeaderCard.class);
        when(leaderCard1.getResourceStorages()).thenReturn(List.of(leaderCardStorage1));
        when(leaderCard1.getState()).thenReturn(LeaderCardState.ACTIVE);

        LeaderCard leaderCardDiscarded = mock(LeaderCard.class);
        when(leaderCardDiscarded.getResourceStorages()).thenReturn(List.of(leaderCardStorage2));
        when(leaderCardDiscarded.getState()).thenReturn(LeaderCardState.DISCARDED);

        PlayerContext playerContext = new PlayerContext(player, 2, shelves);
        playerContext.setLeaderCards(Set.of(leaderCard1, leaderCardDiscarded));
        assertEquals(
            Set.of(
                shelve1,
                shelve2,
                leaderCardStorage1
            ),
            playerContext.getResourceStoragesForResourcesFromMarket()
        );

    }

    @Test
    void testGetInfiniteChest() {
        PlayerContext playerContext = new PlayerContext(player, 2, new HashSet<>());
        assertNotNull(playerContext.getInfiniteChest());
    }

    @Test
    void testGetTemporaryStorage() {
        PlayerContext playerContext = new PlayerContext(player, 2, new HashSet<>());
        assertNotNull(playerContext.getTemporaryStorage());
        assertEquals(new HashSet<>(), playerContext.getTemporaryStorage().peekResources());
    }

    @Test
    void testSetTemporaryStorageResources() {
        PlayerContext playerContext = new PlayerContext(player, 2, new HashSet<>());

        Map<ResourceType, Integer> resources1 = Map.of(
                ResourceType.COINS, 2,
                ResourceType.SHIELDS, 4
        );
        playerContext.setTemporaryStorageResources(resources1);
        assertEquals(resources1, playerContext.getTemporaryStorageResources());
        assertEquals(resources1, playerContext.getTemporaryStorage().peekResources());

        Map<ResourceType, Integer> resources2 = Map.of(
                ResourceType.STONES, 2
        );
        playerContext.setTemporaryStorageResources(resources2);
        assertEquals(resources2, playerContext.getTemporaryStorageResources());
        assertEquals(resources2, playerContext.getTemporaryStorage().peekResources());


    }

    @Test
    void testClearTemporaryStorageResources() {
        PlayerContext playerContext = new PlayerContext(player, 2, new HashSet<>());
        playerContext.setTemporaryStorageResources(Map.of(
                ResourceType.COINS, 2,
                ResourceType.SHIELDS, 4
        ));
        playerContext.clearTemporaryStorageResources();
        assertEquals(new HashMap<>(), playerContext.getTemporaryStorageResources());
        assertEquals(new HashMap<>(), playerContext.getTemporaryStorage().peekResources());
    }

    @Test
    void testGetAllResourceAndResourcesStorages() {

        ResourceStorage shelve1 = mock(ResourceStorage.class);
        when(shelve1.peekResources()).thenReturn(Map.of(
                ResourceType.COINS, 2
        ));

        ResourceStorage shelve2 = mock(ResourceStorage.class);
        when(shelve2.peekResources()).thenReturn(Map.of(
                ResourceType.SHIELDS, 1
        ));

        Set<ResourceStorage> shelves = Set.of(shelve1, shelve2);

        ResourceStorage leaderCardStorage1 = mock(ResourceStorage.class);
        when(leaderCardStorage1.peekResources()).thenReturn(Map.of(
                ResourceType.COINS, 2
        ));
        LeaderCard leaderCard1 = mock(LeaderCard.class);
        when(leaderCard1.getResourceStorages()).thenReturn(List.of(leaderCardStorage1));
        when(leaderCard1.getState()).thenReturn(LeaderCardState.ACTIVE);

        ResourceStorage leaderCardStorage2 = mock(ResourceStorage.class);
        when(leaderCardStorage2.peekResources()).thenReturn(Map.of(
                ResourceType.SERVANTS, 100
        ));
        LeaderCard leaderCardDiscarded = mock(LeaderCard.class);
        when(leaderCardDiscarded.getResourceStorages()).thenReturn(List.of(leaderCardStorage2));
        when(leaderCardDiscarded.getState()).thenReturn(LeaderCardState.IN_HAND);

        ResourceStorage infiniteChest = mock(ResourceStorage.class);
        when(infiniteChest.peekResources()).thenReturn(Map.of(
                ResourceType.STONES, 3,
                ResourceType.COINS, 1
        ));

        ResourceStorage tempStorage = mock(ResourceStorage.class);
        when(infiniteChest.peekResources()).thenReturn(Map.of(
                ResourceType.SERVANTS, 1000
        ));

        PlayerContext playerContext = new PlayerContext(player, shelves, new ArrayList<>(), infiniteChest, tempStorage);
        playerContext.setLeaderCards(Set.of(leaderCard1, leaderCardDiscarded));
        assertEquals(infiniteChest, playerContext.getInfiniteChest());
        assertEquals(tempStorage, playerContext.getTemporaryStorage());

        assertEquals(
                Set.of(
                        shelve1,
                        shelve2,
                        leaderCardStorage1,
                        infiniteChest
                ),
                playerContext.getAllResourceStorages()
        );
        assertEquals(
                Map.of(
                        ResourceType.SHIELDS, 1,
                        ResourceType.STONES, 3,
                        ResourceType.COINS, 5
                ),
                playerContext.getAllResources()
        );

    }

    @Test
    void testAddDevelopmentCard() {
        DevelopmentCard devCard1Deck1 = mock(DevelopmentCard.class);
        DevelopmentCard devCard2Deck1 = mock(DevelopmentCard.class);
        DevelopmentCard devCard1Deck2 = mock(DevelopmentCard.class);
        DevelopmentCard devCard2Deck2 = mock(DevelopmentCard.class);
        DevelopmentCard devCard3Deck2 = mock(DevelopmentCard.class);
        PlayerOwnedDevelopmentCardStack deck1 = mock(PlayerOwnedDevelopmentCardStack.class);
        PlayerOwnedDevelopmentCardStack deck2 = mock(PlayerOwnedDevelopmentCardStack.class);

        PlayerContext playerContext = new PlayerContext(
                player,
                new HashSet<>(),
                List.of(deck1, deck2),
                mock(ResourceStorage.class),
                mock(ResourceStorage.class)
        );
        assertEquals(deck1, playerContext.getDeck(0));

    }

}