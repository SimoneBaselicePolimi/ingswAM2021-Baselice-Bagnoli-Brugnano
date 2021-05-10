package it.polimi.ingsw.network.clientrequest.validator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import it.polimi.ingsw.network.clientrequest.DevelopmentActionClientRequest;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.cardstack.ForbiddenPushOnTopException;
import it.polimi.ingsw.server.model.gameitems.cardstack.ShuffledCardDeck;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTable;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;
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

    @Mock
    DevelopmentCard notAvailableDevelopmentCard;

    @Mock
    ShuffledCardDeck deck;

    List<DevelopmentCard> availableDevelopmentCards;

    Map<ResourceType, Integer> resources = Map.of(
        ResourceType.COINS, 3,
        ResourceType.SERVANTS, 2
    );

    Iterator<DevelopmentCard> iter;
    DevelopmentCard rightCardThirdLevel;
    DevelopmentCard rightCardSecondLevel;
    DevelopmentCard rightCardFirstLevel;
    DevelopmentCard notEnoughResourcesCard;

    @BeforeEach
    void setUp(){

        availableDevelopmentCards = TestUtils.generateListOfMockWithID(DevelopmentCard.class, 4);

        iter = availableDevelopmentCards.iterator();
        rightCardThirdLevel = iter.next();
        rightCardSecondLevel = iter.next();
        rightCardFirstLevel = iter.next();
        notEnoughResourcesCard = iter.next();


        lenient().when(gameContext.getDevelopmentCardsTable()).thenReturn(table);
        lenient().when(table.getAvailableCards()).thenReturn(availableDevelopmentCards);
        lenient().when(playerContext.getAllResources()).thenReturn(resources);
        lenient().when(rightCardFirstLevel.getPurchaseCost()).thenReturn(Map.of(ResourceType.COINS, 2));
        lenient().when(rightCardSecondLevel.getPurchaseCost()).thenReturn(Map.of(ResourceType.COINS, 2));
        lenient().when(rightCardThirdLevel.getPurchaseCost()).thenReturn(Map.of(ResourceType.COINS, 2));
        lenient().when(notEnoughResourcesCard.getPurchaseCost()).thenReturn(Map.of(ResourceType.SERVANTS, 4));
        lenient().when(rightCardSecondLevel.getLevel()).thenReturn(DevelopmentCardLevel.SECOND_LEVEL);
        lenient().when(rightCardFirstLevel.getLevel()).thenReturn(DevelopmentCardLevel.FIRST_LEVEL);
        lenient().when(rightCardThirdLevel.getLevel()).thenReturn(DevelopmentCardLevel.THIRD_LEVEL);

    }

    @Test
    void

    @Test
    void testGetError(){
        int deckNumber = 1;

        DevelopmentActionClientRequest firstLevelCardRequest = new DevelopmentActionClientRequest (
            player,
            rightCardFirstLevel,
            deckNumber
        );

        DevelopmentActionClientRequest differentCardRequest = new DevelopmentActionClientRequest (
            player,
            notAvailableDevelopmentCard,
            deckNumber
        );

        DevelopmentActionClientRequest notEnoughResourcesCardRequest = new DevelopmentActionClientRequest (
            player,
            notEnoughResourcesCard,
            deckNumber
        );

        DevelopmentActionClientRequest thirdLevelCardRequest = new DevelopmentActionClientRequest (
            player,
            rightCardThirdLevel,
            deckNumber
        );

        DevelopmentActionClientRequest secondLevelCardRequest = new DevelopmentActionClientRequest (
            player,
            rightCardSecondLevel,
            deckNumber
        );

        assertTrue(validator.getErrorMessage(firstLevelCardRequest, gameManager).isEmpty());
        assertTrue(validator.getErrorMessage(firstLevelCardRequest, gameManager).isPresent());
        assertTrue(validator.getErrorMessage(differentCardRequest, gameManager).isPresent());
        assertTrue(validator.getErrorMessage(notEnoughResourcesCardRequest, gameManager).isPresent());
        assertTrue(validator.getErrorMessage(thirdLevelCardRequest, gameManager).isPresent());
        assertTrue(validator.getErrorMessage(secondLevelCardRequest, gameManager).isPresent());
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