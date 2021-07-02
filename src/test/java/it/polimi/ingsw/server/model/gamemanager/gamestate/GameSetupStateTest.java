package it.polimi.ingsw.server.model.gamemanager.gamestate;

import it.polimi.ingsw.configfile.GameInfoConfig;
import it.polimi.ingsw.configfile.GameRules;
import it.polimi.ingsw.server.controller.clientrequest.InitialChoicesClientRequest;
import it.polimi.ingsw.server.controller.servermessage.GameUpdateServerMessage;
import it.polimi.ingsw.server.controller.servermessage.InitialChoicesServerMessage;
import it.polimi.ingsw.server.controller.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.gameactionshistory.SetupChoiceAction;
import it.polimi.ingsw.server.gameactionshistory.SetupStartedAction;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameupdate.ServerFaithUpdate;
import it.polimi.ingsw.server.model.gameupdate.ServerGameUpdate;
import it.polimi.ingsw.server.model.gameupdate.ServerLeaderCardsThePlayerOwnsUpdate;
import it.polimi.ingsw.server.model.gameupdate.ServerMarketUpdate;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;
import it.polimi.ingsw.testutils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameSetupStateTest extends GameStateTest {

    Set<LeaderCard> leaderCards;

    int maxNumberOfPlayers = 3;
    int numberOfLeadersCardsGivenToThePlayer = 4;
    int numberOfLeadersCardsThePlayerKeeps = 2;

    Map<Player, Integer> playerInitialFaithPoints;
    Map<Player, Integer> playerInitialStarResources;

    GameRules gameRules;

    GameSetupState state;

    @BeforeEach
    void setUp() {

        playerInitialFaithPoints = Map.of(
            player1, 0,
            player2, 5,
            player3, 3
        );

        playerInitialStarResources = Map.of(
            player1, 0,
            player2, 1,
            player3, 2
        );

        leaderCards = TestUtils.generateSetOfMockWithID(LeaderCard.class, 15);

        Map<Integer, GameInfoConfig.GameSetup.InitialPlayerResourcesAndFaithPoints> initialResources = new HashMap<>();
        for (int i = 0; i < playersInOrder.size(); i++){
            Player player = playersInOrder.get(i);
            initialResources.put(
                i + 1,
                new GameInfoConfig.GameSetup.InitialPlayerResourcesAndFaithPoints(
                    playerInitialStarResources.get(player),
                    playerInitialFaithPoints.get(player))
                );
        }

        gameRules = new GameRules(
            new GameInfoConfig(
                maxNumberOfPlayers,
                false,
                new GameInfoConfig.GameSetup(
                    numberOfLeadersCardsGivenToThePlayer,
                    numberOfLeadersCardsThePlayerKeeps,
                    initialResources
                ),
                0,
                null,
                null,
                false,
                0
            ),
            null,
            null,
            null,
            null
        );

        when(gameItemsManager.getAllItemsOfType(eq(LeaderCard.class))).thenReturn(leaderCards);
        when(gameManager.getGameRules()).thenReturn(gameRules);

        state = new GameSetupState(gameManager);

    }


    @Test
    void testRandomShuffle() {

        GameSetupState state1 = new GameSetupState(new Random(1), gameManager);
        GameSetupState state1Copy = new GameSetupState(new Random(1), gameManager);
        GameSetupState state2 = new GameSetupState(new Random(2), gameManager);

        assertEquals(state1.leaderCardsGivenToThePlayers, state1Copy.leaderCardsGivenToThePlayers);
        assertNotEquals(state1.leaderCardsGivenToThePlayers, state2.leaderCardsGivenToThePlayers);
    }


    @Test
    void testInitialServerMessage() {

        Map<Player, InitialChoicesServerMessage> serverMessages = state.getInitialServerMessage();
        Set<LeaderCard> alreadyAssignedCard = new HashSet<>();
        for (Player player : serverMessages.keySet()) {

            InitialChoicesServerMessage message = serverMessages.get(player);

            assertEquals(
                playerInitialStarResources.get(player),
                message.numberOfStarResources
            );

            assertEquals(numberOfLeadersCardsGivenToThePlayer, message.leaderCardsGivenToThePlayer.size());
            message.leaderCardsGivenToThePlayer.forEach(c -> assertFalse(alreadyAssignedCard.contains(c)));
            alreadyAssignedCard.addAll(message.leaderCardsGivenToThePlayer);

            verifyMessageContainsAllGameUpdates(message);
        }

        verifyGameHistoryActionAdded(SetupStartedAction.class);
    }

    @Test
    void testHandleInitialChoiceCR() throws ResourceStorageRuleViolationException {

        Map<Player, InitialChoicesServerMessage> initialMessagesFromServer = state.getInitialServerMessage();
        InitialChoicesServerMessage messageForPlayer = initialMessagesFromServer.get(player2);

        Set<LeaderCard> leaderCardsChosenForPlayer2 = new HashSet<>();
        Iterator<LeaderCard> cardsThePlayerCanChooseFromIterator = messageForPlayer.leaderCardsGivenToThePlayer.iterator();
        for(int i = 0; i < numberOfLeadersCardsThePlayerKeeps; i++)
                leaderCardsChosenForPlayer2.add(cardsThePlayerCanChooseFromIterator.next());

        Iterator<ResourceStorage> shelvesIter = shelvesForPlayers.get(player2).iterator();
        ResourceStorage playerShelve1 = shelvesIter.next();
        ResourceStorage playerShelve2 = shelvesIter.next();
        Map<ResourceType, Integer> resourcesChosenForShelve1 = Map.of(
            ResourceType.STONES, 2
        );
        Map<ResourceType, Integer> resourcesChosenForShelve2 = Map.of(
            ResourceType.SHIELDS, 2,
            ResourceType.COINS, 1
        );

        ServerGameUpdate leaderCardUpdatePlayer1 = new ServerLeaderCardsThePlayerOwnsUpdate(player1, new HashSet<>());
        ServerGameUpdate leaderCardUpdatePlayer2 = new ServerLeaderCardsThePlayerOwnsUpdate(player2, new HashSet<>());
        ServerGameUpdate leaderCardUpdatePlayer3 = new ServerLeaderCardsThePlayerOwnsUpdate(player3, new HashSet<>());
        ServerGameUpdate otherUpdate1 = new ServerMarketUpdate(null, null);
        ServerGameUpdate otherUpdate2 = new ServerFaithUpdate(player1, 0);
        Set<ServerGameUpdate> allTestGameUpdates = Set.of(
            leaderCardUpdatePlayer1,
            leaderCardUpdatePlayer2,
            leaderCardUpdatePlayer3,
            otherUpdate1,
            otherUpdate2
        );
        when(gameManager.getAllGameUpdates()).thenReturn(allTestGameUpdates);


        //Send valid request
        Map<Player, ServerMessage> validRequestAnswerServerMessages = state.handleInitialChoiceCR(
            new InitialChoicesClientRequest(
                player2,
                leaderCardsChosenForPlayer2,
                Map.of(
                    playerShelve1, resourcesChosenForShelve1,
                    playerShelve2, resourcesChosenForShelve2
                )
            )
        );

        verify(playerContext2).setLeaderCards(eq(leaderCardsChosenForPlayer2));

        verify(faithPath).move(player2, playerInitialFaithPoints.get(player2));

        verify(playerShelve1).addResources(eq(resourcesChosenForShelve1));
        verify(playerShelve2).addResources(eq(resourcesChosenForShelve2));

        verifyGameHistoryActionAdded(SetupChoiceAction.class);

        verifyThereIsAValidServerMessageForEveryPlayer(validRequestAnswerServerMessages);

        Set<ServerGameUpdate> gameUpdatesVisibleForPlayer1 = Set.of(
            leaderCardUpdatePlayer1,
            otherUpdate1,
            otherUpdate2
        );
        Set<ServerGameUpdate> gameUpdatesVisibleForPlayer2 = Set.of(
            leaderCardUpdatePlayer2,
            otherUpdate1,
            otherUpdate2
        );

        assertEquals(gameUpdatesVisibleForPlayer1, ((GameUpdateServerMessage)validRequestAnswerServerMessages.get(player1)).gameUpdates);
        assertEquals(gameUpdatesVisibleForPlayer2, ((GameUpdateServerMessage)validRequestAnswerServerMessages.get(player2)).gameUpdates);


        //Send duplicated request
        Map<Player, ServerMessage> duplicatedRequestAnswerServerMessages = state.handleInitialChoiceCR(
            new InitialChoicesClientRequest(
                player2,
                leaderCardsChosenForPlayer2,
                Map.of(
                    playerShelve1, resourcesChosenForShelve1,
                    playerShelve2, resourcesChosenForShelve2
                )
            )
        );

        verifyServerMessageIsAnswerForInvalidRequest(player2, duplicatedRequestAnswerServerMessages);


        //Send request with invalid leader cards
        Map<Player, ServerMessage> wrongLeaderCardRequestAnswerServerMessages = state.handleInitialChoiceCR(
            new InitialChoicesClientRequest(
                player1,
                leaderCardsChosenForPlayer2,
                Map.of()
            )
        );

        verifyServerMessageIsAnswerForInvalidRequest(player1, wrongLeaderCardRequestAnswerServerMessages);

    }

    @Test
    void testGetFinalServerMessage(){
        GameSetupState state = new GameSetupState(gameManager);
        assertEquals(playersInOrder.size(), state.getFinalServerMessage().size());
    }

    @Test
    void testStateLifecycle() throws ResourceStorageRuleViolationException {

        assertFalse(state.isStateDone());

        Map<Player, InitialChoicesServerMessage> initialMessagesFromServer = state.getInitialServerMessage();
        assertFalse(state.isStateDone());

        //send an InitialChoicesClientRequest for each player
        Iterator<Player> playerIterator = initialMessagesFromServer.keySet().iterator();
        while(playerIterator.hasNext()) {

            Player player = playerIterator.next();
            InitialChoicesServerMessage messageForPlayer = initialMessagesFromServer.get(player);
            Set<LeaderCard> leaderCardsChosen = new HashSet<>();
            Iterator<LeaderCard> cardsThePlayerCanChooseFromIterator = messageForPlayer.leaderCardsGivenToThePlayer.iterator();
            for(int i = 0; i < numberOfLeadersCardsThePlayerKeeps; i++)
                leaderCardsChosen.add(cardsThePlayerCanChooseFromIterator.next());
            ResourceStorage playerShelve1 = shelvesForPlayers.get(player).iterator().next();
            state.handleInitialChoiceCR(
                new InitialChoicesClientRequest(
                    player,
                    leaderCardsChosen,
                    Map.of(
                        playerShelve1,
                        Map.of(ResourceType.STONES, messageForPlayer.numberOfStarResources)
                    )
                )
            );

            if(playerIterator.hasNext())
                assertFalse(state.isStateDone());
            else
                assertTrue(state.isStateDone()); //after the last player has sent the request isStateDone should be true
        }

        assertNotNull(state.getNextState());
    }

}
