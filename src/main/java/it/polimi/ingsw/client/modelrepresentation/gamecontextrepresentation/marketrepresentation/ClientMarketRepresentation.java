package it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.marketrepresentation;

import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientMarbleColourRepresentation;
import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;

public class ClientMarketRepresentation extends ClientRepresentation {

    /**
     * Matrix representing the Market, filled with Marbles
     */
    private ClientMarbleColourRepresentation[][] matrix;

    /**
     * Single Marble on the slide, outside the Market
     */
    private ClientMarbleColourRepresentation outMarble;

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
    public ClientMarketRepresentation(ClientMarbleColourRepresentation[][] matrix, ClientMarbleColourRepresentation outMarble, int nRows, int nColumns) {
        this.matrix = matrix;
        this.outMarble = outMarble;
        this.nRows = nRows;
        this.nColumns = nColumns;
    }

    public ClientMarbleColourRepresentation[][] getMatrix() {
        return matrix;
    }

    public ClientMarbleColourRepresentation getOutMarble() {
        return outMarble;
    }

    public int getnRows() {
        return nRows;
    }

    public int getnColumns() {
        return nColumns;
    }

    public void setMatrix(ClientMarbleColourRepresentation[][] matrix) {
        this.matrix = matrix;
    }

    public void setOutMarble(ClientMarbleColourRepresentation outMarble) {
        this.outMarble = outMarble;
    }

}
