package it.polimi.ingsw.server.model.gamecontext;

import it.polimi.ingsw.configfile.GameRules;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPath;
import it.polimi.ingsw.server.model.gamecontext.market.Market;
import it.polimi.ingsw.server.model.gamecontext.market.WrongNumberOfMarblesException;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.MarbleColour;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTable;
import it.polimi.ingsw.server.model.notifier.GameContextNotifier;
import it.polimi.ingsw.server.model.notifier.MarketNotifier;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ObservableGameContextBuilder extends GameContextBuilder{

	public ObservableGameContextBuilder(
		Set<Player> players,
		GameRules gameRules,
		GameItemsManager gameItemsManager
	) {
		super(players, gameRules, gameItemsManager);
	}

	@Override
	public Market initializeMarket(
		int nRows, int nColumns, Map<MarbleColour, Integer> marbles
	) throws WrongNumberOfMarblesException {
		return new MarketNotifier(nRows, nColumns, marbles);
	}

	@Override
	public GameContext initializeGameContext(
		Market market,
		DevelopmentCardsTable developmentCardsTable,
		FaithPath faithPath,
		List<Player> playersOrder,
		Map<Player, PlayerContext> playerContexts
	) {
		return new GameContextNotifier(market, developmentCardsTable, faithPath, playersOrder, playerContexts);
	}

}
