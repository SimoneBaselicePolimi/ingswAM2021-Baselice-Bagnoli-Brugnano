package it.polimi.ingsw.client.clientrequest;

import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.network.clientrequest.validator.ClientRequestValidator;
import it.polimi.ingsw.network.clientrequest.validator.DiscardLeaderCardClientRequestValidator;
import it.polimi.ingsw.server.controller.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardRequirementsNotSatisfiedException;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameState;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsSetOfIds;

import java.util.Map;

public class DiscardLeaderCardClientRequest extends ClientRequest {

    @SerializeAsSetOfIds
    public final ClientLeaderCardRepresentation leaderCardThePlayerWantsToDiscard;

    public DiscardLeaderCardClientRequest(
        Player player,
        ClientLeaderCardRepresentation leaderCardThePlayerWantsToDiscard
    ) {
        super(player);
        this.leaderCardThePlayerWantsToDiscard = leaderCardThePlayerWantsToDiscard;
    }

}
