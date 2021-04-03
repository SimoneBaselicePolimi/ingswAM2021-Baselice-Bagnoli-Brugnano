package it.polimi.ingsw.server.model.gameitems.developmentcard;

import it.polimi.ingsw.server.model.gameitems.Production;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DevelopmentCardTest {

    @Mock
    Production production;

    @Test
    void testConstructor(){
        DevelopmentCard developmentCard1 = new DevelopmentCard(DevelopmentCardLevel.FIRST_LEVEL, DevelopmentCardColour.BLUE, production, 3);
        DevelopmentCard developmentCard2 = new DevelopmentCard(null, null, null, 0);

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
    }
}
