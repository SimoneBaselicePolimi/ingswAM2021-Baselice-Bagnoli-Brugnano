package it.polimi.ingsw.server.model.gamecontext.faith;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Representable;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.faithrepresentation.ServerFaithPathRepresentation;

import java.util.List;
import java.util.Map;

public interface FaithPath extends Representable<ServerFaithPathRepresentation> {
    /**
     * Returns the position of a specific Player in the Faith Track.
     * @param player player to get the position of
     * @return position of the Player in the Faith Track
     */
    int getPlayerFaithPosition(Player player);

    /**
     * Returns the position of each Player in the Faith Track.
     * @return positions of each Player in the Faith Track
     */
    Map<Player, Integer> getFaithPosition();

    /**
     * Returns the states of the Pope's Favor cards of a specific Player.
     * @param player player to get the Pope's Favor cards of
     * @return states of the Pope's Favor cards of the Player
     */
    List<PopeFavorCardState> getPlayerPopeFavorCardsState(Player player);

    /**
     * Returns the states of the Pope's Favor cards of each Player.
     * @return states of the Pope's Favor cards of each Player
     */
    Map<Player, List<PopeFavorCardState>> getPopeFavorCardsState();

    /**
     * Returns the victory points of a specific Player based on his active Pope's Favor cards and
     * his position in the Faith Track.
     * @param player player to get the victory points of
     * @return number of victory points scored by the Player
     */
    int getPlayerVictoryPoints(Player player);

    /**
     * Returns the victory points scored by each Player.
     * @return number of victory points scored by each Player
     */
    Map<Player,Integer> getVictoryPoints();

    /**
     * Returns true if the last position of the Faith Track has been reached, triggering the end of the Game.
     * @return true if at least one Player has reached the end of the Faith Track, false if no Player has reached it
     */
    boolean lastPositionHasBeenReached();

    /**
     * This method represent the action of a Player moving in the Faith Track for a specific number of steps forward.
     * This action can trigger a Vatican Report if a Pope space is reached by the Player. In this case, each Player
     * has to change the state of his Pope's Favor cards based on their positions in the Track.
     * @param player Player moving in the Faith Track
     * @param steps number of steps a Player has to move forward in the Track
     * @return a Faith Path Event, which states if a Vatican Report happened or if the end of the Faith Track
     * is reached by the Player after moving
     */
    FaithPathEvent move(Player player, int steps);
}
