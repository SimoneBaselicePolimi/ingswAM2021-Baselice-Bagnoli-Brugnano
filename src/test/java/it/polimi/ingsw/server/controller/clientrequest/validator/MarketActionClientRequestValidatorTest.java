package it.polimi.ingsw.server.controller.clientrequest.validator;

import it.polimi.ingsw.server.controller.clientrequest.ClientRequest;
import it.polimi.ingsw.server.model.gamecontext.market.Market;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

abstract class MarketActionClientRequestValidatorTest<R extends ClientRequest, V extends ClientRequestValidator<R>>
    extends ValidatorTest<R, V> {

    V marketActionValidator;

    @Mock
    Market market;

    @BeforeEach
    void setUp() {
        marketActionValidator = getValidator();
        lenient().when(gameContext.getMarket()).thenReturn(market);
        lenient().when(market.getNumOfColumns()).thenReturn(4);
        lenient().when(market.getNumOfRows()).thenReturn(4);
    }

    abstract R createMarketActionRequest(int num);


    @Test
    void testGetError() {
        assertValidatorDoesNotThrowError(createMarketActionRequest(3));
        assertValidatorDoesNotThrowError(createMarketActionRequest(0));
        assertValidatorThrowsError(createMarketActionRequest(-1));
        assertValidatorThrowsError(createMarketActionRequest(6));
        assertValidatorThrowsError(createMarketActionRequest(4));
    }
}
