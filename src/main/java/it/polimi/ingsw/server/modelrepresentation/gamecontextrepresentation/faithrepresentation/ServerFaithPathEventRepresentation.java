package it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.faithrepresentation;

import it.polimi.ingsw.server.modelrepresentation.ServerRepresentation;

public class ServerFaithPathEventRepresentation extends ServerRepresentation {

    /**
     * States if the final space of the Faith Track is reached
     */
    public final boolean endReached;

    /**
     * States if at least one Vatican Report has occurred
     */
    public final boolean vaticanMeeting;

    /**
     * Class constructor.
     * @param endReached boolean which states if the final space of the Faith Track is reached
     * @param vaticanMeeting boolean which states if at least one Vatican Report has occurred
     */
    public ServerFaithPathEventRepresentation(boolean endReached, boolean vaticanMeeting) {
        this.endReached = endReached;
        this.vaticanMeeting = vaticanMeeting;
    }
}
