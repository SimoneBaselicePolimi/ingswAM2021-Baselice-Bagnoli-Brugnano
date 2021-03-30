package it.polimi.ingsw.server.model.notifier.gameupdate;

import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.DevelopmentCardCostDiscount;
import it.polimi.ingsw.server.model.gameitems.WhiteMarbleSubstitution;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirement;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfied;
import it.polimi.ingsw.server.model.notifier.Notifier;
import it.polimi.ingsw.server.model.storage.ResourceStorage;

import java.util.List;
import java.util.Optional;

public class LeaderCardNotifier extends LeaderCard implements Notifier<LeaderCardUpdate> {

    public LeaderCardNotifier(LeaderCardRequirement requirement, List<Production> production, List<ResourceStorage> resourceStorage, List<DevelopmentCardCostDiscount> developmentCardCostDiscounts, List<WhiteMarbleSubstitution> whiteMarbleSubstitutions, int victoryPoints) {
        super(requirement, production, resourceStorage, developmentCardCostDiscounts, whiteMarbleSubstitutions, victoryPoints);
    }

    public Optional<LeaderCardUpdate> getUpdate() {
        //WARNING: may return sensitive data! When the LeaderCard state is 'hidden' the ID of the card should be kept
        //  secret to every player but the one that owns the card.
        //  Probably, a GameState will not handle this notifier like all the others.

        //... call areRequirementsSatisfied() to get value for LeaderCardUpdate.canBeActivated();

        return Optional.empty();
    }

    @Override
    public void activateLeaderCard(PlayerContext playerContext) throws LeaderCardRequirementsNotSatisfied {
        super.activateLeaderCard(playerContext);
    }

    @Override
    public void discardLeaderCard() {
        super.discardLeaderCard();
    }
}
