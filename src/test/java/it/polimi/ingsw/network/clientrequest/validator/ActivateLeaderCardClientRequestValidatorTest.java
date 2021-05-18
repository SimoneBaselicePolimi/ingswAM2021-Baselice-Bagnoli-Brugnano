package it.polimi.ingsw.network.clientrequest.validator;

import it.polimi.ingsw.network.clientrequest.ActivateLeaderCardClientRequest;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardImp;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class ActivateLeaderCardClientRequestValidatorTest
    extends LeaderCardActionClientRequestValidatorTest<ActivateLeaderCardClientRequest, ActivateLeaderCardClientRequestValidator> {

    @Override
    public ActivateLeaderCardClientRequest createClientRequestToValidate() {
        return new ActivateLeaderCardClientRequest(
            player,
            mock(LeaderCardImp.class)
        );
    }

    @Override
    public Class<ActivateLeaderCardClientRequestValidator> getValidatorType() {
        return ActivateLeaderCardClientRequestValidator.class;
    }

    @Override
    ActivateLeaderCardClientRequest createLeaderCardActionRequest(LeaderCard leaderCard) {
        return new ActivateLeaderCardClientRequest(
            player,
            leaderCard
        );
    }

    @Test
    void testThrowsErrorIfLeaderCardDoestNotSatisfyRequirements() {

        LeaderCard l1 = mock(LeaderCardImp.class);
        when(l1.getState()).thenReturn(LeaderCardState.HIDDEN);
        when(l1.areRequirementsSatisfied(any())).thenReturn(true);

        LeaderCard l2 = mock(LeaderCardImp.class);
        when(l2.getState()).thenReturn(LeaderCardState.HIDDEN);
        when(l2.areRequirementsSatisfied(any())).thenReturn(false);

        when(playerContext.getLeaderCards()).thenReturn(Set.of(l1, l2));

        assertValidatorDoesNotThrowError(createLeaderCardActionRequest(l1));
        assertValidatorThrowsError(createLeaderCardActionRequest(l2));
    }
}