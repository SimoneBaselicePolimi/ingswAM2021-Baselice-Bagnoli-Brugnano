package it.polimi.ingsw.server.model.gamecontext.faith;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Representable;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.faithrepresentation.ServerVaticanReportSectionRepresentation;

/**
 * This class contains the characteristics of a specific Vatican Report section, in which Vatican Reports can occur.
 */
public class VaticanReportSection implements Representable<ServerVaticanReportSectionRepresentation> {
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
    public VaticanReportSection(int sectionInitialPos, int popeSpacePos, int sectionVictoryPoints) {
        this.sectionInitialPos = sectionInitialPos;
        this.popeSpacePos = popeSpacePos;
        this.sectionVictoryPoints = sectionVictoryPoints;
    }

    /**
     * Method to get the position in which this Vatican Report section start.
     * @return position in which this Vatican Report section start
     */
    public int getSectionInitialPos() {
        return sectionInitialPos;
    }
    /**
     * Method to get the position of the Pope space related to this Vatican Report section.
     * @return position of the Pope space in the Faith Track
     */
    public int getPopeSpacePos() {
        return popeSpacePos;
    }
    /**
     * Method to get the victory points scored by a Player for the related Pope's Favor card turned face-up (active).
     * @return number of victory points scored for the activated Pope's Favor card
     */
    public int getSectionVictoryPoints() {
        return sectionVictoryPoints;
    }

    @Override
    public ServerVaticanReportSectionRepresentation getServerRepresentation() {
        return new ServerVaticanReportSectionRepresentation(sectionInitialPos, popeSpacePos, sectionVictoryPoints);
    }

    @Override
    public ServerVaticanReportSectionRepresentation getServerRepresentationForPlayer(Player player) {
        return getServerRepresentation();
    }
}
