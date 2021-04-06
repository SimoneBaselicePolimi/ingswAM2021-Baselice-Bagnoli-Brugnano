package it.polimi.ingsw.server.model.gamecontext;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPath;
import it.polimi.ingsw.server.model.gamecontext.market.Market;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameContextTest {

    @Mock
    Market market;

    @Mock
    DevelopmentCardsTable cardsTable;

    @Mock
    FaithPath faithPath;

    @Mock
    Player player1, player2, player3;

    @Mock
    PlayerContext player1Context, player2Context, player3Context;

    List<Player> playersOrder = List.of(player1, player2, player3);

    Map<Player, PlayerContext> playerContextMap = Map.of(
            player1, player1Context,
            player2, player2Context,
            player3, player3Context
    );

    GameContext gameContext;

    @BeforeEach
    public void setUp() {
        gameContext = new GameContext(market, cardsTable, faithPath, playersOrder, playerContextMap);
    }

    @Test
    void testGameContextCorrectlyInitialized() {
        assertEquals(market, gameContext.getMarket());
        assertEquals(cardsTable, gameContext.getDevelopmentCardsTable());
        assertEquals(faithPath, gameContext.getFaithPath());
        assertEquals(playersOrder, gameContext.getPlayersTurnOrder());
        assertEquals(player1Context, gameContext.getPlayerContext(player1));
        assertEquals(player2Context, gameContext.getPlayerContext(player2));
        assertEquals(player3Context, gameContext.getPlayerContext(player3));
    }

    @Test
    void getDevelopmentCardsPlayerCanBuy() {

        when(player1Context.getAllResources()).thenReturn(Map.of(
                ResourceType.COINS, 3,
                ResourceType.SHIELDS, 2
        ));

        when(player2Context.getAllResources()).thenReturn(Map.of(
                ResourceType.STONES, 10,
                ResourceType.COINS, 2
        ));

        DevelopmentCard card1 = mock(DevelopmentCard.class);
        when(card1.getPurchaseCost()).thenReturn(Map.of(
                ResourceType.COINS, 3,
                ResourceType.SHIELDS, 2
        ));

        DevelopmentCard card2 = mock(DevelopmentCard.class);
        when(card2.getPurchaseCost()).thenReturn(Map.of(
                ResourceType.COINS, 1
        ));

        DevelopmentCard card3 = mock(DevelopmentCard.class);
        when(card2.getPurchaseCost()).thenReturn(Map.of(
                ResourceType.STONES, 8
        ));

        DevelopmentCard card4 = mock(DevelopmentCard.class);
        when(card2.getPurchaseCost()).thenReturn(Map.of(
                ResourceType.SERVANTS, 1
        ));

        DevelopmentCardsTable cardsTable = mock(DevelopmentCardsTable.class);
        when(cardsTable.getAvailableCards()).thenReturn(List.of(card1, card2, card3, card4));

        assertEquals(Set.of(card1, card2), gameContext.getDevelopmentCardsPlayerCanBuy(player1));
        assertEquals(Set.of(card2, card3), gameContext.getDevelopmentCardsPlayerCanBuy(player2));

    }
}