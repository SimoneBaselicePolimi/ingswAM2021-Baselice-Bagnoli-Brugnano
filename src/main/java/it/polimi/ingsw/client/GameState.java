package it.polimi.ingsw.client;

/**
 * This class represents the main states in which the game may be seen by each client:
 * - PLAYER_REGISTRATION_AND_MATCHMAKING: player connection process
 * - GAME_SETUP: players decide which leader cards and possible resources and faith points to obtain before starting the game
 * - MY_PLAYER_TURN_BEFORE_MAIN_ACTION: my player is the active player and can perform the main action of this turn
 * - MY_PLAYER_TURN_AFTER_MAIN_ACTION: my player is the active player and has already performed the main action in this turn
 * - ANOTHER_PLAYER_TURN: My player is not the active player
 */
public enum GameState {
    PLAYER_REGISTRATION_AND_MATCHMAKING,
    GAME_SETUP,
    MY_PLAYER_TURN_BEFORE_MAIN_ACTION,
    MY_PLAYER_TURN_AFTER_MAIN_ACTION,
    ANOTHER_PLAYER_TURN
}
