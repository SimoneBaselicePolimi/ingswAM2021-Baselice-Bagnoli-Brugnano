package it.polimi.ingsw.server.controller.clientrequest.validator;

import it.polimi.ingsw.server.controller.clientrequest.MarketActionFetchColumnClientRequest;
import it.polimi.ingsw.server.controller.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.server.model.gamecontext.market.Market;
import it.polimi.ingsw.server.model.gamemanager.GameManager;

import java.util.Optional;

public class MarketActionFetchColumnClientRequestValidator extends ClientRequestValidator <MarketActionFetchColumnClientRequest>{

    @Override
    public Optional<InvalidRequestServerMessage> getErrorMessage(
        MarketActionFetchColumnClientRequest requestToValidate,
        GameManager gameManager
    ) {

        Market market = gameManager.getGameContext().getMarket();
        if (requestToValidate.column < 0 || requestToValidate.column >= market.getNumOfColumns())
            return createInvalidRequestServerMessage(
                "The column indicated by the player is not a valid column of the market matrix"
            );

        return Optional.empty();
    }
}
