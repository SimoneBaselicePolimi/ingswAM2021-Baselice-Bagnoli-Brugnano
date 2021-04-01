package it.polimi.ingsw.server.model.gameitems.leadercard;

/**
 * enumeration that indicates the status of the leader cards:
 * 1. ACTIVE when the player can use special abilities of the card during his turn
 * 2. DISCARDED when the player decides not to have the card in hand anymore
 * 3. IN_HAND when the player has the cards in his hand (he can't use special abilities of the card)
 */
public enum LeaderCardState {
	ACTIVE, DISCARDED, HIDDEN;
}
