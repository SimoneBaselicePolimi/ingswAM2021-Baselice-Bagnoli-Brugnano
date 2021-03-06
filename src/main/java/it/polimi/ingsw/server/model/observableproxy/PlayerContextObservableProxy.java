package it.polimi.ingsw.server.model.observableproxy;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.cardstack.ForbiddenPushOnTopException;
import it.polimi.ingsw.server.model.gameitems.cardstack.PlayerOwnedDevelopmentCardDeck;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.gameupdate.*;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ServerPlayerContextRepresentation;

import java.util.*;

public class PlayerContextObservableProxy extends ObservableProxy<PlayerContext> implements PlayerContext {

    protected boolean haveLeaderCardsChanged = false;
    protected boolean haveDevCardsChanged = false;
    protected boolean haveTempStarResourcesChanged = false;

    long numberOfActiveLeaderCards = 0;

    protected Map<ResourceType, Integer> totalResources = new HashMap<>();

    public PlayerContextObservableProxy(PlayerContext imp, GameManager gameManager) {
        super(imp, gameManager);
    }

    @Override
    public ServerPlayerContextRepresentation getServerRepresentation() {
        return imp.getServerRepresentation();
    }

    @Override
    public ServerPlayerContextRepresentation getServerRepresentationForPlayer(Player player) {
        return imp.getServerRepresentationForPlayer(player);
    }

    @Override
    public void setLeaderCards(Set<LeaderCard> cards) {
        haveLeaderCardsChanged = true;
        imp.setLeaderCards(cards);
    }

    @Override
    public Player getPlayer() {
        return imp.getPlayer();
    }

    @Override
    public Set<LeaderCard> getLeaderCards() {
        return imp.getLeaderCards();
    }

    @Override
    public Set<LeaderCard> getActiveLeaderCards() {
        return imp.getActiveLeaderCards();
    }

    @Override
    public Map<ResourceType, Integer> getActiveLeaderCardsDiscounts() {
        return imp.getActiveLeaderCardsDiscounts();
    }

    @Override
    public Set<ResourceType> getActiveLeaderCardsWhiteMarblesMarketSubstitutions() {
        return imp.getActiveLeaderCardsWhiteMarblesMarketSubstitutions();
    }

    @Override
    public Set<ResourceStorage> getActiveLeaderCardsResourceStorages() {
        return imp.getActiveLeaderCardsResourceStorages();
    }

    @Override
    public Set<Production> getActiveLeaderCardsProductions() {
        return imp.getActiveLeaderCardsProductions();
    }

    @Override
    public Set<Production> getActiveDevelopmentCardsProductions() {
        return imp.getActiveDevelopmentCardsProductions();
    }

    @Override
    public Set<Production> getActiveProductions() {
        return imp.getActiveProductions();
    }

    @Override
    public Set<ResourceStorage> getShelves() {
        return imp.getShelves();
    }

    @Override
    public Set<ResourceStorage> getResourceStoragesForResourcesFromMarket() {
        return imp.getResourceStoragesForResourcesFromMarket();
    }

    @Override
    public ResourceStorage getInfiniteChest() {
        return imp.getInfiniteChest();
    }

    @Override
    public ResourceStorage getTemporaryStorage() {
        return imp.getTemporaryStorage();
    }

    @Override
    public void setTemporaryStorageResources(Map<ResourceType, Integer> resources) throws ResourceStorageRuleViolationException, NotEnoughResourcesException {
        haveTempStarResourcesChanged = true;
        imp.setTemporaryStorageResources(resources);
    }

    @Override
    public Map<ResourceType, Integer> getTemporaryStorageResources() {
        return imp.getTemporaryStorageResources();
    }

    @Override
    public Map<ResourceType, Integer> clearTemporaryStorageResources() throws NotEnoughResourcesException {
        return imp.clearTemporaryStorageResources();
    }

    @Override
    public int getTempStarResources() {
        return imp.getTempStarResources();
    }

    @Override
    public void setTempStarResources(int tempStarResources) {
        imp.setTempStarResources(tempStarResources);
    }

    @Override
    public Set<ResourceStorage> getAllResourceStorages() {
        return imp.getAllResourceStorages();
    }

    @Override
    public Map<ResourceType, Integer> getAllResources() {
        return imp.getAllResources();
    }

    @Override
    public void removeResourcesBasedOnResourcesStoragesPriority(Map<ResourceType, Integer> resourcesToRemove) throws NotEnoughResourcesException {
        imp.removeResourcesBasedOnResourcesStoragesPriority(resourcesToRemove);
    }

    @Override
    public PlayerOwnedDevelopmentCardDeck getDeck(int deckNumber) throws IllegalArgumentException {
        return imp.getDeck(deckNumber);
    }

    @Override
    public void addDevelopmentCard(DevelopmentCard card, int deckNumber) throws IllegalArgumentException, ForbiddenPushOnTopException {
        haveDevCardsChanged = true;
        imp.addDevelopmentCard(card, deckNumber);
    }

    @Override
    public boolean canAddDevelopmentCard(DevelopmentCard card, int deckNumber) throws IllegalArgumentException {
        return imp.canAddDevelopmentCard(card, deckNumber);
    }

    @Override
    public Set<DevelopmentCard> getAllDevelopmentCards() {
        return imp.getAllDevelopmentCards();
    }

    @Override
    public Set<DevelopmentCard> getDevelopmentCardsOnTop() {
        return imp.getDevelopmentCardsOnTop();
    }

    @Override
    public List<PlayerOwnedDevelopmentCardDeck> getPlayerDevCardsDecks() {
        return imp.getPlayerDevCardsDecks();
    }

    @Override
    public Set<ServerGameUpdate> getUpdates() {

        Set<ServerGameUpdate> updates = new HashSet<>();

        if (haveTempStarResourcesChanged) {
            haveTempStarResourcesChanged = false;
            updates.add(new ServerTempStarResourcesUpdate(getPlayer(), imp.getTempStarResources()));
        }

        if (haveLeaderCardsChanged) {
            haveLeaderCardsChanged = false;
            updates.add(new ServerLeaderCardsThePlayerOwnsUpdate(
                imp.getPlayer(),
                imp.getLeaderCards()
            ));
        }

        boolean haveTotalResourcesChanged = !totalResources.equals(imp.getAllResources());
        if(haveTotalResourcesChanged) {
            totalResources = new HashMap<>(imp.getAllResources());
            updates.add(new ServerTotalResourcesUpdate(imp.getPlayer(), totalResources));
        }

        boolean hasLeaderCardsStatusChanged = false;
        if(
            numberOfActiveLeaderCards != imp.getLeaderCards().stream()
                .filter(l -> l.getState().equals(LeaderCardState.ACTIVE))
                .count()
        ) {
            hasLeaderCardsStatusChanged = true;
            numberOfActiveLeaderCards = imp.getLeaderCards().stream()
                .filter(l -> l.getState().equals(LeaderCardState.ACTIVE))
                .count();
        }

        if(haveTotalResourcesChanged || haveDevCardsChanged || hasLeaderCardsStatusChanged) {
            updates.add(
                new ServerPurchasableDevelopmentCardsUpdate(
                    imp.getPlayer(),
                    gameManager.getGameContext().getPurchasableDevelopmentCardsForPlayer(getPlayer())
                )
            );
        }
        haveDevCardsChanged = false;

        return updates;
    }

}
