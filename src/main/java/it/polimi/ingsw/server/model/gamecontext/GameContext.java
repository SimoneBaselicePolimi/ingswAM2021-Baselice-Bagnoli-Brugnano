package it.polimi.ingsw.server.model.gamecontext;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Representable;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPath;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPathImp;
import it.polimi.ingsw.server.model.gamecontext.market.Market;
import it.polimi.ingsw.server.model.gamecontext.market.MarketImp;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContextImp;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTable;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTableImp;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.ServerGameContextRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ServerPlayerContextRepresentation;

import java.util.List;
import java.util.Set;

public interface GameContext extends Representable<ServerGameContextRepresentation> {
    /**
     * An ordered list of players that specifies the player turns order (in the first turn of the game the first
     *  player in the list will play, then the second player in the list will play his turn, then the third, etc.)
     * @return the ordered list
     */
    List<Player> getPlayersTurnOrder();

    /**
     * This method returns the development cards from the DevelopmentCardsTable that the player has enough resources to
     * buy (also considering the possible discounts that may apply, see
     * {@link it.polimi.ingsw.server.model.gameitems.DevelopmentCardCostDiscount})
     * @param player the player, see {@link Player}
     * @return a set of DevelopmentCards the specified player can buy
     */
    Set<DevelopmentCard> getDevelopmentCardsPlayerCanBuy(Player player);

    /**
     * Returns all the game information for a specific player
     * @param player the player, see {@link Player}
     * @return returns the playerContext for the specified player, see {@link PlayerContextImp}
     */
    PlayerContext getPlayerContext(Player player);

    /**
     * @return returns the Market, see {@link MarketImp}
     */
    Market getMarket();

    /**
     * Returns the DevelopmentCardTable, a table with the development cards the players can buy
     * @return returns the DevelopmentCardTable, see {@link DevelopmentCardsTableImp}
     */
    DevelopmentCardsTable getDevelopmentCardsTable();

    /**
     * @return returns the FaithPath, see {@link FaithPathImp}
     */
    FaithPath getFaithPath();

    Player getActivePlayer();

    Player startNextPlayerTurn();
}
