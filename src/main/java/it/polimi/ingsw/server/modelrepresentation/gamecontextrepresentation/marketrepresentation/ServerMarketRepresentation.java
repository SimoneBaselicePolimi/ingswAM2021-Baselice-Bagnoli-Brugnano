package it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.marketrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.modelrepresentation.ServerRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerMarbleColourRepresentation;

public class ServerMarketRepresentation extends ServerRepresentation {

    /**
     * Matrix representing the Market, filled with Marbles
     */
    public final ServerMarbleColourRepresentation[][] matrix;

    /**
     * Single Marble on the slide, outside the Market
     */
    public final ServerMarbleColourRepresentation outMarble;

    /**
     * Number of rows of the Market
     */
    public final int nRows;

    /**
     * Number of columns of the Market
     */
    public final int nColumns;

    /**
     * Class constructor.
     * @param matrix Matrix representing the Market, filled with Marbles
     * @param outMarble Single Marble on the slide, outside the Market
     * @param nRows Number of rows of the Market
     * @param nColumns Number of columns of the Market
     */
    public ServerMarketRepresentation(
        @JsonProperty("matrix") ServerMarbleColourRepresentation[][] matrix,
        @JsonProperty("outMarble") ServerMarbleColourRepresentation outMarble,
        @JsonProperty("nRows") int nRows,
        @JsonProperty("nColumns") int nColumns
    ) {
        this.matrix = matrix;
        this.outMarble = outMarble;
        this.nRows = nRows;
        this.nColumns = nColumns;
    }

}
