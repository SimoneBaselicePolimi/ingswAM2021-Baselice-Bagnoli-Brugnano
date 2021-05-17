package it.polimi.ingsw.server.model.notifier;

import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.DevelopmentCardCostDiscount;
import it.polimi.ingsw.server.model.gameitems.WhiteMarbleSubstitution;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirement;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;
import it.polimi.ingsw.server.model.notifier.gameupdate.GameUpdate;
import it.polimi.ingsw.server.model.storage.ResourceStorage;

import java.util.List;
import java.util.Set;

public class LeaderCardNotifier extends LeaderCard implements Notifier {

    public LeaderCardNotifier(
        String leaderCardID,
        GameItemsManager gameItemsManager,
        Set<LeaderCardRequirement> requirements,
        Set<Production> productions,
        Set<ResourceStorage> resourceStorages,
        Set<DevelopmentCardCostDiscount> cardCostDiscounts,
        Set<WhiteMarbleSubstitution> whiteMarbleSubstitutions,
        int victoryPoints
    ) {
        super(leaderCardID, gameItemsManager, requirements, productions, resourceStorages, cardCostDiscounts, whiteMarbleSubstitutions, victoryPoints);
    }

    public Set<GameUpdate> getUpdates() {
        //WARNING: may return sensitive data! When the LeaderCard state is 'hidden' the ID of the card should be kept
        //  secret to every player but the one that owns the card.
        //  Probably, a GameState will not handle this notifier like all the others.

        //... call areRequirementsSatisfied() to get value for LeaderCardUpdate.canBeActivated();

        return null;
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
