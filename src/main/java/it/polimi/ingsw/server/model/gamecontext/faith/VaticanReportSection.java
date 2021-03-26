package it.polimi.ingsw.server.model.gamecontext.faith;

public class VaticanReportSection {
    public final int sectionInitialPos;
    public final int popeSpacePos;
    public final int sectionVictoryPoints;

    public VaticanReportSection(int sectionInitialPos, int popeSpacePos, int sectionVictoryPoints) {
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
