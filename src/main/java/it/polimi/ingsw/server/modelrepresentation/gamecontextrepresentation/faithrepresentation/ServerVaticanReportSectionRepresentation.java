package it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.faithrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.modelrepresentation.ServerRepresentation;

public class ServerVaticanReportSectionRepresentation extends ServerRepresentation {

    /**
     * Position in which this Vatican Report section start
     */
    public final int sectionInitialPos;

    /**
     * Position of the Pope space related to this Vatican Report section
     */
    public final int popeSpacePos;

    /**
     * Number of victory points scored by a Player for the related Pope's Favor card turned face-up (active)
     */
    public final int sectionVictoryPoints;

    /**
     * Class constructor.
     * @param sectionInitialPos position in which this Vatican Report section start
     * @param popeSpacePos position of the Pope space related to this Vatican Report section
     * @param sectionVictoryPoints victory points scored for the related Pope's Favor card if active
     */
    public ServerVaticanReportSectionRepresentation(
        @JsonProperty("sectionInitialPos") int sectionInitialPos,
        @JsonProperty("popeSpacePos") int popeSpacePos,
        @JsonProperty("sectionVictoryPoints") int sectionVictoryPoints
    ) {
        this.sectionInitialPos = sectionInitialPos;
        this.popeSpacePos = popeSpacePos;
        this.sectionVictoryPoints = sectionVictoryPoints;
    }
}
