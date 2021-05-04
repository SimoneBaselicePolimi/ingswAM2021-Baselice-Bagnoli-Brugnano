package it.polimi.ingsw.network.clientrequest.validator;
import it.polimi.ingsw.network.clientrequest.MarketActionFetchColumnClientRequest;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class MarketActionFetchColumnClientRequestValidatorTest {

    @Mock
    GameManager gameManager;

    @Mock
    GameContext gameContext;

    @Mock
    Market market;

    @Mock
    MarketActionFetchColumnClientRequestValidator validator;

    @Mock
    Player player;

    @BeforeEach
    void setUp() {

        when(gameManager.getGameContext()).thenReturn(gameContext);
        when(gameContext.getMarket()).thenReturn(market);
        when(market.getNumOfColumns()).thenReturn(4);
    }

    @Test
    void testGetError(){

        int validColumn1 = 3;
        int validColumn2 = 0;
        int invalidColumn1 = -1;
        int invalidColumn2 = 6;
        int invalidColumn3 = 4;

        MarketActionFetchColumnClientRequest validRequest1 = new MarketActionFetchColumnClientRequest(
            player,
            validColumn1
        );

        MarketActionFetchColumnClientRequest validRequest2 = new MarketActionFetchColumnClientRequest(
            player,
            validColumn2
        );

        MarketActionFetchColumnClientRequest invalidRequest1 = new MarketActionFetchColumnClientRequest(
            player,
            invalidColumn1
        );

        MarketActionFetchColumnClientRequest invalidRequest2 = new MarketActionFetchColumnClientRequest(
            player,
            invalidColumn2
        );

        MarketActionFetchColumnClientRequest invalidRequest3 = new MarketActionFetchColumnClientRequest(
            player,
            invalidColumn3
        );


        assertTrue(validator.getErrorMessage(validRequest1, gameManager).isEmpty());
        assertTrue(validator.getErrorMessage(validRequest2, gameManager).isEmpty());
        assertTrue(validator.getErrorMessage(invalidRequest1, gameManager).isPresent());
        assertTrue(validator.getErrorMessage(invalidRequest2, gameManager).isPresent());
        assertTrue(validator.getErrorMessage(invalidRequest3, gameManager).isPresent());
    }

}