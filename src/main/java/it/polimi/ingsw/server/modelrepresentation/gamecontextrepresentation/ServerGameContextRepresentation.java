package it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.modelrepresentation.ServerRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.faithrepresentation.ServerFaithPathRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.marketrepresentation.ServerMarketRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ServerPlayerContextRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ServerDevelopmentCardsTableRepresentation;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsMapWithIdKey;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.List;
import java.util.Map;

public class ServerGameContextRepresentation extends ServerRepresentation {

    public final ServerMarketRepresentation market;
    public final ServerDevelopmentCardsTableRepresentation developmentCardsTable;
    public final ServerFaithPathRepresentation faithPath;

    //TODO @SerializeAsListWithId
    public final List<Player> playersOrder;

    @SerializeAsMapWithIdKey
    public final Map<Player, ServerPlayerContextRepresentation> playerContexts;

    @SerializeIdOnly
    public final Player activePlayer;

    public ServerGameContextRepresentation(
        @JsonProperty("market") ServerMarketRepresentation market,
        @JsonProperty("developmentCardsTable") ServerDevelopmentCardsTableRepresentation developmentCardsTable,
        @JsonProperty("faithPath") ServerFaithPathRepresentation faithPath,
        @JsonProperty("playersOrder") List<Player> playersOrder,
        @JsonProperty("playerContexts") Map<Player, ServerPlayerContextRepresentation> playerContexts,
        @JsonProperty("activePlayer") Player activePlayer
    ) {
        this.market = market;
        this.developmentCardsTable = developmentCardsTable;
        this.faithPath = faithPath;
        this.playersOrder = playersOrder;
        this.playerContexts = playerContexts;
        this.activePlayer = activePlayer;
    }
}
