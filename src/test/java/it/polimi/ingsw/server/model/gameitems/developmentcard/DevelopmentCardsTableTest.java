package it.polimi.ingsw.server.model.gameitems.developmentcard;

import it.polimi.ingsw.server.model.gameitems.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
public class DevelopmentCardsTableTest {
    @Mock
    DevelopmentCard developmentCard1;

    @Mock
    DevelopmentCard developmentCard2;

    @Mock
    DevelopmentCard developmentCard3;



    //@BeforeEach
    //void setUp() {
    //    when(trueRule1.checkRule(any(), any())).thenReturn(true);
    @Test
    void getAvailableCardsTest (){
        List<DevelopmentCard> developmentCards = new ArrayList<DevelopmentCard>();
        developmentCards.add(developmentCard1);
        developmentCards.add(developmentCard2);
        developmentCards.add(developmentCard3);
        DevelopmentCardsTable table = new DevelopmentCardsTable(developmentCards);
        table.getAvailableCards();

    }
}
