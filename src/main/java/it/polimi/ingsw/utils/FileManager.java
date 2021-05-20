package it.polimi.ingsw.utils;

import it.polimi.ingsw.configfile.*;
import it.polimi.ingsw.utils.serialization.SerializationHelper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class FileManager {

	protected static FileManager instance;

	public static final String LOCALIZATION_PATH = "localization/%s/localization.yml";

	public static final String DEFAULT_RULES_PATH =  "GameRulesConfig/StandardGameRules";

	public static final String GAME_INFO_CONFIG_FILE_NAME = "game-info-config.yml";
	public static final String MARKET_CONFIG_FILE_NAME = "market-config.yml";
	public static final String FAITH_PATH_CONFIG_FILE_NAME = "faith-path-config.yml";
	public static final String DEV_CARDS_CONFIG_FILE_NAME = "development-cards-config.yml";
	public static final String LEAD_CARDS_CONFIG_FILE_NAME = "leader-cards-config.yml";

	protected Map<String, GameRules> cachedGameRules = new HashMap<>();
	protected Map<String, Map<String, Object>> cachedLocalizationMap = new HashMap<>();

	protected ClassLoader classloader = Thread.currentThread().getContextClassLoader();

	protected FileManager() {}

	public static FileManager getFileManagerInstance() {
		if (instance == null)
			instance = new FileManager();
		return instance;
	}

	public Map<String, Object> getLocalization(String localization) throws IOException {
		if(!cachedLocalizationMap.containsKey(localization))
			loadLocalization(localization);
		return cachedLocalizationMap.get(localization);
	}

	public GameRules getDefaultGameRules() throws IOException {
		return getGameRules(DEFAULT_RULES_PATH);
	}

	public GameRules getGameRules(String gameRulesPath) throws IOException {
		if(cachedGameRules.containsKey(gameRulesPath))
			return cachedGameRules.get(gameRulesPath);
		else
		    return readGameRules(gameRulesPath);
	}

	protected GameRules readGameRules(String gameRulesPath) throws IOException {
		GameInfoConfig gameInfoConfig = SerializationHelper.deserializeYamlFromInputStream(
			getGameRulesFile(gameRulesPath, GAME_INFO_CONFIG_FILE_NAME),
			GameInfoConfig.class
		);

		MarketConfig marketConfig = SerializationHelper.deserializeYamlFromInputStream(
			getGameRulesFile(gameRulesPath, MARKET_CONFIG_FILE_NAME),
			MarketConfig.class
		);

		FaithPathConfig faithPathConfig = SerializationHelper.deserializeYamlFromInputStream(
			getGameRulesFile(gameRulesPath, FAITH_PATH_CONFIG_FILE_NAME),
			FaithPathConfig.class
		);

		DevelopmentCardsConfig developmentCardsConfig = SerializationHelper.deserializeYamlFromInputStream(
			getGameRulesFile(gameRulesPath, DEV_CARDS_CONFIG_FILE_NAME),
			DevelopmentCardsConfig.class
		);

		LeaderCardsConfig leaderCardsConfig = SerializationHelper.deserializeYamlFromInputStream(
			getGameRulesFile(gameRulesPath, LEAD_CARDS_CONFIG_FILE_NAME),
			LeaderCardsConfig.class
		);

		GameRules gameRules = new GameRules(
			gameInfoConfig,
			faithPathConfig,
			marketConfig,
			developmentCardsConfig,
			leaderCardsConfig
		);

		cachedGameRules.put(gameRulesPath, gameRules);
		return gameRules;
	}

	protected InputStream getGameRulesFile(String gameRulesPath, String fileName) {
		return classloader.getResourceAsStream(Path.of(gameRulesPath, fileName).toString());
	}

	protected Map<String, Object> loadLocalization(String localization) throws IOException {
		String locPath = String.format(LOCALIZATION_PATH, localization);
		InputStream locFile = classloader.getResourceAsStream(Path.of(locPath).toString());
		Map<String, Object> localizationMap = SerializationHelper.deserializeYamlFromInputStream(locFile, Map.class);
		cachedLocalizationMap.put(localization, localizationMap);
		return localizationMap;
	}
}
