package it.polimi.ingsw.client.model;

public class FaithPathEventRepresentation extends Representation{

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
    public FaithPathEventRepresentation(boolean endReached, boolean vaticanMeeting) {
        this.endReached = endReached;
        this.vaticanMeeting = vaticanMeeting;
    }

    public boolean isEndReached() {
        return endReached;
    }

    public boolean isVaticanMeeting() {
        return vaticanMeeting;
    }

    public void setEndReached(boolean endReached) {
        this.endReached = endReached;
    }

    public void setVaticanMeeting(boolean vaticanMeeting) {
        this.vaticanMeeting = vaticanMeeting;
    }
}
