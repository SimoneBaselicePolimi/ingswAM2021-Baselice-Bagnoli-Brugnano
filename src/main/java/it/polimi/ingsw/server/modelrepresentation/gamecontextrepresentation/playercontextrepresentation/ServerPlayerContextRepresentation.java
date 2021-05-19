package it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.playercontextrepresentation;

import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;
import it.polimi.ingsw.server.modelrepresentation.ServerRepresentation;
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
    private int numberOfLeaderCardsThePlayerOwns;


    public ServerPlayerContextRepresentation(
        ServerPlayerRepresentation player,
        Set<ServerResourceStorageRepresentation> shelves,
        ServerResourceStorageRepresentation infiniteChest,
        ServerResourceStorageRepresentation tempStorage,
        int tempStarResources,
        Set<ServerLeaderCardRepresentation> leaderCardsPlayerOwns,
        List<ServerPlayerOwnedDevelopmentCardDeckRepresentation> developmentCardDecks,
        Set<ServerProductionRepresentation> baseProductions,
        int numberOfLeaderCardsThePlayerOwns
    ) {
        this.player = player;
        this.shelves = shelves;
        this.infiniteChest = infiniteChest;
        this.tempStorage = tempStorage;
        this.tempStarResources = tempStarResources;
        this.leaderCardsPlayerOwns = leaderCardsPlayerOwns;
        this.developmentCardDecks = developmentCardDecks;
        this.baseProductions = baseProductions;
        this.numberOfLeaderCardsThePlayerOwns = numberOfLeaderCardsThePlayerOwns;
    }
}
