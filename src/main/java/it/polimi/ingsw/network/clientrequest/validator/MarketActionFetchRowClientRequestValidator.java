package it.polimi.ingsw.network.clientrequest.validator;

import it.polimi.ingsw.network.clientrequest.MarketActionFetchRowClientRequest;
import it.polimi.ingsw.network.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.server.model.gamemanager.GameManager;

import java.util.Optional;

public class MarketActionFetchRowClientRequestValidator extends ClientRequestValidator <MarketActionFetchRowClientRequest>{

    @Override
    public Optional<InvalidRequestServerMessage> getErrorMessage(
        MarketActionFetchRowClientRequest requestToValidate,
        GameManager gameManager
    ) {

        //if(requestToValidate.row > gameManager.getGameContext().getMarket().getNumOfRows() )
        return Optional.empty();
    }
}
