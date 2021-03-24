package it.polimi.ingsw.server.model.gamecontext.market;

import it.polimi.ingsw.server.model.gameitems.MarbleColour;

import java.util.Map;
import java.util.Random;

public class Market {

	Random randGenerator = new Random();

	MarbleColour[][] matrix;
	MarbleColour outMarble;

	public Market(int nRows, int nColumns, Map<MarbleColour,Integer> marbles) throws WrongNumberOfMarblesException {
		matrix = new MarbleColour[nRows][nColumns];
		if (marbles.values().stream().mapToInt(i -> i).sum() != nRows*nColumns + 1) //marbles in matrix + 1 marble out
			throw new WrongNumberOfMarblesException();
		int i=0;
		//TODO
        // To generate a random number from [0, N) use randGenerator.nextInt(N)
	}

	public Market(Random randGenerator, int nRows, int nColumns, Map<MarbleColour,Integer> marbles) throws WrongNumberOfMarblesException {
		this(nRows, nColumns, marbles);
		this.randGenerator = randGenerator;
	}


	public MarbleColour[ ][ ] getMarbleMatrix() {
		return matrix;
	}

	public MarbleColour getOutMarble() {
		return outMarble;
	}

	public MarbleColour[ ] fetchMarbleRow(int row) {
		return null;
	}

	public MarbleColour[ ] fetchMarbleColumn(int column) {
		return null;
	}

}
