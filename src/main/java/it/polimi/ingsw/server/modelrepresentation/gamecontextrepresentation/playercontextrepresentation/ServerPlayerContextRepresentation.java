package it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.playercontextrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    public final int numberOfLeaderCardsThePlayerOwns;

    public ServerPlayerContextRepresentation(
        @JsonProperty("player") ServerPlayerRepresentation player,
        @JsonProperty("shelves") Set<ServerResourceStorageRepresentation> shelves,
        @JsonProperty("infiniteChest") ServerResourceStorageRepresentation infiniteChest,
        @JsonProperty("tempStorage") ServerResourceStorageRepresentation tempStorage,
        @JsonProperty("tempStarResources") int tempStarResources,
        @JsonProperty("leaderCardsPlayerOwns") Set<ServerLeaderCardRepresentation> leaderCardsPlayerOwns,
        @JsonProperty("developmentCardDecks") List<ServerPlayerOwnedDevelopmentCardDeckRepresentation> developmentCardDecks,
        @JsonProperty("baseProductions") Set<ServerProductionRepresentation> baseProductions,
        @JsonProperty("numberOfLeaderCardsThePlayerOwns") int numberOfLeaderCardsThePlayerOwns
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
