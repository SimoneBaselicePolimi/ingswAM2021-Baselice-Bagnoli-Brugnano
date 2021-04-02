package it.polimi.ingsw.server.model.leadercard;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirement;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfied;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
public class DevelopmentCardColorRequirementTest {
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
        Map<ResourceType, Integer> mapNull = new HashMap<ResourceType, Integer>();

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
        lenient().when(playerContext4.getAllResources()).thenReturn(mapNull);
        }



}
