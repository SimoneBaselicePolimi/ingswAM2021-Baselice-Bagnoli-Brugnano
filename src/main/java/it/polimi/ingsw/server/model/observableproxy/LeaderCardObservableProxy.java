package it.polimi.ingsw.server.model.observableproxy;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.market.Market;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.DevelopmentCardCostDiscount;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.WhiteMarbleSubstitution;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerGameUpdate;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.model.storage.ResourceStorageRuleViolationException;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ServerLeaderCardRepresentation;

import java.util.Set;

public class LeaderCardObservableProxy extends ObservableProxy<LeaderCard> implements LeaderCard{

    public LeaderCardObservableProxy(LeaderCard imp, GameManager gameManager) {
        super(imp, gameManager);
    }

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
        imp.activateLeaderCard(playerContext);
    }

    @Override
    public void discardLeaderCard() throws LeaderCardRequirementsNotSatisfiedException {
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
        return null;
    }

    @Override
    public ServerLeaderCardRepresentation getServerRepresentation() {
        return getServerRepresentation();
    }

    @Override
    public ServerLeaderCardRepresentation getServerRepresentationForPlayer(Player player) {
        return getServerRepresentationForPlayer(player);
    }
}
