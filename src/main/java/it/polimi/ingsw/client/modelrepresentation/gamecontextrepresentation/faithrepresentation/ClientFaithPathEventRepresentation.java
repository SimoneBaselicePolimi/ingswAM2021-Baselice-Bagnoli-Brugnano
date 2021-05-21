package it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.faithrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;

public class ClientFaithPathEventRepresentation extends ClientRepresentation {

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
    public ClientFaithPathEventRepresentation(
        @JsonProperty("endReached") boolean endReached,
        @JsonProperty("vaticanMeeting") boolean vaticanMeeting
    ) {
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
