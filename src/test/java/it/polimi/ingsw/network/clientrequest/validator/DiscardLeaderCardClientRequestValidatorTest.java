package it.polimi.ingsw.network.clientrequest.validator;

import it.polimi.ingsw.network.clientrequest.ActivateLeaderCardClientRequest;
import it.polimi.ingsw.network.clientrequest.DiscardLeaderCardClientRequest;
import it.polimi.ingsw.network.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.cardstack.ForbiddenPushOnTopException;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;
import it.polimi.ingsw.testutils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

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
            mock(LeaderCard.class)
        );
    }

    @Override
    public Class<DiscardLeaderCardClientRequestValidator> getValidatorType() {
        return DiscardLeaderCardClientRequestValidator.class;
    }
}