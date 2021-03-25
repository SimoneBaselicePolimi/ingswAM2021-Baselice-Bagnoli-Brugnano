package it.polimi.ingsw.server.model.gamecontext.market;

import it.polimi.ingsw.server.model.gameitems.MarbleColour;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * This class represents the Market structure, containing a specific number of coloured Marbles
 * a Player can select taking Resources from them.
 */
public class Market {
	/**
	 * A random number generator used to place Marbles in the Market
	 */
	Random randGenerator;
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
	private int nRows;
	/**
	 * Number of columns of the Market
	 */
	private int nColumns;

	/**
	 *  Market constructor.
	 * @param nRows number of rows in the matrix
	 * @param nColumns number of columns in the matrix
	 * @param marbles colour and number of the marbles inside the Market
	 * @throws WrongNumberOfMarblesException if a wrong number of marbles is passed as parameter
	 */
	public Market(int nRows, int nColumns, Map<MarbleColour,Integer> marbles) throws WrongNumberOfMarblesException {
		randGenerator = new Random();
		initializeMatrixAndOutMarble(nRows, nColumns, marbles);
		}

	/**
	 * Market constructor specifying a type of random number generator.
	 * @param randGenerator random number generator
	 * @param nRows number of rows in the matrix
	 * @param nColumns number of columns in the matrix
	 * @param marbles colour and number of the marbles inside the Market
	 * @throws WrongNumberOfMarblesException if a wrong number of marbles is passed as parameter
	 */
	public Market(Random randGenerator, int nRows, int nColumns, Map<MarbleColour,Integer> marbles)
			throws WrongNumberOfMarblesException {
		this.randGenerator = randGenerator;
		initializeMatrixAndOutMarble(nRows, nColumns, marbles);
	}

	/**
	 * Initializes the Market matrix filled with Marbles and the single Marble on the slide.
	 * @param nRows number of rows in the matrix
	 * @param nColumns number of columns in the matrix
	 * @param marbles colour and number of the marbles inside the Market
	 * @throws WrongNumberOfMarblesException if a wrong number of marbles is passed as parameter
	 */
	private void initializeMatrixAndOutMarble(int nRows, int nColumns, Map<MarbleColour,Integer> marbles)
			throws WrongNumberOfMarblesException{
		this.nRows = nRows;
		this. nColumns = nColumns;

		matrix = new MarbleColour[nRows][nColumns];
		if (marbles.values().stream().mapToInt(i -> i).sum() != nRows*nColumns + 1) //Marbles in matrix + 1 Marble out
			throw new WrongNumberOfMarblesException();

		// Marbles Map in a List
		List<MarbleColour> marblesList = new ArrayList<>();
		for(MarbleColour m : marbles.keySet() )
			for (int v = 0; v < marbles.get(m); v++)
				marblesList.add(m);

		// Every cell of the Market matrix is filled with a Marble randomly chosen from the List
		for(int r=0; r<nRows; r++)
			for(int c=0; c<nColumns; c++) {
				int randNum = randGenerator.nextInt(marblesList.size());
				matrix[r][c] = marblesList.remove(randNum);
			}

		outMarble = marblesList.get(0);
	}

	/**
	 * Returns the Market structure.
	 * @return matrix of Marbles inside the Market
	 */
	public MarbleColour[][] getMarbleMatrix() {
		return cloneMatrix(matrix);
	}

	/**
	 * Returns a shallow copy of the Market structure.
	 * @param matrix matrix of the Marbles inside the Market
	 * @return matrix representing a copy of the Market
	 */
	static MarbleColour[][] cloneMatrix(MarbleColour[][] matrix) {
		MarbleColour[][] newMatrix = new MarbleColour[matrix.length][];
		for (int i = 0; i < matrix.length; i++) {
			newMatrix[i] = matrix[i].clone();
		}
		return newMatrix;
	}

	/**
	 * Returns the single Marble on the slide, outside the Market.
	 * @return Marble outside the Market
	 */
	public MarbleColour getOutMarble() {
		return outMarble;
	}

	/**
	 * Returns the chosen row of Marbles. Inserts the Marble on the slide in the chosen row, so as to push the Marbles
	 * in the correct direction and to locate a different Marble on the slide.
	 * @param row chosen row of Marbles to get Resources from the Market
	 * @return array of selected Marbles
	 */
	public MarbleColour[ ] fetchMarbleRow(int row) {
		MarbleColour[] marbleRow = new MarbleColour[nColumns];

		for(int c=0; c<nColumns; c++)
			marbleRow[c] = matrix[row][c];

		for(int c=0; c<nColumns-1; c++)
			matrix[row][c] = matrix[row][c+1];

		matrix[row][nColumns-1] = outMarble;

		outMarble = marbleRow[0];

		return marbleRow;
	}

	/**
	 * Returns the chosen column of Marbles. Inserts the Marble on the slide in the chosen column, so as to push
	 * the Marbles in the correct direction and to locate a different Marble on the slide.
	 * @param column chosen column of Marbles to get Resources from the Market
	 * @return array of selected Marbles
	 */
	public MarbleColour[ ] fetchMarbleColumn(int column) {
		MarbleColour[] marbleColumn = new MarbleColour[nRows];

		for(int r=0; r<nRows; r++)
			marbleColumn[r] = matrix[r][column];

		for(int r=0; r<nRows-1; r++)
			matrix[r][column] = matrix[r+1][column];
		matrix[nRows-1][column] = outMarble;

		outMarble = marbleColumn[0];

		return marbleColumn;
	}
}
