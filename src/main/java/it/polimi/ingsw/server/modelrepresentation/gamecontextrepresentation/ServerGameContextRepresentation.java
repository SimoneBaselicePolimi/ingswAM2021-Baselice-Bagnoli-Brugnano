package it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation;

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
        ServerMarketRepresentation market,
        ServerDevelopmentCardsTableRepresentation developmentCardsTable,
        ServerFaithPathRepresentation faithPath,
        List<ServerPlayerRepresentation> playersOrder,
        Map<ServerPlayerRepresentation, ServerPlayerContextRepresentation> playerContexts,
        ServerPlayerRepresentation activePlayer
    ) {
        this.market = market;
        this.developmentCardsTable = developmentCardsTable;
        this.faithPath = faithPath;
        this.playersOrder = playersOrder;
        this.playerContexts = playerContexts;
        this.activePlayer = activePlayer;
    }
}
