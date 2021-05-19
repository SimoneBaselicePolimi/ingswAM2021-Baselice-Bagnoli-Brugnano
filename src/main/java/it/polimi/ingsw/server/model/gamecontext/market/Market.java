package it.polimi.ingsw.server.model.gamecontext.market;

import it.polimi.ingsw.server.model.Representable;
import it.polimi.ingsw.server.model.gameitems.MarbleColour;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.marketrepresentation.ServerMarketRepresentation;

public interface Market extends Representable<ServerMarketRepresentation> {
    /**
     * Returns the Market structure.
     * @return matrix of Marbles inside the Market
     */
    MarbleColour[][] getMarbleMatrix();

    /**
     * Returns the number of columns of the matrix
     * @return num of columns of the matrix
     */
    int getNumOfColumns();

    /**
     * Returns the number of rows of the matrix
     * @return num of rows of the matrix
     */
    int getNumOfRows();

    /**
     * Returns the single Marble on the slide, outside the Market.
     * @return Marble outside the Market
     */
    MarbleColour getOutMarble();

    /**
     * Returns the chosen row of Marbles. Inserts the Marble on the slide in the chosen row, so as to push the Marbles
     * in the correct direction and to locate a different Marble on the slide.
     * @param row chosen row of Marbles to get Resources from the Market
     * @return array of selected Marbles
     * @throws IllegalArgumentException if the row passed as parameter is negative or if it's greater than the
     * number of rows of the Market matrix
     */
    MarbleColour[ ] fetchMarbleRow(int row) throws IllegalArgumentException;

    /**
     * Returns the chosen column of Marbles. Inserts the Marble on the slide in the chosen column, so as to push
     * the Marbles in the correct direction and to locate a different Marble on the slide.
     * @param column chosen column of Marbles to get Resources from the Market
     * @return array of selected Marbles
     * @throws IllegalArgumentException if the column passed as parameter is negative or if it's greater than the
     * number of columns of the Market matrix
     */
    MarbleColour[ ] fetchMarbleColumn(int column) throws IllegalArgumentException;
}
