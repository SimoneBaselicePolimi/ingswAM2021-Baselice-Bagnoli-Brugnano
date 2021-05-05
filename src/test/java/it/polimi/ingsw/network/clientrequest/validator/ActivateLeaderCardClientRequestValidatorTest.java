package it.polimi.ingsw.network.clientrequest.validator;

import it.polimi.ingsw.network.clientrequest.ActivateLeaderCardClientRequest;
import it.polimi.ingsw.network.clientrequest.DiscardLeaderCardClientRequest;
import it.polimi.ingsw.network.clientrequest.ManageResourcesFromMarketClientRequest;
import it.polimi.ingsw.network.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.cardstack.ForbiddenPushOnTopException;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class ActivateLeaderCardClientRequestValidatorTest {

    @Mock
    GameManager gameManager;

    @Mock
    GameContext gameContext;

    @Mock
    PlayerContext playerContext;

    ActivateLeaderCardClientRequestValidator validator = new ActivateLeaderCardClientRequestValidator();

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

    Set<LeaderCard> leaderCardsThePlayerWantsToActivate1;
    Set<LeaderCard> leaderCardsThePlayerWantsToActivate2;

    @BeforeEach
    void setUp() {

        lenient().when(gameManager.getGameContext()).thenReturn(gameContext);
        lenient().when(gameContext.getPlayerContext(player)).thenReturn(playerContext);
        lenient().when(gameContext.getActivePlayer()).thenReturn(player);
        lenient().when(playerContext.getLeaderCards()).thenReturn(Set.of(l1, l2));
        lenient().when(l1.getState()).thenReturn(LeaderCardState.HIDDEN);
        lenient().when(l2.getState()).thenReturn(LeaderCardState.HIDDEN);
    }

    @Test
    void getValidatorFromClientRequest() {
        ActivateLeaderCardClientRequest request = new ActivateLeaderCardClientRequest(
            player,
            leaderCardsThePlayerWantsToActivate1
        );
        assertTrue(request.getValidator() instanceof ActivateLeaderCardClientRequestValidator);
    }

    @Test
    void testGetError() {

        leaderCardsThePlayerWantsToActivate1 = Set.of (l1);
        leaderCardsThePlayerWantsToActivate2 = Set.of (l3, l4);

        ActivateLeaderCardClientRequest request1 = new ActivateLeaderCardClientRequest (
            player,
            leaderCardsThePlayerWantsToActivate1
        );

        ActivateLeaderCardClientRequest request2 = new ActivateLeaderCardClientRequest(
            player,
            leaderCardsThePlayerWantsToActivate2
        );

        assertTrue(validator.getErrorMessage(request1, gameManager).isEmpty());
        assertTrue(validator.getErrorMessage(request2, gameManager).isPresent());
        assertTrue(validator.getErrorMessage(request2, gameManager).isPresent());
        assertTrue(validator.getErrorMessage(request1, gameManager).isPresent());
    }
}