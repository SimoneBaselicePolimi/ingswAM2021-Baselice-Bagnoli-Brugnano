package it.polimi.ingsw.network.clientrequest.validator;

import it.polimi.ingsw.network.clientrequest.DiscardLeaderCardClientRequest;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardImp;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class DiscardLeaderCardClientRequestValidatorTest
    extends LeaderCardActionClientRequestValidatorTest<DiscardLeaderCardClientRequest, DiscardLeaderCardClientRequestValidator> {

    @Override
    DiscardLeaderCardClientRequest createLeaderCardActionRequest(LeaderCard leaderCard) {
        return new DiscardLeaderCardClientRequest(
            player,
            leaderCard
        );
    }

    @Override
    public DiscardLeaderCardClientRequest createClientRequestToValidate() {
        return new DiscardLeaderCardClientRequest(
            player,
            mock(LeaderCardImp.class)
        );
    }

    @Override
    public Class<DiscardLeaderCardClientRequestValidator> getValidatorType() {
        return DiscardLeaderCardClientRequestValidator.class;
    }
}