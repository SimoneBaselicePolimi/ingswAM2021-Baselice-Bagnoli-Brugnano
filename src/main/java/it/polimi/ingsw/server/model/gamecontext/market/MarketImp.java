package it.polimi.ingsw.server.model.gamecontext.market;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.MarbleColour;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.marketrepresentation.ServerMarketRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerMarbleColourRepresentation;

import java.util.*;

/**
 * This class represents the Market structure, containing a specific number of coloured Marbles.
 * The marbles will be organized randomly in the Market matrix. Only one marble, at any moment, will be left out
 * of this market. A Player can select a row or column of the matrix, obtaining a copy of the marbles selected
 * and shifting the position of the marbles in the matrix.
 */
public class MarketImp implements Market {

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
	 * Market constructor.
	 * The marbles will be organized randomly in the Market matrix. One marble will be left out of this matrix.
	 * @param nRows number of rows in the matrix
	 * @param nColumns number of columns in the matrix
	 * @param marbles colour and number of the marbles inside the Market
	 * @throws WrongNumberOfMarblesException if a wrong number of marbles is passed as parameter
	 */
	public MarketImp(int nRows, int nColumns, Map<MarbleColour,Integer> marbles) throws WrongNumberOfMarblesException {
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
	public MarketImp(Random randGenerator, int nRows, int nColumns, Map<MarbleColour,Integer> marbles)
			throws WrongNumberOfMarblesException {
		this.randGenerator = randGenerator;
		initializeMatrixAndOutMarble(nRows, nColumns, marbles);
	}

	/**
	 * Initializes the Market matrix filled with Marbles and the single Marble on the slide.
	 * @param nRows number of rows in the matrix
	 * @param nColumns number of columns in the matrix
	 * @param marbles colour and number of the marbles inside the Market
	 * @throws WrongNumberOfMarblesException if the number of Marbles passed is not equal to nRows*nColumns+1
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
	@Override
	public MarbleColour[][] getMarbleMatrix() {
		return cloneMatrix(matrix);
	}

	/**
	 * Returns the number of columns of the matrix
	 * @return num of columns of the matrix
	 */
	@Override
	public int getNumOfColumns() {
		return nColumns;
	}

	/**
	 * Returns the number of rows of the matrix
	 * @return num of rows of the matrix
	 */
	@Override
	public int getNumOfRows() {
		return nRows;
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
	@Override
	public MarbleColour getOutMarble() {
		return outMarble;
	}

	/**
	 * Returns the chosen row of Marbles. Inserts the Marble on the slide in the chosen row, so as to push the Marbles
	 * in the correct direction and to locate a different Marble on the slide.
	 * @param row chosen row of Marbles to get Resources from the Market
	 * @return array of selected Marbles
	 * @throws IllegalArgumentException if the row passed as parameter is negative or if it's greater than the
	 * number of rows of the Market matrix
	 */
	@Override
	public MarbleColour[ ] fetchMarbleRow(int row) throws IllegalArgumentException{
		if(row<0 || row>=nRows)
			throw new IllegalArgumentException();

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
	 * @throws IllegalArgumentException if the column passed as parameter is negative or if it's greater than the
	 * number of columns of the Market matrix
	 */
	@Override
	public MarbleColour[ ] fetchMarbleColumn(int column) throws IllegalArgumentException{
		if(column<0 || column>=nColumns)
			throw new IllegalArgumentException();

		MarbleColour[] marbleColumn = new MarbleColour[nRows];

		for(int r=0; r<nRows; r++)
			marbleColumn[r] = matrix[r][column];

		for(int r=0; r<nRows-1; r++)
			matrix[r][column] = matrix[r+1][column];
		matrix[nRows-1][column] = outMarble;

		outMarble = marbleColumn[0];

		return marbleColumn;
	}

	@Override
	public ServerMarketRepresentation getServerRepresentation() {
		return new ServerMarketRepresentation(
			Arrays.stream(matrix)
				.map(m -> Arrays.stream(m)
					.map(MarbleColour::getServerRepresentation)
					.toArray(ServerMarbleColourRepresentation[]::new)
				).toArray(ServerMarbleColourRepresentation[][]::new),
			outMarble.getServerRepresentation(),
			nRows,
			nColumns
		);
	}

	@Override
	public ServerMarketRepresentation getServerRepresentationForPlayer(Player player) {
		return getServerRepresentation();
	}
}
