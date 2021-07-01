package it.polimi.ingsw.server.controller.clientrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.controller.clientrequest.validator.ClientRequestValidator;
import it.polimi.ingsw.server.controller.clientrequest.validator.DevelopmentActionClientRequestValidator;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.cardstack.ForbiddenPushOnTopException;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameState;
import it.polimi.ingsw.server.controller.servermessage.ServerMessage;
import it.polimi.ingsw.server.model.storage.NotEnoughResourcesException;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.Map;

public class DevelopmentActionClientRequest extends ClientRequest {

    @SerializeIdOnly
    public final DevelopmentCard developmentCard;

    public final int deckNumber;

    public DevelopmentActionClientRequest(
        @JsonProperty("player") Player player,
        @JsonProperty("developmentCard") DevelopmentCard developmentCard,
        @JsonProperty("deckNumber") int deckNumber
    ) {
        super(player);
        this.developmentCard = developmentCard;
        this.deckNumber = deckNumber;
    }

    public Map<Player, ServerMessage> callHandler(GameState state) throws ForbiddenPushOnTopException, NotEnoughResourcesException {
		return(state.handleRequestDevelopmentAction(this));
	}

    @Override
    public ClientRequestValidator getValidator() {
        return new DevelopmentActionClientRequestValidator();
    }

}
