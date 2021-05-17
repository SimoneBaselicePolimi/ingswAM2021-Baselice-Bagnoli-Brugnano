package it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation;

import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardsTableRepresentation;
import it.polimi.ingsw.client.modelrepresentation.ClientPlayerRepresentation;
import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.faithrepresentation.ClientFaithPathRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.marketrepresentation.ClientMarketRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;

import java.util.List;
import java.util.Map;

public class ClientGameContextRepresentation extends ClientRepresentation {

    private final ClientMarketRepresentation market;
    private final ClientDevelopmentCardsTableRepresentation developmentCardsTable;
    private final ClientFaithPathRepresentation faithPath;
    private final List<ClientPlayerRepresentation> playersOrder;
    private final Map<ClientPlayerRepresentation, ClientPlayerContextRepresentation> playerContexts;
    private ClientPlayerRepresentation activePlayer;

    public ClientGameContextRepresentation(
        ClientMarketRepresentation market,
        ClientDevelopmentCardsTableRepresentation developmentCardsTable,
        ClientFaithPathRepresentation faithPath,
        List<ClientPlayerRepresentation> playersOrder,
        Map<ClientPlayerRepresentation, ClientPlayerContextRepresentation> playerContexts,
        ClientPlayerRepresentation activePlayer
    ) {
        this.market = market;
        this.developmentCardsTable = developmentCardsTable;
        this.faithPath = faithPath;
        this.playersOrder = playersOrder;
        this.playerContexts = playerContexts;
        this.activePlayer = activePlayer;
    }

    public ClientMarketRepresentation getMarket() {
        return market;
    }

    public ClientDevelopmentCardsTableRepresentation getDevelopmentCardsTable() {
        return developmentCardsTable;
    }

    public ClientFaithPathRepresentation getFaithPath() {
        return faithPath;
    }

    public List<ClientPlayerRepresentation> getPlayersOrder() {
        return playersOrder;
    }

    public ClientPlayerContextRepresentation getPlayerContext(ClientPlayerRepresentation playerRepresentation) {
        return playerContexts.get(playerRepresentation);
    }

    public ClientPlayerRepresentation getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(ClientPlayerRepresentation activePlayer) {
        this.activePlayer = activePlayer;
    }
}
