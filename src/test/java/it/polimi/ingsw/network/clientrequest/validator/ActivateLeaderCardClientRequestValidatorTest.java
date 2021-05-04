package it.polimi.ingsw.network.clientrequest.validator;

import it.polimi.ingsw.network.clientrequest.DiscardLeaderCardClientRequest;
import it.polimi.ingsw.network.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.cardstack.ForbiddenPushOnTopException;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class ActivateLeaderCardClientRequestValidatorTest {

    @Mock
    GameManager gameManager;

    @Mock
    GameContext gameContext;

    @Mock
    PlayerContext playerContext;

    @Mock
    DiscardLeaderCardClientRequestValidator validator;

    @Mock
    LeaderCard l1;

    @Mock
    LeaderCard l2;

    @Mock
    LeaderCard l3;

    @Mock
    LeaderCard l4;

    @Mock
    Player player;

    Set<LeaderCard> leaderCardsOwnedByThePlayer;
    Set<LeaderCard> leaderCardsThePlayerWantsToActivate1;
    Set<LeaderCard> leaderCardsThePlayerWantsToActivate2;

    @BeforeEach
    void setUp() throws ResourceStorageRuleViolationException, NotEnoughResourcesException, LeaderCardRequirementsNotSatisfiedException, ForbiddenPushOnTopException {

        when(gameManager.getGameContext()).thenReturn(gameContext);
        when(gameContext.getPlayerContext(player)).thenReturn(playerContext);
        when(playerContext.getLeaderCards()).thenReturn(leaderCardsOwnedByThePlayer);
    }

    @Test
    void testGetError() throws ResourceStorageRuleViolationException, NotEnoughResourcesException, LeaderCardRequirementsNotSatisfiedException, ForbiddenPushOnTopException {

        leaderCardsOwnedByThePlayer= Set.of(l1, l2);
        leaderCardsThePlayerWantsToActivate1 = Set.of (l1);
        leaderCardsThePlayerWantsToActivate2 = Set.of (l3, l4);

        DiscardLeaderCardClientRequest request1 = new DiscardLeaderCardClientRequest (
            player,
            leaderCardsThePlayerWantsToActivate1
        );

        DiscardLeaderCardClientRequest request2 = new DiscardLeaderCardClientRequest (
            player,
            leaderCardsThePlayerWantsToActivate2
        );


        assertTrue(validator.getErrorMessage(request1, gameManager).isEmpty());
        assertTrue(validator.getErrorMessage(request2, gameManager).isPresent());
        assertTrue(validator.getErrorMessage(request2, gameManager).isPresent());
        assertTrue(validator.getErrorMessage(request1, gameManager).isPresent());
    }
}