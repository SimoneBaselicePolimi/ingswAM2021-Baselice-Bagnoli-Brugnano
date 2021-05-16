package it.polimi.ingsw.client.model;

public class MarketRepresentation extends Representation {

    /**
     * Matrix representing the Market, filled with Marbles
     */
    private MarbleColourRepresentation[][] matrix;

    /**
     * Single Marble on the slide, outside the Market
     */
    private MarbleColourRepresentation outMarble;

    /**
     * Number of rows of the Market
     */
    private final int nRows;

    /**
     * Number of columns of the Market
     */
    private final int nColumns;

    /**
     * Class constructor.
     * @param matrix Matrix representing the Market, filled with Marbles
     * @param outMarble Single Marble on the slide, outside the Market
     * @param nRows Number of rows of the Market
     * @param nColumns Number of columns of the Market
     */
    public MarketRepresentation(MarbleColourRepresentation[][] matrix, MarbleColourRepresentation outMarble, int nRows, int nColumns) {
        this.matrix = matrix;
        this.outMarble = outMarble;
        this.nRows = nRows;
        this.nColumns = nColumns;
    }

    public MarbleColourRepresentation[][] getMatrix() {
        return matrix;
    }

    public MarbleColourRepresentation getOutMarble() {
        return outMarble;
    }

    public int getnRows() {
        return nRows;
    }

    public int getnColumns() {
        return nColumns;
    }

    public void setMatrix(MarbleColourRepresentation[][] matrix) {
        this.matrix = matrix;
    }

    public void setOutMarble(MarbleColourRepresentation outMarble) {
        this.outMarble = outMarble;
    }

}
