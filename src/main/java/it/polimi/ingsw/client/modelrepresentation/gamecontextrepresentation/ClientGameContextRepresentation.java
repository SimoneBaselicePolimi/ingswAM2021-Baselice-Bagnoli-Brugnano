package it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardsTableRepresentation;
import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.faithrepresentation.ClientFaithPathRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.marketrepresentation.ClientMarketRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsMapWithIdKey;

import java.util.List;
import java.util.Map;

public class ClientGameContextRepresentation extends ClientRepresentation {

    private final ClientMarketRepresentation market;
    private final ClientDevelopmentCardsTableRepresentation developmentCardsTable;
    private final ClientFaithPathRepresentation faithPath;
    private final List<Player> playersOrder;
    private Player activePlayer;

    @SerializeAsMapWithIdKey
    private final Map<Player, ClientPlayerContextRepresentation> playerContexts;

    public ClientGameContextRepresentation(
        @JsonProperty("market") ClientMarketRepresentation market,
        @JsonProperty("developmentCardsTable") ClientDevelopmentCardsTableRepresentation developmentCardsTable,
        @JsonProperty("faithPath") ClientFaithPathRepresentation faithPath,
        @JsonProperty("playersOrder") List<Player> playersOrder,
        @JsonProperty("playerContexts") Map<Player, ClientPlayerContextRepresentation> playerContexts,
        @JsonProperty("activePlayer") Player activePlayer
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

    public List<Player> getPlayersOrder() {
        return playersOrder;
    }

    public ClientPlayerContextRepresentation getPlayerContext(Player playerRepresentation) {
        return playerContexts.get(playerRepresentation);
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }
}
