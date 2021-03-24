package it.polimi.ingsw.server.model.gamecontext;

import it.polimi.ingsw.server.model.gamecontext.market.Market;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTable;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPath;

import java.util.List;
import java.util.Map;

public class GameContext {

	private Map<Player,PlayerContext> playerContexts;

	public List<Player> getOrdererPlayers() {
		return null;
	}

	public GameContext(Market market, DevelopmentCardsTable developmentCardsTable, Map<Player,PlayerContext> playerContexts) {

	}

	public List<DevelopmentCard> getDevelopmentCardsPlayerCanBuy(Player player) {
		return null;
	}

	public PlayerContext getPlayerContext(Player player) {
		return null;
	}

	public Market getMarket() {
		return null;
	}

	public DevelopmentCardsTable getDevelopmentCardsTable() {
		return null;
	}

	public FaithPath getFaithPath() {
		return null;
	}

}
