package it.polimi.ingsw.server.model.gamecontext.faith;

/**
 * This enumeration list all the possible states of Pope's Favor card.
 */
public enum PopeFavorCardState {
	/**
	 * The Pope's Favor card is turned face-up.
	 * It is one of the two possible configurations of the Pope's Favor cards after a Vatican Report has occurred
	 */
	ACTIVE,

	/**
	 * The Pope's Favor card is discarded.
	 * It is one of the two possible configurations of the Pope's Favor cards after a Vatican Report has occurred
	 */

	DISCARDED,
	/**
	 * The card is showing the “non active” side (the side with a red X) up.
	 * It represent the initial configuration of the Pope's Favor cards
	 */
	HIDDEN
}
