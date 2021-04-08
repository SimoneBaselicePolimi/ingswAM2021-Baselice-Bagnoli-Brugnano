package it.polimi.ingsw.server.model.notifier.gameupdate;

import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfied;
import it.polimi.ingsw.server.model.notifier.Notifier;

import java.util.Optional;

public class LeaderCardNotifier extends LeaderCard implements Notifier<LeaderCardUpdate> {

    public Optional<LeaderCardUpdate> getUpdate() {
        //WARNING: may return sensitive data! When the LeaderCard state is 'hidden' the ID of the card should be kept
        //  secret to every player but the one that owns the card.
        //  Probably, a GameState will not handle this notifier like all the others.

        //... call areRequirementsSatisfied() to get value for LeaderCardUpdate.canBeActivated();

        return Optional.empty();
    }

    @Override
    public void activateLeaderCard() throws LeaderCardRequirementsNotSatisfied {
        super.activateLeaderCard();
    }

    @Override
    public void discardLeaderCard() {
        super.discardLeaderCard();
    }
}
