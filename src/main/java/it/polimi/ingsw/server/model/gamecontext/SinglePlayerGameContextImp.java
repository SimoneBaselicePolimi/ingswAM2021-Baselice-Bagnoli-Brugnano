package it.polimi.ingsw.server.model.gamecontext;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPathImp;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPathSinglePlayer;
import it.polimi.ingsw.server.model.gamecontext.market.Market;
import it.polimi.ingsw.server.model.gamecontext.market.MarketImp;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContextImp;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTable;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTableImp;
import it.polimi.ingsw.server.model.gameitems.singleplayertokens.BlackCrossMoveAndTokensShuffleToken;
import it.polimi.ingsw.server.model.gameitems.singleplayertokens.BlackCrossMoveToken;
import it.polimi.ingsw.server.model.gameitems.singleplayertokens.DiscardCardsToken;
import it.polimi.ingsw.server.model.gameitems.singleplayertokens.SinglePlayerToken;

import java.util.*;

public class SinglePlayerGameContextImp extends GameContextImp implements SinglePlayerGameContext {

    public final List<SinglePlayerToken> SINGLE_PLAYER_TOKENS = List.of(
        new DiscardCardsToken(2, DevelopmentCardColour.GREEN),
        new DiscardCardsToken(2, DevelopmentCardColour.YELLOW),
        new DiscardCardsToken(2, DevelopmentCardColour.BLUE),
        new DiscardCardsToken(2, DevelopmentCardColour.PURPLE),
        new BlackCrossMoveToken(2),
        new BlackCrossMoveAndTokensShuffleToken(1)
    );

    protected FaithPathSinglePlayer faithPathSinglePlayer;
    protected Stack<SinglePlayerToken> singlePlayerTokens;

    /**
     * Creates the game context associated with this game.
     * <p>
     * Note1: there should only be one gameContext for each game (in the scope of a specific game, the game context is
     * like a singleton).
     * Note2: this constructor is marked as protected because this class should only ever be initialized by the
     * {@link GameContextBuilder} and thus the constructor should never be
     * called from outside this package
     *
     * @param market                Market, see {@link MarketImp}
     * @param developmentCardsTable the table with the development cards the players can buy, see
     *                              {@link DevelopmentCardsTableImp}
     * @param faithPath             the faith path, see {@link FaithPathImp}
     * @param playersOrder          An ordered list of players that specifies the player turns order
     * @param playerContexts        a map that associates every player with his player context. See {@link PlayerContextImp}
     */
    protected SinglePlayerGameContextImp(
        Market market,
        DevelopmentCardsTable developmentCardsTable,
        FaithPathSinglePlayer faithPath,
        List<Player> playersOrder,
        Map<Player, PlayerContext> playerContexts
    ) {
        super(market, developmentCardsTable, faithPath, playersOrder, playerContexts);
        this.faithPathSinglePlayer = faithPath;
        resetAndShuffleSinglePlayerTokens();
    }

    @Override
    public FaithPathSinglePlayer getFaithPathSinglePlayer() {
        return faithPathSinglePlayer;
    }

    @Override
    public Stack<SinglePlayerToken> getSinglePlayerTokensDeck() {
        return singlePlayerTokens;
    }

    @Override
    public void resetAndShuffleSinglePlayerTokens() {

        Random randGen = new Random();

        singlePlayerTokens = new Stack<>();
        List<SinglePlayerToken> tokensToPickFrom = new ArrayList<>(SINGLE_PLAYER_TOKENS);
        while (!tokensToPickFrom.isEmpty()) {
            singlePlayerTokens.add(tokensToPickFrom.remove(randGen.nextInt(tokensToPickFrom.size())));
        }

    }

}
