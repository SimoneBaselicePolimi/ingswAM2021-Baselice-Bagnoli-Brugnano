package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.gameitems.MarbleColour;

public class MarketRepresentation extends Representation {

    /**
     * Matrix representing the Market, filled with Marbles
     */
    MarbleColour[][] matrix;

    /**
     * Single Marble on the slide, outside the Market
     */
    MarbleColour outMarble;

    /**
     * Number of rows of the Market
     */
    int nRows;

    /**
     * Number of columns of the Market
     */
    int nColumns;

    /**
     * Class constructor.
     * @param matrix Matrix representing the Market, filled with Marbles
     * @param outMarble Single Marble on the slide, outside the Market
     * @param nRows Number of rows of the Market
     * @param nColumns Number of columns of the Market
     */
    public MarketRepresentation(MarbleColour[][] matrix, MarbleColour outMarble, int nRows, int nColumns) {
        this.matrix = matrix;
        this.outMarble = outMarble;
        this.nRows = nRows;
        this.nColumns = nColumns;
    }

    public MarbleColour[][] getMatrix() {
        return matrix;
    }

    public MarbleColour getOutMarble() {
        return outMarble;
    }

    public int getnRows() {
        return nRows;
    }

    public int getnColumns() {
        return nColumns;
    }

    public void setMatrix(MarbleColour[][] matrix) {
        this.matrix = matrix;
    }

    public void setOutMarble(MarbleColour outMarble) {
        this.outMarble = outMarble;
    }

}
