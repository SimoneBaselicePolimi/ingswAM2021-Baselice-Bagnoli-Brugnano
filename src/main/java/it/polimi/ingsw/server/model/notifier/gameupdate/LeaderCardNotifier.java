package it.polimi.ingsw.server.model.notifier.gameupdate;

import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.DevelopmentCardCostDiscount;
import it.polimi.ingsw.server.model.gameitems.WhiteMarbleSubstitution;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirement;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;
import it.polimi.ingsw.server.model.notifier.Notifier;
import it.polimi.ingsw.server.model.storage.ResourceStorage;

import java.util.List;
import java.util.Optional;

public class LeaderCardNotifier extends LeaderCard implements Notifier<LeaderCardUpdate> {

    public LeaderCardNotifier(List<LeaderCardRequirement> requirements, List<Production> productions, List<ResourceStorage> resourceStorages, List<DevelopmentCardCostDiscount> developmentCardCostDiscounts, List<WhiteMarbleSubstitution> whiteMarbleSubstitutions, int victoryPoints) {
        super(requirements, productions, resourceStorages, developmentCardCostDiscounts, whiteMarbleSubstitutions, victoryPoints);
    }

    public Optional<LeaderCardUpdate> getUpdate() {
        //WARNING: may return sensitive data! When the LeaderCard state is 'hidden' the ID of the card should be kept
        //  secret to every player but the one that owns the card.
        //  Probably, a GameState will not handle this notifier like all the others.

        //... call areRequirementsSatisfied() to get value for LeaderCardUpdate.canBeActivated();

        return Optional.empty();
    }

    @Override
    public void activateLeaderCard(PlayerContext playerContext) throws LeaderCardRequirementsNotSatisfiedException {
        super.activateLeaderCard(playerContext);
    }

    @Override
    public void discardLeaderCard () throws LeaderCardRequirementsNotSatisfiedException{
        super.discardLeaderCard();
    }
}
