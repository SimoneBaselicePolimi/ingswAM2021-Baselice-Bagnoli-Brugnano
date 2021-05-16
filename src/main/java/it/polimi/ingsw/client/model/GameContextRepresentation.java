package it.polimi.ingsw.client.model;

import java.util.List;
import java.util.Map;

public class GameContextRepresentation  extends Representation{

    private final MarketRepresentation market;
    private final DevelopmentCardsTableRepresentation developmentCardsTable;
    private final FaithPathRepresentation faithPath;
    private final List<PlayerRepresentation> playersOrder;
    private final Map<PlayerRepresentation, PlayerContextRepresentation> playerContexts;
    private PlayerRepresentation activePlayer;

    public GameContextRepresentation(
        MarketRepresentation market,
        DevelopmentCardsTableRepresentation developmentCardsTable,
        FaithPathRepresentation faithPath,
        List<PlayerRepresentation> playersOrder,
        Map<PlayerRepresentation, PlayerContextRepresentation> playerContexts,
        PlayerRepresentation activePlayer
    ) {
        this.market = market;
        this.developmentCardsTable = developmentCardsTable;
        this.faithPath = faithPath;
        this.playersOrder = playersOrder;
        this.playerContexts = playerContexts;
        this.activePlayer = activePlayer;
    }

    public MarketRepresentation getMarket() {
        return market;
    }

    public DevelopmentCardsTableRepresentation getDevelopmentCardsTable() {
        return developmentCardsTable;
    }

    public FaithPathRepresentation getFaithPath() {
        return faithPath;
    }

    public List<PlayerRepresentation> getPlayersOrder() {
        return playersOrder;
    }

    public Map<PlayerRepresentation, PlayerContextRepresentation> getPlayerContexts() {
        return playerContexts;
    }

    public PlayerRepresentation getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(PlayerRepresentation activePlayer) {
        this.activePlayer = activePlayer;
    }
}
