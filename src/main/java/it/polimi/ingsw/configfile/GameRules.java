package it.polimi.ingsw.configfile;

public class GameRules {

    public final GameInfoConfig gameInfoConfig;

    public final MarketConfig marketConfig;

    public GameRules(GameInfoConfig gameInfoConfig, MarketConfig marketConfig) {
        this.gameInfoConfig = gameInfoConfig;
        this.marketConfig = marketConfig;
    }

}
