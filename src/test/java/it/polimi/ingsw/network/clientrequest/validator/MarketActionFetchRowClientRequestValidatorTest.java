package it.polimi.ingsw.network.clientrequest.validator;
import it.polimi.ingsw.network.clientrequest.MarketActionFetchColumnClientRequest;
import it.polimi.ingsw.network.clientrequest.MarketActionFetchRowClientRequest;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.GameContext;
import it.polimi.ingsw.server.model.gamecontext.market.Market;
import it.polimi.ingsw.server.model.gamemanager.GameManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MarketActionFetchRowClientRequestValidatorTest extends MarketActionClientRequestValidatorTest<MarketActionFetchRowClientRequest, MarketActionFetchRowClientRequestValidator>
{

    @Override
    MarketActionFetchRowClientRequest createClientRequestToValidate() {
        return new MarketActionFetchRowClientRequest(player, 1);
    }

    @Override
    Class<MarketActionFetchRowClientRequestValidator> getValidatorType() {
        return MarketActionFetchRowClientRequestValidator.class;
    }

    @Override
    MarketActionFetchRowClientRequest createMarketActionRequest(int num) {
        return new MarketActionFetchRowClientRequest(
            player,
            num
        );
    }
}