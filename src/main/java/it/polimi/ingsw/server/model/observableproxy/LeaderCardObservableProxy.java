package it.polimi.ingsw.server.model.observableproxy;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.market.Market;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.DevelopmentCardCostDiscount;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.WhiteMarbleSubstitution;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.notifier.gameupdate.*;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ServerLeaderCardRepresentation;

import java.util.*;

public class LeaderCardObservableProxy extends ObservableProxy<LeaderCard> implements LeaderCard{

    public LeaderCardObservableProxy(LeaderCard imp, GameManager gameManager) {
        super(imp, gameManager);
    }

    protected boolean hasThePlayerActivateLeaderCard = false;
    protected boolean hasThePlayerDiscardLeaderCard = false;

    @Override
    public String getItemId() {
        return imp.getItemId();
    }

    @Override
    public boolean areRequirementsSatisfied(PlayerContext playerContext) {
        return imp.areRequirementsSatisfied(playerContext);
    }

    @Override
    public void activateLeaderCard(PlayerContext playerContext) throws LeaderCardRequirementsNotSatisfiedException {
        hasThePlayerActivateLeaderCard = true;
        imp.activateLeaderCard(playerContext);
    }

    @Override
    public void discardLeaderCard() throws LeaderCardRequirementsNotSatisfiedException {
        hasThePlayerDiscardLeaderCard = true;
        imp.discardLeaderCard();
    }

    @Override
    public LeaderCardState getState() {
        return imp.getState();
    }

    @Override
    public Set<Production> getProductions() {
        return imp.getProductions();
    }

    @Override
    public Set<ResourceStorage> getResourceStorages() {
        return imp.getResourceStorages();
    }

    @Override
    public Set<DevelopmentCardCostDiscount> getDevelopmentCardCostDiscount() {
        return imp.getDevelopmentCardCostDiscount();
    }

    @Override
    public Set<WhiteMarbleSubstitution> getWhiteMarbleSubstitutions() {
        return imp.getWhiteMarbleSubstitutions();
    }

    @Override
    public int getVictoryPoints() {
        return imp.getVictoryPoints();
    }

    @Override
    public Set<ServerGameUpdate> getUpdates() throws ResourceStorageRuleViolationException, NotEnoughResourcesException {
        Set<ServerGameUpdate> updates = new HashSet<>();

        if(hasThePlayerActivateLeaderCard) {
            hasThePlayerActivateLeaderCard = false;

            Optional<PlayerContext> playerContextAssociatedWithLeaderCard = gameManager.getPlayers().stream()
                .map(p -> gameManager.getGameContext().getPlayerContext(p))
                .filter(playerContext -> playerContext.getLeaderCards().contains(this))
                .findAny();
            boolean areRequirementsSatisfied;
            if (playerContextAssociatedWithLeaderCard.isPresent())
                areRequirementsSatisfied = imp.areRequirementsSatisfied(playerContextAssociatedWithLeaderCard.get());
            else
                areRequirementsSatisfied = false;

            updates.add(new ServerLeaderCardCanBeActivatedUpdate(imp, areRequirementsSatisfied));
            updates.add(new ServerLeaderCardStateUpdate(imp, imp.getState()));
        }

        if(hasThePlayerDiscardLeaderCard){
            hasThePlayerDiscardLeaderCard = false;

            updates.add(new ServerLeaderCardStateUpdate(imp, imp.getState()));
            //TODO Mi serve il player
            updates.add(new ServerLeaderCardsThePlayerOwnsUpdate())
        }
        return updates;
    }

    @Override
    public ServerLeaderCardRepresentation getServerRepresentation() {
        return getServerRepresentation();
    }

    @Override
    public ServerLeaderCardRepresentation getServerRepresentationForPlayer(Player player) {
        return imp.getServerRepresentationForPlayer(player);
    }
}
