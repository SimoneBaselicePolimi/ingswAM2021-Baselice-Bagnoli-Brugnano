package it.polimi.ingsw.configfile;

public class GameRules {

    public final GameInfoConfig gameInfoConfig;

    public final MarketConfig marketConfig;

    public final DevelopmentCardsConfig developmentCardsConfig;

    public final FaithPathConfig faithPathConfig;

    public final LeaderCardsConfig leaderCardsConfig;

    public GameRules(GameInfoConfig gameInfoConfig, MarketConfig marketConfig, DevelopmentCardsConfig developmentCardsConfig,
                     FaithPathConfig faithPathConfig, LeaderCardsConfig leaderCardsConfig, ProductionConfig productionConfig) {
        this.gameInfoConfig = gameInfoConfig;
        this.marketConfig = marketConfig;
        this.developmentCardsConfig = developmentCardsConfig;
        this.faithPathConfig = faithPathConfig;
        this.leaderCardsConfig = leaderCardsConfig;
    }

}
