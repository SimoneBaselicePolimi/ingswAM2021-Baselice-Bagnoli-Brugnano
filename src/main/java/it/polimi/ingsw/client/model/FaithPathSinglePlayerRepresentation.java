package it.polimi.ingsw.client.model;

public class FaithPathSinglePlayerRepresentation extends FaithPathRepresentation {

    /**
     * The position in the Faith Track of Lorenzo Magnifico, represented by a Black Cross token
     */
    private int blackCrossFaithPosition;

    public FaithPathSinglePlayerRepresentation(int blackCrossFaithPosition) {
        this.blackCrossFaithPosition = blackCrossFaithPosition;
    }

    public int getBlackCrossFaithPosition() {
        return blackCrossFaithPosition;
    }

    public void setBlackCrossFaithPosition(int blackCrossFaithPosition) {
        this.blackCrossFaithPosition = blackCrossFaithPosition;
    }
}
