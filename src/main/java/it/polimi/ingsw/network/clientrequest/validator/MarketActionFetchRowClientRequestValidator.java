package it.polimi.ingsw.network.clientrequest.validator;

import it.polimi.ingsw.network.clientrequest.MarketActionFetchRowClientRequest;
import it.polimi.ingsw.network.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.server.model.gamecontext.market.Market;
import it.polimi.ingsw.server.model.gamemanager.GameManager;

import java.util.Optional;

public class MarketActionFetchRowClientRequestValidator extends ClientRequestValidator <MarketActionFetchRowClientRequest>{

    @Override
    public Optional<InvalidRequestServerMessage> getErrorMessage(
        MarketActionFetchRowClientRequest requestToValidate,
        GameManager gameManager
    ) {

        Market market = gameManager.getGameContext().getMarket();
        if (requestToValidate.row < 0 || requestToValidate.row >= market.getNumOfRows())
            return createInvalidRequestServerMessage(
                "The row indicated by the player is not a valid row of the market matrix"
            );

        return Optional.empty();
    }
}
