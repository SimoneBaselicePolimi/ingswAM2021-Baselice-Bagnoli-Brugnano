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

    MarketActionFetchColumnClientRequestValidator validator = new MarketActionFetchColumnClientRequestValidator();

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

        assertTrue(validator.getErrorMessage(new MarketActionFetchColumnClientRequest(
                player,
                3
            ), gameManager
        ).isEmpty());

        assertTrue(validator.getErrorMessage(new MarketActionFetchColumnClientRequest(
                player,
                0
            ), gameManager
        ).isEmpty());

        assertTrue(validator.getErrorMessage(new MarketActionFetchColumnClientRequest(
                player,
                -1
            ), gameManager
        ).isPresent());

        assertTrue(validator.getErrorMessage(new MarketActionFetchColumnClientRequest(
                player,
                6
            ), gameManager
        ).isPresent());

        assertTrue(validator.getErrorMessage(new MarketActionFetchColumnClientRequest(
                player,
                4
            ), gameManager
        ).isPresent());

    }
}