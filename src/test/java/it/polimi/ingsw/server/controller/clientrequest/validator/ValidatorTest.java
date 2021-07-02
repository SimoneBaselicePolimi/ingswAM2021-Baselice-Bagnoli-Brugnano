package it.polimi.ingsw.server.controller.clientrequest.validator;

import it.polimi.ingsw.server.controller.clientrequest.ClientRequest;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;

public abstract class ValidatorTest<R extends ClientRequest, V extends ClientRequestValidator<R>> {

    @Mock
    GameManager gameManager;

    @Mock
    GameContext gameContext;

    @Mock
    PlayerContext playerContext;

    @Mock
    Player player;

    @BeforeEach
    void validatorTestSetUp() {
        lenient().when(gameManager.getGameContext()).thenReturn(gameContext);
        lenient().when(gameContext.getPlayerContext(player)).thenReturn(playerContext);
        lenient().when(gameContext.getActivePlayer()).thenReturn(player);
    }

    abstract R createClientRequestToValidate();

    abstract Class<V> getValidatorType();

    @Test
    void getValidatorFromClientRequest() {
        R clientRequest = createClientRequestToValidate();
        assertEquals(getValidatorType(), clientRequest.getValidator().getClass());
    }

    @SuppressWarnings("unchecked")
    protected V getValidator() {
        return (V) createClientRequestToValidate().getValidator();
    }

    protected void assertValidatorThrowsError(R request) {
        assertTrue(getValidator().getErrorMessage(request, gameManager).isPresent());
    }

    protected void assertValidatorDoesNotThrowError(R request) {
        assertTrue(getValidator().getErrorMessage(request, gameManager).isEmpty());
    }
}
