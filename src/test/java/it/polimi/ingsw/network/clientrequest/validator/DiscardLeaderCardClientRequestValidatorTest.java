package it.polimi.ingsw.network.clientrequest.validator;

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

class DiscardLeaderCardClientRequestValidatorTest {

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

    InvalidRequestServerMessage invalidRequest1 = new InvalidRequestServerMessage(
        "The leader card cannot be discarded: the player does not own this card");
    InvalidRequestServerMessage invalidRequest2 = new InvalidRequestServerMessage(
        "The leader card cannot be discarded: " +
        "the player no longer has the card in his hand (the state is not HIDDEN)");

    Set<LeaderCard> leaderCardsOwnedByThePlayer;
    Set<LeaderCard> leaderCardsThePlayerWantsToDiscard1;
    Set<LeaderCard> leaderCardsThePlayerWantsToDiscard2;

    @BeforeEach
    void setUp() throws ResourceStorageRuleViolationException, NotEnoughResourcesException, LeaderCardRequirementsNotSatisfiedException, ForbiddenPushOnTopException {

        when(gameManager.getGameContext()).thenReturn(gameContext);
        when(gameContext.getPlayerContext(player)).thenReturn(playerContext);
        when(playerContext.getLeaderCards()).thenReturn(leaderCardsOwnedByThePlayer);
    }

    @Test
    void testGetError() throws ResourceStorageRuleViolationException, NotEnoughResourcesException, LeaderCardRequirementsNotSatisfiedException, ForbiddenPushOnTopException {

        leaderCardsOwnedByThePlayer= Set.of(l1, l2);
        leaderCardsThePlayerWantsToDiscard1 = Set.of (l1);
        leaderCardsThePlayerWantsToDiscard2 = Set.of (l3, l4);

        DiscardLeaderCardClientRequest request1 = new DiscardLeaderCardClientRequest (
            player,
            leaderCardsThePlayerWantsToDiscard1
        );

        DiscardLeaderCardClientRequest request2 = new DiscardLeaderCardClientRequest (
            player,
            leaderCardsThePlayerWantsToDiscard2
        );

        Map<Player, ServerMessage> map1 = new HashMap<>();
        map1.put(player, invalidRequest1);

        Map<Player, ServerMessage> map2 = new HashMap<>();
        map2.put(player, invalidRequest2);

        Optional<String> optional = Optional.empty();
        assertEquals(validator.getErrorMessage(request1, gameManager), optional);
        assertEquals(validator.getErrorMessage(request2, gameManager), map1);
        assertEquals(validator.getErrorMessage(request1, gameManager), map2);
    }
}