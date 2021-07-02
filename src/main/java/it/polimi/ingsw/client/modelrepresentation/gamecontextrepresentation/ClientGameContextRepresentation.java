package it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.faithrepresentation.ClientFaithPathRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.faithrepresentation.ClientFaithPathSinglePlayerRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.marketrepresentation.ClientMarketRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardsTableRepresentation;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsMapWithIdKey;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ClientGameContextRepresentation extends ClientRepresentation {

    private final ClientMarketRepresentation market;
    private final ClientDevelopmentCardsTableRepresentation developmentCardsTable;
    private final ClientFaithPathRepresentation faithPath;

    private final Optional<ClientFaithPathSinglePlayerRepresentation> singlePlayerFaithPath;

    //TODO @SerializeAsListWithId
    private final List<Player> playersOrder;

    @SerializeIdOnly
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
        this.playersOrder = playersOrder;
        this.playerContexts = playerContexts;
        this.activePlayer = activePlayer;

        if(playersOrder.size() == 1) {
            this.singlePlayerFaithPath = Optional.of(new ClientFaithPathSinglePlayerRepresentation(
                faithPath.getFaithPathLength(),
                faithPath.getVaticanReportSections(),
                faithPath.getVictoryPointsByPosition(),
                faithPath.getFaithPositions(),
                faithPath.getPopeFavorCards(),
                faithPath.getVictoryPoints(),
                0
            ));
            this.faithPath = this.singlePlayerFaithPath.get();
        } else {
            this.faithPath = faithPath;
            this.singlePlayerFaithPath = Optional.empty();
        }

    }

    public boolean isSinglePlayerGame() {
        return singlePlayerFaithPath.isPresent();
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

    public Optional<ClientFaithPathSinglePlayerRepresentation> getSinglePlayerFaithPath() {
        return singlePlayerFaithPath;
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
        notifyViews();
    }
}
