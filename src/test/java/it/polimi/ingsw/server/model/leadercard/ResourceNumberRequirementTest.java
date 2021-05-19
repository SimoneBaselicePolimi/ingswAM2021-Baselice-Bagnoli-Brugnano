package it.polimi.ingsw.server.model.leadercard;

import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.leadercard.ResourceNumberRequirement;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class) //Needed to use annotation @Mock

public class ResourceNumberRequirementTest {

    @Mock
    PlayerContext playerContext1;

    @Mock
    PlayerContext playerContext2;

    @Mock
    PlayerContext playerContext3;

    @Mock
    PlayerContext playerContext4;

    @BeforeEach
    void setUp() {
        Map<ResourceType, Integer> map1 = new HashMap<ResourceType, Integer>();
        Map<ResourceType, Integer> map2 = new HashMap<ResourceType, Integer>();
        Map<ResourceType, Integer> map3 = new HashMap<ResourceType, Integer>();
        Map<ResourceType, Integer> emptyMap = new HashMap<ResourceType, Integer>();

        map1.put(ResourceType.COINS, 3);
        map1.put(ResourceType.SERVANTS, 3);
        map1.put(ResourceType.STONES,1);

        map2.put(ResourceType.SHIELDS, 1);
        map2.put(ResourceType.COINS, 1);

        map3.put(ResourceType.STONES,4);
        map3.put(ResourceType.SERVANTS,4);


        lenient().when(playerContext1.getAllResources()).thenReturn(map1);
        lenient().when(playerContext2.getAllResources()).thenReturn(map2);
        lenient().when(playerContext3.getAllResources()).thenReturn(map3);
        lenient().when(playerContext4.getAllResources()).thenReturn(emptyMap);
    }

    ResourceNumberRequirement requirement1 = new ResourceNumberRequirement(ResourceType.COINS, 2);
    ResourceNumberRequirement requirement2 = new ResourceNumberRequirement(ResourceType.SERVANTS, 3);
    ResourceNumberRequirement requirement3 = new ResourceNumberRequirement(ResourceType.SERVANTS, 5);
    ResourceNumberRequirement requirement4 = new ResourceNumberRequirement(ResourceType.SHIELDS, 1);

    /**
     * Tests the method to verify if a player has the necessary requirements to activate a leader card:
     * the method returns true if the player has the necessary type and number of resources.
     */
    @Test
    void checkRequirementTest (){
        assertTrue(requirement1.checkRequirement(playerContext1));
        assertTrue(requirement2.checkRequirement(playerContext1));
        assertFalse(requirement3.checkRequirement(playerContext1));
        assertFalse(requirement1.checkRequirement(playerContext2));
        assertFalse(requirement2.checkRequirement(playerContext2));
        assertTrue(requirement4.checkRequirement(playerContext2));
        assertFalse(requirement3.checkRequirement(playerContext2));
        assertTrue(requirement2.checkRequirement(playerContext3));
        assertFalse(requirement3.checkRequirement(playerContext3));
        assertFalse(requirement4.checkRequirement(playerContext3));
    }

    /**
     * Tests the method to verify if a player has the necessary requirements to activate a leader card
     * passing to the method a player who doesn't have any resources.
     * The method must always return false.
     */
    @Test
    void checkRequirementTestWithEmptyMap (){
        assertFalse(requirement1.checkRequirement(playerContext4));
        assertFalse(requirement2.checkRequirement(playerContext4));
        assertFalse(requirement3.checkRequirement(playerContext4));
        assertFalse(requirement4.checkRequirement(playerContext4));
    }
}


