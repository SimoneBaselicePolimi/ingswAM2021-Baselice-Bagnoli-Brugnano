package it.polimi.ingsw.utils;

import it.polimi.ingsw.configfile.GameInfoConfig;
import it.polimi.ingsw.configfile.GameRules;
import it.polimi.ingsw.configfile.MarketConfig;
import it.polimi.ingsw.utils.serialization.SerializationHelper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;

public class FileManager {

	protected static FileManager instance;

	public static final String DEFAULT_RULES_PATH =  "GameRulesConfig/StandardGameRules";

	public static final String GAME_INFO_CONFIG_FILE_NAME =  "game-info-config.yml";
	public static final String MARKET_CONFIG_PATH =  "market-config.yml";
	//TODO

	protected Map<String, GameRules> cachedGameRules;

	protected ClassLoader classloader = Thread.currentThread().getContextClassLoader();

	protected FileManager() {}

	public static FileManager getFileManagerInstance() {
		if (instance == null)
			instance = new FileManager();
		return instance;
	}

	public GameRules getDefaultGameRules() throws IOException {
		return getGameRules(DEFAULT_RULES_PATH);
	}

	public GameRules getGameRules(String gameRulesPath) throws IOException {
		if(cachedGameRules.containsKey(gameRulesPath))
			return cachedGameRules.get(cachedGameRules);
		else
		    return readGameRules(gameRulesPath);
	}

	protected GameRules readGameRules(String gameRulesPath) throws IOException {
		GameInfoConfig gameInfoConfig = SerializationHelper.deserializeYamlAsObject(
			getGameRulesFile(gameRulesPath, GAME_INFO_CONFIG_FILE_NAME),
			GameInfoConfig.class
		);

		MarketConfig marketConfig = SerializationHelper.deserializeYamlAsObject(
			getGameRulesFile(gameRulesPath, MARKET_CONFIG_PATH),
			MarketConfig.class
		);

		//TODO
		GameRules gameRules =  null; //new GameRules(gameInfoConfig, marketConfig, );

		cachedGameRules.put(gameRulesPath, gameRules);
		return gameRules;
	}

	protected InputStream getGameRulesFile(String gameRulesPath, String fileName) {
		return classloader.getResourceAsStream(Path.of(gameRulesPath, fileName).toString());
	}
}
