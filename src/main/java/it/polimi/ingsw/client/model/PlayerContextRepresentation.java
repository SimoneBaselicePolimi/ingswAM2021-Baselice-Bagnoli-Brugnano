package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.cardstack.PlayerOwnedDevelopmentCardDeck;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;

import java.util.List;
import java.util.Set;

public class PlayerContextRepresentation extends Representation{
    private final PlayerRepresentation player;
    private Set<ResourceStorageRepresentation> shelves;
    private final ResourceStorageRepresentation infiniteChest;
    private final ResourceStorageRepresentation tempStorage;
    private int tempStarResources;
    private Set<LeaderCardRepresentation> leaderCardsPlayerOwns;
    private List<PlayerOwnedDevelopmentCardDeckRepresentation> developmentCardDecks;
    private final Set<ProductionRepresentation> baseProductions;

    public PlayerContextRepresentation(
        PlayerRepresentation player,
        Set<ResourceStorageRepresentation> shelves,
        ResourceStorageRepresentation infiniteChest,
        ResourceStorageRepresentation tempStorage,
        int tempStarResources,
        Set<LeaderCardRepresentation> leaderCardsPlayerOwns,
        List<PlayerOwnedDevelopmentCardDeckRepresentation> developmentCardDecks,
        Set<ProductionRepresentation> baseProductions
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

    public PlayerRepresentation getPlayer() {
        return player;
    }

    public Set<ResourceStorageRepresentation> getShelves() {
        return shelves;
    }

    public ResourceStorageRepresentation getInfiniteChest() {
        return infiniteChest;
    }

    public ResourceStorageRepresentation getTempStorage() {
        return tempStorage;
    }

    public int getTempStarResources() {
        return tempStarResources;
    }

    public Set<LeaderCardRepresentation> getLeaderCardsPlayerOwns() {
        return leaderCardsPlayerOwns;
    }

    public List<PlayerOwnedDevelopmentCardDeckRepresentation> getDevelopmentCardDecks() {
        return developmentCardDecks;
    }

    public Set<ProductionRepresentation> getBaseProductions() {
        return baseProductions;
    }

    public void setShelves(Set<ResourceStorageRepresentation> shelves) {
        this.shelves = shelves;
    }

    public void setTempStarResources(int tempStarResources) {
        this.tempStarResources = tempStarResources;
    }

    public void setLeaderCardsPlayerOwns(Set<LeaderCardRepresentation> leaderCardsPlayerOwns) {
        this.leaderCardsPlayerOwns = leaderCardsPlayerOwns;
    }

    public void setDevelopmentCardDecks(List<PlayerOwnedDevelopmentCardDeckRepresentation> developmentCardDecks) {
        this.developmentCardDecks = developmentCardDecks;
    }
}
