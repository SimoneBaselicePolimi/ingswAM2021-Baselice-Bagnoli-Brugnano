package it.polimi.ingsw.server.controller.clientrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.controller.clientrequest.validator.ClientRequestValidator;
import it.polimi.ingsw.server.controller.clientrequest.validator.DiscardLeaderCardClientRequestValidator;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameState;
import it.polimi.ingsw.server.controller.servermessage.ServerMessage;
import it.polimi.ingsw.utils.serialization.annotations.*;

import java.util.Map;

public class DiscardLeaderCardClientRequest extends ClientRequest {

    @SerializeIdOnly
    public final LeaderCard leaderCardThePlayerWantsToDiscard;

    public DiscardLeaderCardClientRequest(
        @JsonProperty("player") Player player,
        @JsonProperty("leaderCardThePlayerWantsToDiscard") LeaderCard leaderCardThePlayerWantsToDiscard
    ) {
        super(player);
        this.leaderCardThePlayerWantsToDiscard = leaderCardThePlayerWantsToDiscard;
    }

    public Map<Player, ServerMessage> callHandler(GameState state) throws LeaderCardRequirementsNotSatisfiedException {
		return(state.handleRequestDiscardLeaderAction(this));
	}

    @Override
    public ClientRequestValidator getValidator() {
        return new DiscardLeaderCardClientRequestValidator();
    }

}
