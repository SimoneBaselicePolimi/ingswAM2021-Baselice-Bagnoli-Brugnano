package it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ClientPlayerOwnedDevelopmentCardDeckRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.client.modelrepresentation.storagerepresentation.ClientResourceStorageRepresentation;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClientPlayerContextRepresentation extends ClientRepresentation {
    private final Player player;
    private Set<ClientResourceStorageRepresentation> shelves;
    private final ClientResourceStorageRepresentation infiniteChest;
    private final ClientResourceStorageRepresentation tempStorage;
    private int tempStarResources;
    private Set<ClientLeaderCardRepresentation> leaderCardsPlayerOwns;
    private List<ClientPlayerOwnedDevelopmentCardDeckRepresentation> developmentCardDecks;
    private final Set<ClientProductionRepresentation> baseProductions;
    private int numberOfLeaderCardsThePlayerOwns;
    private Map<ResourceType, Integer> totalResourcesOwnedByThePlayer = new HashMap<>();

    public ClientPlayerContextRepresentation(
        @JsonProperty("player") Player player,
        @JsonProperty("shelves") Set<ClientResourceStorageRepresentation> shelves,
        @JsonProperty("infiniteChest") ClientResourceStorageRepresentation infiniteChest,
        @JsonProperty("tempStorage") ClientResourceStorageRepresentation tempStorage,
        @JsonProperty("tempStarResources") int tempStarResources,
        @JsonProperty("leaderCardsPlayerOwns") Set<ClientLeaderCardRepresentation> leaderCardsPlayerOwns,
        @JsonProperty("developmentCardDecks") List<ClientPlayerOwnedDevelopmentCardDeckRepresentation> developmentCardDecks,
        @JsonProperty("baseProductions") Set<ClientProductionRepresentation> baseProductions,
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

    public Player getPlayer() {
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

    public int getNumberOfLeaderCardsThePlayerOwns() {
        return numberOfLeaderCardsThePlayerOwns;
    }

    public void setShelves(Set<ClientResourceStorageRepresentation> shelves) {
        this.shelves = shelves;
        notifyViews();
    }

    public void setTempStarResources(int tempStarResources) {
        this.tempStarResources = tempStarResources;
        notifyViews();
    }

    public void setLeaderCardsPlayerOwns(Set<ClientLeaderCardRepresentation> leaderCardsPlayerOwns) {
        this.leaderCardsPlayerOwns = leaderCardsPlayerOwns;
        notifyViews();
    }

    public void setDevelopmentCardDecks(List<ClientPlayerOwnedDevelopmentCardDeckRepresentation> developmentCardDecks) {
        this.developmentCardDecks = developmentCardDecks;
        notifyViews();
    }

    public void setNumberOfLeaderCardsThePlayerOwns(int numberOfLeaderCardsThePlayerOwns) {
        this.numberOfLeaderCardsThePlayerOwns = numberOfLeaderCardsThePlayerOwns;
        notifyViews();
    }

    public Set<ClientLeaderCardRepresentation> getActiveLeaderCards() {
        return leaderCardsPlayerOwns.stream()
            .filter(leaderCard -> leaderCard.getState() == LeaderCardState.ACTIVE)
            .collect(Collectors.toSet());
    }

    public Set<ClientProductionRepresentation> getActiveLeaderCardsProductions() {
        return getActiveLeaderCards().stream()
            .flatMap(leaderCard -> leaderCard.getProductions().stream())
            .collect(Collectors.toSet());
    }

    public Set<ClientDevelopmentCardRepresentation> getDevelopmentCardsOnTop() {
        return developmentCardDecks.stream().map(ClientPlayerOwnedDevelopmentCardDeckRepresentation::peek).collect(Collectors.toSet());
    }

    public Set<ClientProductionRepresentation> getActiveDevelopmentCardsProductions(){
        return getDevelopmentCardsOnTop().stream()
            .map(ClientDevelopmentCardRepresentation::getProduction)
            .collect(Collectors.toSet());
    }

    public Set<ClientProductionRepresentation> getActiveProductions() {
        return Stream.concat(Stream.concat(
            baseProductions.stream(),
            getActiveLeaderCardsProductions().stream()),
            getActiveDevelopmentCardsProductions().stream()
        ).collect(Collectors.toSet());
    }

    public Map<ResourceType, Integer> getTotalResourcesOwnedByThePlayer() {
        return totalResourcesOwnedByThePlayer;
    }

    public void setTotalResourcesOwnedByThePlayer(Map<ResourceType, Integer> totalResourcesOwnedByThePlayer) {
        this.totalResourcesOwnedByThePlayer = totalResourcesOwnedByThePlayer;
        notifyViews();
    }

}
