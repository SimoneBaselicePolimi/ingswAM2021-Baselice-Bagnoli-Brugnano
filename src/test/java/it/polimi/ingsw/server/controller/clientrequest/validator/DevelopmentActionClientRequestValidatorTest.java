package it.polimi.ingsw.server.controller.clientrequest.validator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import it.polimi.ingsw.server.controller.clientrequest.DevelopmentActionClientRequest;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTable;
import it.polimi.ingsw.testutils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)

class DevelopmentActionClientRequestValidatorTest extends ValidatorTest<DevelopmentActionClientRequest, DevelopmentActionClientRequestValidator>{

    DevelopmentActionClientRequestValidator validator = getValidator();

    @Mock
    DevelopmentCardsTable table;

    List<DevelopmentCard> availableDevelopmentCards;

    Iterator<DevelopmentCard> iter;
    DevelopmentCard rightCardThirdLevel;
    DevelopmentCard rightCardSecondLevel;
    DevelopmentCard rightCardFirstLevel;

    @BeforeEach
    void setUp(){

        availableDevelopmentCards = TestUtils.generateListOfMockWithID(DevelopmentCard.class, 8);

        iter = availableDevelopmentCards.iterator();
        rightCardThirdLevel = iter.next();
        rightCardSecondLevel = iter.next();
        rightCardFirstLevel = iter.next();

        //The player has 3 COINS, 2 SERVANTS
        // Third Level Card Cost : 3 COINS, 1 STONES (not enough resources)
        // Second Level Card Cost : 1 COINS, 2 SERVANTS (enough resources)

        lenient().when(gameContext.getDevelopmentCardsTable()).thenReturn(table);
        lenient().when(table.getAvailableCards()).thenReturn(availableDevelopmentCards);

        lenient().when(playerContext.getAllResources()).thenReturn(Map.of(ResourceType.COINS, 3, ResourceType.SERVANTS, 2));

        lenient().when(rightCardThirdLevel.getPurchaseCost()).thenReturn(Map.of(ResourceType.COINS, 3, ResourceType.STONES, 1));
        lenient().when(rightCardSecondLevel.getPurchaseCost()).thenReturn(Map.of(ResourceType.COINS, 1, ResourceType.SERVANTS, 2));

        lenient().when(playerContext.canAddDevelopmentCard(rightCardFirstLevel, 1)).thenReturn(false);
        lenient().when(playerContext.canAddDevelopmentCard(rightCardSecondLevel, 1)).thenReturn(true);
        lenient().when(playerContext.canAddDevelopmentCard(rightCardThirdLevel, 1)).thenReturn(true);

        lenient().when(rightCardSecondLevel.getLevel()).thenReturn(DevelopmentCardLevel.SECOND_LEVEL);
        lenient().when(rightCardFirstLevel.getLevel()).thenReturn(DevelopmentCardLevel.FIRST_LEVEL);
        lenient().when(rightCardThirdLevel.getLevel()).thenReturn(DevelopmentCardLevel.THIRD_LEVEL);
    }

    @Test
    void testNotAvailableCard(){

        DevelopmentActionClientRequest request = new DevelopmentActionClientRequest (
            player,
            mock(DevelopmentCard.class),
            1
        );

        assertTrue(validator.getErrorMessage(request, gameManager).isPresent());
    }

    @Test
    void testNotEnoughResources(){

        DevelopmentActionClientRequest request1 = new DevelopmentActionClientRequest (
            player,
            rightCardThirdLevel,
            1
        );

        DevelopmentActionClientRequest request2 = new DevelopmentActionClientRequest (
            player,
            rightCardSecondLevel,
            1
        );

        assertTrue(validator.getErrorMessage(request1, gameManager).isPresent());
        assertTrue(validator.getErrorMessage(request2, gameManager).isEmpty());
    }

    @Test
    void testWrongLevelOfCardToAddOnDeck(){

        DevelopmentActionClientRequest request1 = new DevelopmentActionClientRequest (
            player,
            rightCardFirstLevel,
            1
        );

        DevelopmentActionClientRequest request2 = new DevelopmentActionClientRequest (
            player,
            rightCardSecondLevel,
            1
        );

        assertTrue(validator.getErrorMessage(request1, gameManager).isPresent());
        assertTrue(validator.getErrorMessage(request2, gameManager).isEmpty());
    }


    @Override
    DevelopmentActionClientRequest createClientRequestToValidate() {
        return new DevelopmentActionClientRequest(
            player,
            mock(DevelopmentCard.class),
            1
        );
    }

    @Override
    Class<DevelopmentActionClientRequestValidator> getValidatorType() {
        return DevelopmentActionClientRequestValidator.class;
    }
}