package it.polimi.ingsw.client.model;

public class VaticanReportSectionRepresentation  extends Representation{

    /**
     * Position in which this Vatican Report section start
     */
    int sectionInitialPos;

    /**
     * Position of the Pope space related to this Vatican Report section
     */
    int popeSpacePos;

    /**
     * Number of victory points scored by a Player for the related Pope's Favor card turned face-up (active)
     */
    int sectionVictoryPoints;

    /**
     * Class constructor.
     * @param sectionInitialPos position in which this Vatican Report section start
     * @param popeSpacePos position of the Pope space related to this Vatican Report section
     * @param sectionVictoryPoints victory points scored for the related Pope's Favor card if active
     */
    public VaticanReportSectionRepresentation(int sectionInitialPos, int popeSpacePos, int sectionVictoryPoints) {
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

    public void setSectionInitialPos(int sectionInitialPos) {
        this.sectionInitialPos = sectionInitialPos;
    }

    public void setPopeSpacePos(int popeSpacePos) {
        this.popeSpacePos = popeSpacePos;
    }

    public void setSectionVictoryPoints(int sectionVictoryPoints) {
        this.sectionVictoryPoints = sectionVictoryPoints;
    }
}
