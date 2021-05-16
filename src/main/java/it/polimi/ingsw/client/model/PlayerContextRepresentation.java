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
    private Set<LeaderCard> leaderCardsPlayerOwns;
    private List<PlayerOwnedDevelopmentCardDeck> developmentCardDecks;
    private final Set<Production> baseProductions;

    public PlayerContextRepresentation(PlayerRepresentation player, Set<ResourceStorageRepresentation> shelves, ResourceStorageRepresentation infiniteChest, ResourceStorageRepresentation tempStorage, int tempStarResources, Set<LeaderCard> leaderCardsPlayerOwns, List<PlayerOwnedDevelopmentCardDeck> developmentCardDecks, Set<Production> baseProductions) {
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

    public Set<LeaderCard> getLeaderCardsPlayerOwns() {
        return leaderCardsPlayerOwns;
    }

    public List<PlayerOwnedDevelopmentCardDeck> getDevelopmentCardDecks() {
        return developmentCardDecks;
    }

    public Set<Production> getBaseProductions() {
        return baseProductions;
    }

    public void setShelves(Set<ResourceStorageRepresentation> shelves) {
        this.shelves = shelves;
    }

    public void setTempStarResources(int tempStarResources) {
        this.tempStarResources = tempStarResources;
    }

    public void setLeaderCardsPlayerOwns(Set<LeaderCard> leaderCardsPlayerOwns) {
        this.leaderCardsPlayerOwns = leaderCardsPlayerOwns;
    }

    public void setDevelopmentCardDecks(List<PlayerOwnedDevelopmentCardDeck> developmentCardDecks) {
        this.developmentCardDecks = developmentCardDecks;
    }
}
