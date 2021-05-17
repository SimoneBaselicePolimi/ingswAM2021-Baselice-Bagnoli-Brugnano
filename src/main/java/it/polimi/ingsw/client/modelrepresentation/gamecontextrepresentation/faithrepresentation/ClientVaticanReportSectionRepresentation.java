package it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.faithrepresentation;

import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;

public class ClientVaticanReportSectionRepresentation extends ClientRepresentation {

    /**
     * Position in which this Vatican Report section start
     */
    private final int sectionInitialPos;

    /**
     * Position of the Pope space related to this Vatican Report section
     */
    private final int popeSpacePos;

    /**
     * Number of victory points scored by a Player for the related Pope's Favor card turned face-up (active)
     */
    private final int sectionVictoryPoints;

    /**
     * Class constructor.
     * @param sectionInitialPos position in which this Vatican Report section start
     * @param popeSpacePos position of the Pope space related to this Vatican Report section
     * @param sectionVictoryPoints victory points scored for the related Pope's Favor card if active
     */
    public ClientVaticanReportSectionRepresentation(int sectionInitialPos, int popeSpacePos, int sectionVictoryPoints) {
        this.sectionInitialPos = sectionInitialPos;
        this.popeSpacePos = popeSpacePos;
        this.sectionVictoryPoints = sectionVictoryPoints;
    }

    public int getSectionInitialPos() {
        return sectionInitialPos;
    }

    public int getPopeSpacePos() {
        return popeSpacePos;
    }

    public int getSectionVictoryPoints() {
        return sectionVictoryPoints;
    }

}
