package it.polimi.ingsw.server.model.observableproxy;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.DevelopmentCardCostDiscount;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.WhiteMarbleSubstitution;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import it.polimi.ingsw.server.model.gameupdate.ServerGameUpdate;
import it.polimi.ingsw.server.model.gameupdate.ServerLeaderCardCanBeActivatedUpdate;
import it.polimi.ingsw.server.model.gameupdate.ServerLeaderCardStateUpdate;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ServerLeaderCardRepresentation;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class LeaderCardObservableProxy extends ObservableProxyForIdentifiableItem<LeaderCard> implements LeaderCard{

    protected boolean firstUpdateDone = false;
    protected boolean wereRequirementsSatisfied;
    protected boolean hasLeaderCardStateChanged = false;

    public LeaderCardObservableProxy(LeaderCard imp, GameManager gameManager) {
        super(imp, gameManager);
    }

    @Override
    public String getItemID() {
        return imp.getItemID();
    }

    @Override
    public boolean areRequirementsSatisfied(PlayerContext playerContext) {
        return imp.areRequirementsSatisfied(playerContext);
    }

    @Override
    public void activateLeaderCard(PlayerContext playerContext) throws LeaderCardRequirementsNotSatisfiedException {
        hasLeaderCardStateChanged = true;
        imp.activateLeaderCard(playerContext);
    }

    @Override
    public void discardLeaderCard() throws LeaderCardRequirementsNotSatisfiedException {
        hasLeaderCardStateChanged = true;
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
    public Set<ServerGameUpdate> getUpdates() {

        Set<ServerGameUpdate> updates = new HashSet<>();

        boolean areRequirementsSatisfied = areRequirementsSatisfiedIfCardLinkedToPlayerContext();
        if(!firstUpdateDone || wereRequirementsSatisfied != areRequirementsSatisfied) {
            wereRequirementsSatisfied = areRequirementsSatisfied;
            updates.add(new ServerLeaderCardCanBeActivatedUpdate(imp, areRequirementsSatisfied));
        }
        firstUpdateDone = true;

        if(hasLeaderCardStateChanged){
            Player playerWhoOwnsLeaderCard = gameManager.getGameContext().getPlayersTurnOrder().stream()
                .map(p -> gameManager.getGameContext().getPlayerContext(p))
                .flatMap(c -> c.getLeaderCards().stream().map(l -> new AbstractMap.SimpleEntry<>(c.getPlayer(), l)))
                .filter(e -> e.getValue().getItemID().equals(imp.getItemID()))
                .map(AbstractMap.SimpleEntry::getKey)
                .findAny()
                .get();
            hasLeaderCardStateChanged = false;
            updates.add(new ServerLeaderCardStateUpdate(playerWhoOwnsLeaderCard, imp, imp.getState()));
        }

        return updates;
    }

    @Override
    public ServerLeaderCardRepresentation getServerRepresentation() {
        ServerLeaderCardRepresentation representationFromImp = imp.getServerRepresentation();
        return new ServerLeaderCardRepresentation(
            representationFromImp.itemID,
            representationFromImp.state,
            representationFromImp.requirements,
            representationFromImp.productions,
            representationFromImp.resourceStorages,
            representationFromImp.cardCostDiscounts,
            representationFromImp.whiteMarbleSubstitutions,
            representationFromImp.victoryPoints,
            areRequirementsSatisfiedIfCardLinkedToPlayerContext()
        );
    }

    @Override
    public ServerLeaderCardRepresentation getServerRepresentationForPlayer(Player player) {
        ServerLeaderCardRepresentation representationFromImp = imp.getServerRepresentationForPlayer(player);
        return new ServerLeaderCardRepresentation(
            representationFromImp.itemID,
            representationFromImp.state,
            representationFromImp.requirements,
            representationFromImp.productions,
            representationFromImp.resourceStorages,
            representationFromImp.cardCostDiscounts,
            representationFromImp.whiteMarbleSubstitutions,
            representationFromImp.victoryPoints,
            areRequirementsSatisfiedIfCardLinkedToPlayerContext()
        );
    }

    protected boolean areRequirementsSatisfiedIfCardLinkedToPlayerContext() {
        Optional<PlayerContext> playerContextAssociatedWithLeaderCard = gameManager.getPlayers().stream()
            .map(p -> gameManager.getGameContext().getPlayerContext(p))
            .filter(playerContext -> playerContext.getLeaderCards().contains(this))
            .findAny();
        return playerContextAssociatedWithLeaderCard
            .filter(playerContext -> imp.areRequirementsSatisfied(playerContext))
            .isPresent();
    }

}
