package it.polimi.ingsw.network.clientrequest.validator;
import it.polimi.ingsw.network.clientrequest.MarketActionFetchColumnClientRequest;
import it.polimi.ingsw.network.clientrequest.MarketActionFetchRowClientRequest;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.gamecontext.market.Market;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MarketActionFetchRowClientRequestValidatorTest {

    @Mock
    GameManager gameManager;

    @Mock
    GameContext gameContext;

    @Mock
    Market market;

    MarketActionFetchRowClientRequestValidator validator = new MarketActionFetchRowClientRequestValidator();

    @Mock
    Player player;

    @BeforeEach
    void setUp() {

        lenient().when(gameManager.getGameContext()).thenReturn(gameContext);
        lenient().when(gameContext.getMarket()).thenReturn(market);
        lenient().when(market.getNumOfRows()).thenReturn(3);
    }

    @Test
    void getValidatorFromClientRequest() {
        MarketActionFetchRowClientRequest request = new MarketActionFetchRowClientRequest(
            player,
            2
        );
        assertTrue(request.getValidator() instanceof MarketActionFetchRowClientRequestValidator);
    }

    @Test
    void testGetError(){

        assertTrue(validator.getErrorMessage(new MarketActionFetchRowClientRequest(
                player,
                2
            ), gameManager
        ).isEmpty());

        assertTrue(validator.getErrorMessage(new MarketActionFetchRowClientRequest(
                player,
                0
            ), gameManager
        ).isEmpty());

        assertTrue(validator.getErrorMessage(new MarketActionFetchRowClientRequest(
                player,
                -1
            ), gameManager
        ).isPresent());

        assertTrue(validator.getErrorMessage(new MarketActionFetchRowClientRequest(
                player,
                10
            ), gameManager
        ).isPresent());

        assertTrue(validator.getErrorMessage(new MarketActionFetchRowClientRequest(
                player,
                3
            ), gameManager
        ).isPresent());

    }
}