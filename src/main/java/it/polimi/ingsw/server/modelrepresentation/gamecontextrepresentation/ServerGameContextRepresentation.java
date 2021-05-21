package it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;
import it.polimi.ingsw.server.modelrepresentation.ServerRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.faithrepresentation.ServerFaithPathRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.marketrepresentation.ServerMarketRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ServerPlayerContextRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ServerDevelopmentCardsTableRepresentation;

import java.util.List;
import java.util.Map;

public class ServerGameContextRepresentation extends ServerRepresentation {

    public final ServerMarketRepresentation market;
    public final ServerDevelopmentCardsTableRepresentation developmentCardsTable;
    public final ServerFaithPathRepresentation faithPath;
    public final List<ServerPlayerRepresentation> playersOrder;
    public final Map<ServerPlayerRepresentation, ServerPlayerContextRepresentation> playerContexts;
    public final ServerPlayerRepresentation activePlayer;

    public ServerGameContextRepresentation(
        @JsonProperty("market") ServerMarketRepresentation market,
        @JsonProperty("developmentCardsTable") ServerDevelopmentCardsTableRepresentation developmentCardsTable,
        @JsonProperty("faithPath") ServerFaithPathRepresentation faithPath,
        @JsonProperty("playersOrder") List<ServerPlayerRepresentation> playersOrder,
        @JsonProperty("playerContexts") Map<ServerPlayerRepresentation, ServerPlayerContextRepresentation> playerContexts,
        @JsonProperty("activePlayer") ServerPlayerRepresentation activePlayer
    ) {
        this.market = market;
        this.developmentCardsTable = developmentCardsTable;
        this.faithPath = faithPath;
        this.playersOrder = playersOrder;
        this.playerContexts = playerContexts;
        this.activePlayer = activePlayer;
    }
}
