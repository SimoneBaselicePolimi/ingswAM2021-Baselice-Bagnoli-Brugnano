package it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation;

import it.polimi.ingsw.client.modelrepresentation.*;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientPlayerOwnedDevelopmentCardDeckRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientResourceStorageRepresentation;

import java.util.List;
import java.util.Set;

public class ClientPlayerContextRepresentation extends ClientRepresentation {
    private final ClientPlayerRepresentation player;
    private Set<ClientResourceStorageRepresentation> shelves;
    private final ClientResourceStorageRepresentation infiniteChest;
    private final ClientResourceStorageRepresentation tempStorage;
    private int tempStarResources;
    private Set<ClientLeaderCardRepresentation> leaderCardsPlayerOwns;
    private List<ClientPlayerOwnedDevelopmentCardDeckRepresentation> developmentCardDecks;
    private final Set<ClientProductionRepresentation> baseProductions;

    public ClientPlayerContextRepresentation(
        ClientPlayerRepresentation player,
        Set<ClientResourceStorageRepresentation> shelves,
        ClientResourceStorageRepresentation infiniteChest,
        ClientResourceStorageRepresentation tempStorage,
        int tempStarResources,
        Set<ClientLeaderCardRepresentation> leaderCardsPlayerOwns,
        List<ClientPlayerOwnedDevelopmentCardDeckRepresentation> developmentCardDecks,
        Set<ClientProductionRepresentation> baseProductions
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

    public ClientPlayerRepresentation getPlayer() {
        return player;
    }

    public Set<ClientResourceStorageRepresentation> getShelves() {
        return shelves;
    }

    public ClientResourceStorageRepresentation getInfiniteChest() {
        return infiniteChest;
    }

    public ClientResourceStorageRepresentation getTempStorage() {
        return tempStorage;
    }

    public int getTempStarResources() {
        return tempStarResources;
    }

    public Set<ClientLeaderCardRepresentation> getLeaderCardsPlayerOwns() {
        return leaderCardsPlayerOwns;
    }

    public List<ClientPlayerOwnedDevelopmentCardDeckRepresentation> getDevelopmentCardDecks() {
        return developmentCardDecks;
    }

    public Set<ClientProductionRepresentation> getBaseProductions() {
        return baseProductions;
    }

    public void setShelves(Set<ClientResourceStorageRepresentation> shelves) {
        this.shelves = shelves;
    }

    public void setTempStarResources(int tempStarResources) {
        this.tempStarResources = tempStarResources;
    }

    public void setLeaderCardsPlayerOwns(Set<ClientLeaderCardRepresentation> leaderCardsPlayerOwns) {
        this.leaderCardsPlayerOwns = leaderCardsPlayerOwns;
    }

    public void setDevelopmentCardDecks(List<ClientPlayerOwnedDevelopmentCardDeckRepresentation> developmentCardDecks) {
        this.developmentCardDecks = developmentCardDecks;
    }
}
