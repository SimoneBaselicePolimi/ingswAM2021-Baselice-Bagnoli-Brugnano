package it.polimi.ingsw.configfile;

/**
 * This class represents the collection of the Game Rules: it contains the references to the configuration schemas
 * of every Game Item and structure whose parameters are set in configuration files.
 */
public class GameRules {

    /**
     * Reference to the GameInfoConfig class, which sets all the parameters regarding the Setup and general information
     * about the Game
     */
    public final GameInfoConfig gameInfoConfig;

    /**
     * Reference to the FaithPathConfig class, which sets all the parameters regarding the Faith Path
     */
    public final FaithPathConfig faithPathConfig;

    /**
     * Reference to the MarketConfig class, which sets all the parameters regarding the Market structure
     */
    public final MarketConfig marketConfig;

    /**
     * Reference to the DevelopmentCardsConfig class, which sets all the parameters regarding the Development Cards
     */
    public final DevelopmentCardsConfig developmentCardsConfig;

    /**
     * Reference to the LeaderCardsConfig class, which sets all the parameters regarding the Leader Cards
     */
    public final LeaderCardsConfig leaderCardsConfig;

    /**
     * GameRules constructor.
     * @param gameInfoConfig contains all the parameters regarding the Setup and general information about the Game
     * @param faithPathConfig contains all the parameters regarding the Faith Path
     * @param marketConfig contains all the parameters regarding the Market structure
     * @param developmentCardsConfig contains all the parameters regarding the Development Cards
     * @param leaderCardsConfig contains all the parameters regarding the Leader Cards
     */
    public GameRules(GameInfoConfig gameInfoConfig, FaithPathConfig faithPathConfig, MarketConfig marketConfig,
                     DevelopmentCardsConfig developmentCardsConfig, LeaderCardsConfig leaderCardsConfig) {
        this.gameInfoConfig = gameInfoConfig;
        this.faithPathConfig = faithPathConfig;
        this.marketConfig = marketConfig;
        this.developmentCardsConfig = developmentCardsConfig;
        this.leaderCardsConfig = leaderCardsConfig;
    }

}