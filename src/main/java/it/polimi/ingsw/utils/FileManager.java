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

	public static final String DEFAULT_RULES_PATH =  "GameRulesConfig/StandardGameRules";

	public static final String GAME_INFO_CONFIG_FILE_NAME =  "game-info-config.yml";
	public static final String MARKET_CONFIG_PATH =  "market-config.yml";
	//TODO


	protected String rulesPath;

	protected Map<String, GameRules> cachedGameRules;

	protected ClassLoader classloader = Thread.currentThread().getContextClassLoader();


	public FileManager(String path) {
		rulesPath = DEFAULT_RULES_PATH;
	}

	public GameRules getGameRules() throws IOException {
		if(cachedGameRules.containsKey(rulesPath))
			return cachedGameRules.get(cachedGameRules);
		else
		    return readGameRules();
	}

	protected GameRules readGameRules() throws IOException {
		GameInfoConfig gameInfoConfig = SerializationHelper.deserializeYamlAsObject(
			getGameRulesFile(GAME_INFO_CONFIG_FILE_NAME),
			GameInfoConfig.class
		);

		MarketConfig marketConfig = SerializationHelper.deserializeYamlAsObject(
			getGameRulesFile(MARKET_CONFIG_PATH),
			MarketConfig.class
		);

		GameRules gameRules = null;
			// new GameRules(gameInfoConfig, marketConfig);

		cachedGameRules.put(rulesPath, gameRules);
		return gameRules;
	}

	protected InputStream getGameRulesFile(String fileName) {
		return classloader.getResourceAsStream(Path.of(rulesPath, fileName).toString());
	}
}
