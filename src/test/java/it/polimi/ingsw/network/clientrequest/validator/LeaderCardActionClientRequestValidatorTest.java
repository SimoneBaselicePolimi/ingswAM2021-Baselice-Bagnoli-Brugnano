package it.polimi.ingsw.network.clientrequest.validator;

import it.polimi.ingsw.network.clientrequest.ActivateLeaderCardClientRequest;
import it.polimi.ingsw.network.clientrequest.ClientRequest;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import org.junit.internal.requests.ClassRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

abstract class LeaderCardActionClientRequestValidatorTest<R extends ClientRequest, V extends ClientRequestValidator<R>>
    extends ValidatorTest<R, V> {

    V leaderActionValidator;

    @BeforeEach
    void setUp() {
        leaderActionValidator = getValidator();
    }

    abstract R createLeaderCardActionRequest(LeaderCard leaderCard);

    @Test
    void testThrowsErrorIfLeaderCardIsNotHidden() {
        LeaderCard l1 = mock(LeaderCard.class);
        LeaderCard l2 = mock(LeaderCard.class);
        LeaderCard l3 = mock(LeaderCard.class);
        when(playerContext.getLeaderCards()).thenReturn(Set.of(l1, l2, l3));

        when(l1.getState()).thenReturn(LeaderCardState.HIDDEN);
        lenient().when(l1.areRequirementsSatisfied(any())).thenReturn(true);
        when(l2.getState()).thenReturn(LeaderCardState.DISCARDED);
        lenient().when(l2.areRequirementsSatisfied(any())).thenReturn(true);
        when(l3.getState()).thenReturn(LeaderCardState.ACTIVE);
        lenient().when(l3.areRequirementsSatisfied(any())).thenReturn(true);

        assertValidatorDoesNotThrowError(createLeaderCardActionRequest(l1));
        assertValidatorThrowsError(createLeaderCardActionRequest(l2));
        assertValidatorThrowsError(createLeaderCardActionRequest(l3));

    }

    @Test
    void testThrowsErrorIfPlayerDoesNotOwnLeaderCard() {

        LeaderCard l = mock(LeaderCard.class);
        lenient().when(l.getState()).thenReturn(LeaderCardState.HIDDEN);
        when(playerContext.getLeaderCards()).thenReturn(Set.of(mock(LeaderCard.class), mock(LeaderCard.class)));

        assertValidatorThrowsError(createLeaderCardActionRequest(l));
    }

}
