package it.polimi.ingsw.server.model.gamecontext.faith;

public class FaithPathEvent {
	private boolean endReached;
	private boolean vaticanMeeting;

	public FaithPathEvent(boolean endReached, boolean vaticanMeeting) {
		this.endReached = endReached;
		this.vaticanMeeting = vaticanMeeting;
	}

	public boolean isEndReached() {
		return endReached;
	}

	public boolean hasVaticanMeetingHappened() {
		return vaticanMeeting;
	}
}
