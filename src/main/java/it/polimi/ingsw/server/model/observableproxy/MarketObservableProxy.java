package it.polimi.ingsw.server.model.observableproxy;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.market.Market;
import it.polimi.ingsw.server.model.gameitems.MarbleColour;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.gameupdate.ServerGameUpdate;
import it.polimi.ingsw.server.model.gameupdate.ServerMarketUpdate;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.marketrepresentation.ServerMarketRepresentation;

import java.util.HashSet;
import java.util.Set;

public class MarketObservableProxy extends ObservableProxy<Market> implements Market {

    protected boolean hasSomethingChanged = false;

    public MarketObservableProxy(Market marketImp, GameManager gameManager) {
        super(marketImp, gameManager);
    }

    @Override
    public Set<ServerGameUpdate> getUpdates() {
        if (hasSomethingChanged) {
            hasSomethingChanged = false;
            return Set.of(new ServerMarketUpdate(imp.getMarbleMatrix(), imp.getOutMarble()));
        }
        else
            return new HashSet<>();
    }

    @Override
    public MarbleColour[][] getMarbleMatrix() {
        return imp.getMarbleMatrix();
    }

    @Override
    public int getNumOfColumns() {
        return imp.getNumOfColumns();
    }

    @Override
    public int getNumOfRows() {
        return imp.getNumOfRows();
    }

    @Override
    public MarbleColour getOutMarble() {
        return imp.getOutMarble();
    }

    @Override
    public MarbleColour[] fetchMarbleRow(int row) throws IllegalArgumentException {
        hasSomethingChanged = true;
        return imp.fetchMarbleRow(row);
    }

    @Override
    public MarbleColour[] fetchMarbleColumn(int column) throws IllegalArgumentException {
        hasSomethingChanged = true;
        return imp.fetchMarbleColumn(column);
    }

    @Override
    public ServerMarketRepresentation getServerRepresentation() {
        return imp.getServerRepresentation();
    }

    @Override
    public ServerMarketRepresentation getServerRepresentationForPlayer(Player player) {
        return imp.getServerRepresentationForPlayer(player);
    }
}
