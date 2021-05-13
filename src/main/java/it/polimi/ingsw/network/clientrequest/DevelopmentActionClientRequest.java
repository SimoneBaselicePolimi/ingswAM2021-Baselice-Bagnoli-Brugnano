package it.polimi.ingsw.network.clientrequest;

import it.polimi.ingsw.network.clientrequest.validator.ClientRequestValidator;
import it.polimi.ingsw.network.clientrequest.validator.DevelopmentActionClientRequestValidator;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.cardstack.ForbiddenPushOnTopException;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gamemanager.gamestate.GameState;
import it.polimi.ingsw.network.servermessage.ServerMessage;

import java.util.Map;

public class DevelopmentActionClientRequest extends ClientRequest {

    public final DevelopmentCard developmentCard;
    public final int deckNumber;

    public DevelopmentActionClientRequest(Player player, DevelopmentCard developmentCard, int deckNumber) {
        super(player);
        this.developmentCard = developmentCard;
        this.deckNumber = deckNumber;
    }

    public Map<Player, ServerMessage> callHandler(GameState state) throws ForbiddenPushOnTopException {
		return(state.handleRequestDevelopmentAction(this));
	}

    @Override
    public ClientRequestValidator getValidator() {
        return new DevelopmentActionClientRequestValidator();
    }

}
