package it.polimi.ingsw.server.controller.clientrequest.validator;
import it.polimi.ingsw.server.controller.clientrequest.MarketActionFetchRowClientRequest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

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