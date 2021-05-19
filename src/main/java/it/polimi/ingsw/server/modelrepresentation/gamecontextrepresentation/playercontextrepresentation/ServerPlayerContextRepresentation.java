package it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.playercontextrepresentation;

import it.polimi.ingsw.client.modelrepresentation.ClientPlayerRepresentation;
import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientPlayerOwnedDevelopmentCardDeckRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientResourceStorageRepresentation;
import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;
import it.polimi.ingsw.server.modelrepresentation.ServerRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerGameActionRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.ServerProductionRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ServerPlayerOwnedDevelopmentCardDeckRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ServerLeaderCardRepresentation;
import it.polimi.ingsw.server.modelrepresentation.storagerepresentation.ServerResourceStorageRepresentation;

import java.util.List;
import java.util.Set;

public class ServerPlayerContextRepresentation extends ServerRepresentation {
    public final ServerPlayerRepresentation player;
    public final Set<ServerResourceStorageRepresentation> shelves;
    public final ServerResourceStorageRepresentation infiniteChest;
    public final ServerResourceStorageRepresentation tempStorage;
    public final int tempStarResources;
    public final Set<ServerLeaderCardRepresentation> leaderCardsPlayerOwns;
    public final List<ServerPlayerOwnedDevelopmentCardDeckRepresentation> developmentCardDecks;
    public final Set<ServerProductionRepresentation> baseProductions;

    public ServerPlayerContextRepresentation(
        ServerPlayerRepresentation player,
        Set<ServerResourceStorageRepresentation> shelves,
        ServerResourceStorageRepresentation infiniteChest,
        ServerResourceStorageRepresentation tempStorage,
        int tempStarResources,
        Set<ServerLeaderCardRepresentation> leaderCardsPlayerOwns,
        List<ServerPlayerOwnedDevelopmentCardDeckRepresentation> developmentCardDecks,
        Set<ServerProductionRepresentation> baseProductions
    ) {
        this.player = player;
        this.shelves = shelves;
        this.infiniteChest = infiniteChest;
        this.tempStorage = tempStorage;
        this.tempStarResources = tempStarResources;
        this.leaderCardsPlayerOwns = leaderCardsPlayerOwns;
        this.developmentCardDecks = developmentCardDecks;
        this.baseProductions = baseProductions;
    }
}
