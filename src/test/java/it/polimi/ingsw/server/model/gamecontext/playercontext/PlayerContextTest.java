package it.polimi.ingsw.server.model.gamecontext.playercontext;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PlayerContextTest {

    @Mock
    Player player;

    @Test
    void testSetLeaderCards() {
        PlayerContext playerContext = new PlayerContext(player, 2);
        assertEquals(new HashSet<>(), playerContext.getLeaderCards());
        Set<LeaderCard> cards = Set.of(

        );
        playerContext.setLeaderCards();
    }

    @Test
    void getActiveLeaderCards() {
    }

    @Test
    void getActiveLeaderCardsDiscounts() {
    }

    @Test
    void getActiveLeaderCardsWhiteMarblesMarketSubstitutions() {
    }

    @Test
    void getActiveLeaderCardsResourceStorages() {
    }

    @Test
    void getActiveLeaderCardsProductions() {
    }

    @Test
    void getShelves() {
    }

    @Test
    void getResourceStoragesForResourcesFromMarket() {
    }

    @Test
    void getInfiniteChest() {
    }

    @Test
    void getTemporaryStorage() {
    }

    @Test
    void setTemporaryStorageResources() {
    }

    @Test
    void clearTemporaryStorageResources() {
    }

    @Test
    void getAllResourceStorages() {
    }

    @Test
    void getAllResources() {
    }

    @Test
    void addDevelopmentCard() {
    }

    @Test
    void getAllDevelopmentCards() {
    }

    @Test
    void getDevelopmentCardsOnTop() {
    }
}