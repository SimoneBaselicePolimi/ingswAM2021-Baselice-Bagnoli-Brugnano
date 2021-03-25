package it.polimi.ingsw.server.model.gamecontext.market;

import it.polimi.ingsw.server.model.gameitems.MarbleColour;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Market {

	Random randGenerator;

	MarbleColour[][] matrix;
	MarbleColour outMarble;
	private int nRows;
	private int nColumns;

	public Market(int nRows, int nColumns, Map<MarbleColour,Integer> marbles) throws WrongNumberOfMarblesException {
		randGenerator = new Random();
		initializeMatrixAndOutMarble(nRows, nColumns, marbles);
		}

	public Market(Random randGenerator, int nRows, int nColumns, Map<MarbleColour,Integer> marbles) throws WrongNumberOfMarblesException {
		this.randGenerator = randGenerator;
		initializeMatrixAndOutMarble(nRows, nColumns, marbles);
	}

	private void initializeMatrixAndOutMarble(int nRows, int nColumns, Map<MarbleColour,Integer> marbles) throws WrongNumberOfMarblesException{
		this.nRows = nRows;
		this. nColumns = nColumns;

		matrix = new MarbleColour[nRows][nColumns];
		if (marbles.values().stream().mapToInt(i -> i).sum() != nRows*nColumns + 1) //marbles in matrix + 1 marble out
			throw new WrongNumberOfMarblesException();

		List<MarbleColour> marblesList = new ArrayList<>();
		for(MarbleColour m : marbles.keySet() )
			for (int v = 0; v < marbles.get(m); v++)
				marblesList.add(m);

		for(int r=0; r<nRows; r++)
			for(int c=0; c<nColumns; c++) {
				int randNum = randGenerator.nextInt(marblesList.size());
				matrix[r][c] = marblesList.remove(randNum);
			}

		outMarble = marblesList.get(0);
	}

	public MarbleColour[ ][ ] getMarbleMatrix() {
		return cloneMatrix(matrix);
	}

	static MarbleColour[][] cloneMatrix(MarbleColour[][] matrix) {
		MarbleColour[][] newMatrix = new MarbleColour[matrix.length][];
		for (int i = 0; i < matrix.length; i++) {
			newMatrix[i] = matrix[i].clone();
		}
		return newMatrix;
	}

	public MarbleColour getOutMarble() {
		return outMarble;
	}

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
