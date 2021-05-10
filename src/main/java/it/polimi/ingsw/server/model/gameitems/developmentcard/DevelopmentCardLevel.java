package it.polimi.ingsw.server.model.gameitems.developmentcard;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

//Note: because of a "bug" with Jackson using JsonProperty with the right number is not enough.
//This is caused by jackson always considering a number as the enum id and never as a "custom string name" when
// deserializing. See https://github.com/FasterXML/jackson-databind/issues/1626

/**
 * This class represents the level of a development card (FIRST_LEVEL, SECOND_LEVEL, THIRD_LEVEL)
 */
public enum DevelopmentCardLevel {
	@JsonProperty("1") FIRST_LEVEL(1),
	@JsonProperty("2") SECOND_LEVEL(2),
	@JsonProperty("3") THIRD_LEVEL(3);

	int numAlias;
	DevelopmentCardLevel(int alias) {numAlias = alias;}

	private static Map<Integer, DevelopmentCardLevel> numAliasMap;

	@JsonCreator
	public static DevelopmentCardLevel forValue(int numAlias) {
		return numAliasMap.get(numAlias);
	}

	@JsonValue
	public int toValue() {
		return numAlias;
	}

	static {
		numAliasMap = Arrays.stream(DevelopmentCardLevel.values())
			.collect(Collectors.toMap(level -> level.numAlias, level -> level));
	}

}