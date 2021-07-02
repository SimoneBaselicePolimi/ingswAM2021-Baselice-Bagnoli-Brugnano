package it.polimi.ingsw.server.model.gamecontext.faith;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.localization.Localization;

import java.util.Map;

/**
 * This enumeration list all the possible states of Pope's Favor card.
 */
public enum PopeFavorCardState {

	/**
	 * The Pope's Favor card is turned face-up.
	 * It is one of the two possible configurations of the Pope's Favor cards after a Vatican Report has occurred
	 */
	@JsonProperty("ACTIVE") ACTIVE,

	/**
	 * The Pope's Favor card is discarded.
	 * It is one of the two possible configurations of the Pope's Favor cards after a Vatican Report has occurred
	 */
	@JsonProperty("DISCARDED") DISCARDED,

	/**
	 * The card is showing the “non active” side (the side with a red X) up.
	 * It represent the initial configuration of the Pope's Favor cards
	 */
	@JsonProperty("HIDDEN") HIDDEN;

	private final static Map<PopeFavorCardState, String> CARDSTATELOC = Map.of(
		ACTIVE, "gameHistory.faithPath.popeFavorCards.states.active",
		DISCARDED, "gameHistory.faithPath.popeFavorCards.states.discarded",
		HIDDEN, "gameHistory.faithPath.popeFavorCards.states.hidden"
	);

	@JsonIgnore
	public String getLocalizedName() {
		return Localization.getLocalizationInstance().getString(CARDSTATELOC.get(this));
	}

}
