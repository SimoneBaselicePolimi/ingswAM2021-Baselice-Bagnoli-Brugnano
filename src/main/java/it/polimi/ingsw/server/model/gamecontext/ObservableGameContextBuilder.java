package it.polimi.ingsw.server.model.gamecontext;

import it.polimi.ingsw.configfile.GameRules;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.FaithPath;
import it.polimi.ingsw.server.model.gamecontext.faith.VaticanReportSection;
import it.polimi.ingsw.server.model.gamecontext.market.Market;
import it.polimi.ingsw.server.model.gamecontext.market.WrongNumberOfMarblesException;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gamehistory.GameHistory;
import it.polimi.ingsw.server.model.gameitems.*;
import it.polimi.ingsw.server.model.gameitems.cardstack.PlayerOwnedDevelopmentCardDeck;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTable;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirement;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.observableproxy.*;
import it.polimi.ingsw.server.model.storage.ObservableResourcesStorageBuilder;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.model.storage.ResourceStorageBuilder;
import it.polimi.ingsw.server.model.storage.ResourceStorageRule;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

//TODO initialize FaithPathSinglePlayerObservableProxy
public class ObservableGameContextBuilder extends GameContextBuilder{

	protected GameManager gameManager;

	public ObservableGameContextBuilder(
		GameManager gameManager,
		Set<Player> players,
		GameRules gameRules,
		GameItemsManager gameItemsManager,
		GameHistory gameHistory
	) {
		super(players, gameRules, gameItemsManager, gameHistory);
		this.gameManager = gameManager;
	}

	@Override
	public Market initializeMarket(
		int nRows,
		int nColumns,
		Map<MarbleColour, Integer> marbles
	)
		throws WrongNumberOfMarblesException {
		return new MarketObservableProxy(
			super.initializeMarket(nRows, nColumns, marbles),
			gameManager
		);
	}

	@Override
	public GameContext initializeGameContext(
		Market market,
		DevelopmentCardsTable developmentCardsTable,
		FaithPath faithPath,
		List<Player> playersOrder,
		Map<Player, PlayerContext> playerContexts
	) {
		return new GameContextObservableProxy(
			super.initializeGameContext(market, developmentCardsTable, faithPath, playersOrder, playerContexts),
			gameManager
		);
	}

	@Override
	public PlayerContext initializePlayerContext(
		Player player,
		Set<ResourceStorage> shelves,
		List<PlayerOwnedDevelopmentCardDeck> decks,
		ResourceStorage infiniteChest,
		ResourceStorage temporaryStorage,
		Set<Production> baseProductions
	) {
		return new PlayerContextObservableProxy(
			super.initializePlayerContext(player, shelves, decks, infiniteChest, temporaryStorage, baseProductions),
			gameManager
		);
	}

	@Override
	public ResourceStorage initializeResourceStorage(
		String resourceStorageID,
		List<ResourceStorageRule> rules
	) {
		return new ResourceStorageObservableProxy(
			super.initializeResourceStorage(resourceStorageID, rules),
			gameManager
		);
	}

	@Override
	public DevelopmentCardsTable initializeDevelopmentCardsTable(
		List<DevelopmentCard> cards,
		BiFunction<DevelopmentCardColour, DevelopmentCardLevel, String> getIdForDeckWithColourAndLevel
	) {
		return new DevelopmentCardsTableObservableProxy(
			super.initializeDevelopmentCardsTable(cards, getIdForDeckWithColourAndLevel),
			gameManager
		);
	}

	@Override
	public FaithPath initializeFaithPath(
		int faithPathLength,
		List<VaticanReportSection> vaticanSections,
		int[] victoryPointsByPosition
	) {
		return new FaithPathObservableProxy(
			super.initializeFaithPath(faithPathLength, vaticanSections, victoryPointsByPosition),
			gameManager
		);
	}

	@Override
	public LeaderCard initializeLeaderCard(
		String leaderCardID,
		Set<LeaderCardRequirement> requirements,
		Set<Production> productions,
		Set<ResourceStorage> resourceStorages,
		Set<DevelopmentCardCostDiscount> cardCostDiscounts,
		Set<WhiteMarbleSubstitution> specialMarbleSubstitutions,
		int victoryPoints
	) {
		return new LeaderCardObservableProxy(
			super.initializeLeaderCard(
				leaderCardID,
				requirements,
				productions,
				resourceStorages,
				cardCostDiscounts,
				specialMarbleSubstitutions,
				victoryPoints
			),
			gameManager
		);
	}

	@Override
	public ResourceStorageBuilder initializeResourceStorageBuilder() {
		return new ObservableResourcesStorageBuilder(gameItemsManager, gameManager);
	}

	@Override
	public PlayerOwnedDevelopmentCardDeck initializePlayerOwnedDevelopmentCardDeck(String idDeck) {
		return new PlayerOwnedDevelopmentCardDeckObservableProxy(
			super.initializePlayerOwnedDevelopmentCardDeck(idDeck),
			gameManager
		);
	}

}
