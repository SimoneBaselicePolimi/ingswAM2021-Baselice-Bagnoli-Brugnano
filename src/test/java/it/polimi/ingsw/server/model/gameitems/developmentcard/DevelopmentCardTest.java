package it.polimi.ingsw.server.model.gameitems.developmentcard;

import it.polimi.ingsw.server.model.gameitems.IdentifiableItemTest;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class DevelopmentCardTest implements IdentifiableItemTest<DevelopmentCard> {

    @Mock
    Production production;

    @Mock
    Map<ResourceType, Integer> purchaseCost;

    @Mock
    Map<ResourceType, Integer> purchaseCost1;

    /**
     * Tests the initialization of a Development Card.
     * Checks the equality between elements passed to the constructor and elements returned by getter methods.
     */
    @Test
    void testConstructor(){
        DevelopmentCard developmentCard1 = new DevelopmentCard("testID", DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE, production, 3, purchaseCost);
        DevelopmentCard developmentCard2 = new DevelopmentCard("testID", null, null, null, 0, purchaseCost);

        assertEquals(developmentCard1.getLevel(), DevelopmentCardLevel.FIRST_LEVEL);
        assertEquals(developmentCard1.getColour(), DevelopmentCardColour.BLUE);
        assertEquals(developmentCard1.getProduction(), production);
        assertEquals(developmentCard1.getVictoryPoints(),3);
        assertEquals(developmentCard2.getLevel(), null);
        assertEquals(developmentCard2.getColour(), null);
        assertEquals(developmentCard2.getProduction(), null);
        assertEquals(developmentCard2.getVictoryPoints(),0);
        assertNotEquals(developmentCard2.getLevel(), DevelopmentCardLevel.SECOND_LEVEL);
        assertNotEquals(developmentCard1.getColour(), DevelopmentCardColour.PURPLE);
        assertEquals(developmentCard1.getProduction(), production);
        assertEquals(developmentCard1.getPurchaseCost(), purchaseCost);
        assertNotEquals(developmentCard1.getPurchaseCost(), purchaseCost1);
    }

    @Override
    public DevelopmentCard initializeItemWithId(String id) {
        return new DevelopmentCard(
            id,
            DevelopmentCardLevel.FIRST_LEVEL,
            DevelopmentCardColour.BLUE,
            mock(Production.class),
            0,
            new HashMap<>()
        );
    }
}
