package it.polimi.ingsw.configfile;

public class GameRules {

    public final GameInfoConfig gameInfoConfig;

    public final MarketConfig marketConfig;

    public final DevelopmentCardsConfig developmentCardsConfig;

    public final FaithPathConfig faithPathConfig;

    public final LeaderCardsConfig leaderCardsConfig;

    public final MarblesConfig marblesConfig;

    public GameRules(GameInfoConfig gameInfoConfig, MarketConfig marketConfig, DevelopmentCardsConfig developmentCardsConfig,
                     FaithPathConfig faithPathConfig, LeaderCardsConfig leaderCardsConfig, MarblesConfig marblesConfig) {
        this.gameInfoConfig = gameInfoConfig;
        this.marketConfig = marketConfig;
        this.developmentCardsConfig = developmentCardsConfig;
        this.faithPathConfig = faithPathConfig;
        this.leaderCardsConfig = leaderCardsConfig;
        this.marblesConfig = marblesConfig;
    }

}
