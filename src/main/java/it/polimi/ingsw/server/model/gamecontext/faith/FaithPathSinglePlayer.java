package it.polimi.ingsw.server.model.gamecontext.faith;

public interface FaithPathSinglePlayer extends FaithPath {
    /**
     * Returns the position of Lorenzo il Magnifico in the Faith Track.
     * @return position of the Black Cross token in the Faith Track
     */
    int getBlackCrossFaithPosition();

    /**
     This method represent the action of the Black Cross token (representing Lorenzo il Magnifico) moving
     in the Faith Track for a specific number of steps forward. This action can trigger a Vatican Report if a Pope space
     is reached. In this case, the single Player has to change the state of his Pope's Favor cards based on his position
     in the Track.
     * @param steps number of steps the Black Cross token has to move forward in the Track
     * @return a Faith Path Event, which states if a Vatican Report happened or if the end of the Faith Track
     * is reached by the Black Cross token after moving, triggering the immediate end of the Game
     */
    FaithPathEvent moveBlackCross(int steps);
}
