package it.polimi.ingsw.network.clientrequest;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameState;
import it.polimi.ingsw.network.servermessage.ServerMessage;
import it.polimi.ingsw.utils.serialization.SerializeAsSetOfIds;

import java.util.Map;
import java.util.Set;

public class LeaderActionClientRequest extends ClientRequest {
    @SerializeAsSetOfIds
    public final Set<LeaderCard> leaderCardsThePlayerWantsToDiscard;

    @SerializeAsSetOfIds
    public final Set<LeaderCard> leaderCardsThePlayerWantsToActivate;


    public LeaderActionClientRequest(
        Player player,
        Set<LeaderCard> leaderCardsThePlayerWantsToDiscard,
        Set<LeaderCard> leaderCardsThePlayerWantsToActivate
    ) {
        super(player);
        this.leaderCardsThePlayerWantsToDiscard = leaderCardsThePlayerWantsToDiscard;
        this.leaderCardsThePlayerWantsToActivate = leaderCardsThePlayerWantsToActivate;
    }

    public Map<Player, ServerMessage> callHandler(GameState state) throws LeaderCardRequirementsNotSatisfiedException {
		return(state.handleRequestLeaderAction(this));
	}

}
