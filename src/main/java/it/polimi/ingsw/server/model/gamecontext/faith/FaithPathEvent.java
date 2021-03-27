package it.polimi.ingsw.server.model.gamecontext.faith;

/**
 * This class represents the different types of Events that can be triggered by a Player (or Black Cross token)
 * moving in the Faith Track. It states if a Vatican Report happened or if the end of the Faith Track is reached.
 */
public class FaithPathEvent {
	/**
	 * States if the final space of the Faith Track is reached
	 */
	private boolean endReached;
	/**
	 * States if at least one Vatican Report has occurred
	 */
	private boolean vaticanMeeting;

	/**
	 * Class constructor.
	 * @param endReached boolean which states if the final space of the Faith Track is reached
	 * @param vaticanMeeting boolean which states if at least one Vatican Report has occurred
	 */
	public FaithPathEvent(boolean endReached, boolean vaticanMeeting) {
		this.endReached = endReached;
		this.vaticanMeeting = vaticanMeeting;
	}

	/**
	 * Method to state if the final space of the Faith Track is reached.
	 * @return true if the end of the Faith Track is reached, false if the Player's position after moving is before
	 * the final space of the Faith Track
	 */
	public boolean isEndReached() {
		return endReached;
	}

	/**
	 * Method to state if a Vatican Report has occurred.
	 * @return true if at least one Vatican Report has occurred, false if no Vatican Report has occurred
	 */
	public boolean hasVaticanMeetingHappened() {
		return vaticanMeeting;
	}
}
