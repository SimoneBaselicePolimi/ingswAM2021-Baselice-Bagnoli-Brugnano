package it.polimi.ingsw.server.model.gamecontext.market;

import it.polimi.ingsw.server.model.gameitems.MarbleColour;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MarketTest {

    // Marbles initialization
    Map<MarbleColour,Integer> test21marbles = Map.of(
        new MarbleColour(
                Optional.of(ResourceType.SERVANTS), 1, false, "RedMarble"
        ), 8,
        new MarbleColour(
                Optional.of(ResourceType.COINS), 0, false, "BlueMarble"
        ), 5,
        new MarbleColour(
                Optional.of(ResourceType.STONES), 0, false, "YellowMarble"
        ), 3,
        new MarbleColour(
                Optional.empty(), 0, true, "AnotherBoringColorMarble"
        ), 5
    );

    /**
     * Tests the Market initialization.
     * Initializes the dimensions of the Market, creates a Map of Marbles, invokes the Market constructor passing
     * the Map and the dimensions of the Market as parameters; then gets the Marbles from the Market, counts them
     * and compares them to the ones put in the first Map.
     * @throws WrongNumberOfMarblesException if the number of Marbles is not equal to nRows*nColumns+1
     */
    @Test
    void testMarketInitialization() throws WrongNumberOfMarblesException {
        int nRow = 2;
        int nColumn = 3;
        Map<MarbleColour,Integer> marbles = new HashMap<>();
        marbles.put(new MarbleColour(Optional.of(ResourceType.SERVANTS), 1, false, "RedMarble"),5);
        marbles.put(new MarbleColour(Optional.of(ResourceType.SHIELDS), 0, false, "GreyMarble"),2);
        Market market = new Market(nRow, nColumn, marbles);
        assertNotNull(market.getMarbleMatrix());
        assertEquals(nRow,market.getMarbleMatrix().length);
        assertEquals(nColumn, market.getMarbleMatrix()[0].length);
        Map<MarbleColour,Integer> marblesTest = new HashMap<>();
        for(int i=0; i<nRow; i++)
            for(int j=0; j<nColumn; j++)
                marblesTest.compute(market.getMarbleMatrix()[i][j], (k, v) -> v!=null ? v+1 : 1);
        marblesTest.compute(market.getOutMarble(), (k, v) -> v!=null ? v+1 : 1);
        assertEquals(marbles, marblesTest);
    }

    /**
     * Tests the Market initialization passing to the constructor a wrong number of Marbles.
     * Initializes the dimensions of the Market, creates a Map of Marbles (the sum of the Marbles does not equal
     * the size of the Market), invokes the Market constructor and expects the Exception to be thrown.
     * @throws WrongNumberOfMarblesException if the number of Marbles is not equal to nRows*nColumns+1
     */
    @Test
    void testMarketInitializationWrongNumberOfMarbles() throws WrongNumberOfMarblesException {
        int nRow = 2;
        int nColumn = 3;
        Map<MarbleColour,Integer> marbles = new HashMap<>();
        marbles.put(new MarbleColour(Optional.of(ResourceType.SERVANTS), 1, false, "RedMarble"), 3);
        marbles.put(new MarbleColour(Optional.of(ResourceType.SHIELDS), 0, false, "GreyMarble"), 3);
        assertThrows(
            WrongNumberOfMarblesException.class,
            () -> new Market(nRow, nColumn, marbles),
            "If the number of given marbles is not equal to nRow * nColumn + 1 an exception should be thrown"
        );
    }

    /**
     * Tests the Market initialization passing to the constructor a specific type of random number generator.
     * Checks the equality between Markets generated by the same seed (passed to the random number generator)
     * and the inequality between Markets generated by different seeds.
     * Checks also the equality (inequality) of Marbles on the slide, left outside the Market.
     * @throws WrongNumberOfMarblesException if the number of Marbles is not equal to nRows*nColumns+1
     */

    @Test
    void testMarketInitializationRandomness() throws WrongNumberOfMarblesException {
        int nRow = 4;
        int nCol = 5;
        Market market1 = new Market(new Random(1), nRow, nCol, test21marbles);
        Market market1Copy = new Market(new Random(1), nRow, nCol, test21marbles);
        Market market2 = new Market(new Random(2), nRow, nCol, test21marbles);
        assertTrue(marbleMatricesEquals(market1.getMarbleMatrix(), market1Copy.getMarbleMatrix()));
        assertEquals(market1.getOutMarble(), market1Copy.getOutMarble());
        assertFalse(marbleMatricesEquals(market1.getMarbleMatrix(), market2.getMarbleMatrix()));
        assertNotEquals(market1.getOutMarble(), market2.getOutMarble());
    }

    /**
     * Tests the fetch of a specific Marble row in the Market.
     * Initializes the Market, invokes the fetch of a chosen row of the Market, verifies that the method returns
     * a copy of the Marbles selected and shifts the position of the Marbles in the Matrix.
     * @throws IllegalArgumentException if the chosen row is negative or if it's greater than the number of rows
     * of the Market matrix
     * @throws WrongNumberOfMarblesException if the number of Marbles is not equal to nRows*nColumns+1
     */
    @Test
    void testFetchMarbleRow() throws IllegalArgumentException, WrongNumberOfMarblesException {
        int nRows = 5, nColumns = 4;
        Market market = new Market(nRows, nColumns, test21marbles);
        MarbleColour[][] oldMatrix = market.getMarbleMatrix();
        MarbleColour oldOutMarble = market.getOutMarble();
        int chosenRow = 2;
        assertArrayEquals(oldMatrix[chosenRow], market.fetchMarbleRow(chosenRow));
        assertEquals(oldMatrix[chosenRow][0], market.getOutMarble());
        for (int i = 0; i < nColumns - 1; i++) {
            assertEquals(oldMatrix[chosenRow][i+1], market.getMarbleMatrix()[chosenRow][i]);
        }
        assertEquals(oldOutMarble, market.getMarbleMatrix()[chosenRow][nColumns-1]);
    }

    /**
     * Tests the fetch of a specific Marble column in the Market.
     * Initializes the Market, invokes the fetch of a chosen column of the Market, verifies that the method returns
     * a copy of the Marbles selected and shifts the position of the Marbles in the Matrix.
     * @throws IllegalArgumentException if the chosen column is negative or if it's greater than the number of rows
     * of the Market matrix
     * @throws WrongNumberOfMarblesException if the number of Marbles is not equal to nRows*nColumns+1
     */
    @Test
    void testFetchMarbleColumn() throws IllegalArgumentException, WrongNumberOfMarblesException {
        int nRows = 5, nColumns = 4;
        Market market = new Market(nRows, nColumns, test21marbles);
        MarbleColour[][] oldMatrix = market.getMarbleMatrix();
        MarbleColour oldOutMarble = market.getOutMarble();
        int chosenColumn = 3;
        MarbleColour[] obtainedMarbles = market.fetchMarbleColumn(chosenColumn);
        assertEquals(nRows, obtainedMarbles.length);
        for (int i = 0; i < nRows; i++) {
            assertEquals(oldMatrix[i][chosenColumn], obtainedMarbles[i]);
        }
        assertEquals(oldMatrix[0][chosenColumn], market.getOutMarble());
        for (int i = 0; i < nRows - 1; i++) {
            assertEquals(oldMatrix[i+1][chosenColumn], market.getMarbleMatrix()[i][chosenColumn]);
        }
        assertEquals(oldOutMarble, market.getMarbleMatrix()[nRows-1][chosenColumn]);
    }

    // Verifies the equality between two Matrices
    static boolean marbleMatricesEquals(MarbleColour[][] matrixA, MarbleColour[][] matrixB) {
        if(matrixA.length != matrixB.length || (matrixA.length != 0 && matrixA[0].length != matrixB[0].length))
            return false;
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                if(!matrixA[i][j].equals(matrixB[i][j]))
                    return false;
            }
        }
        return true;
    }

}