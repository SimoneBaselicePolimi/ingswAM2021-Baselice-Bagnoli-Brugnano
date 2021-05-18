package it.polimi.ingsw.network.clientrequest.validator;
import it.polimi.ingsw.network.clientrequest.MarketActionFetchColumnClientRequest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)

class MarketActionFetchColumnClientRequestValidatorTest extends MarketActionClientRequestValidatorTest<MarketActionFetchColumnClientRequest, MarketActionFetchColumnClientRequestValidator>{

    @Override
    MarketActionFetchColumnClientRequest createClientRequestToValidate() {
        return new MarketActionFetchColumnClientRequest(player, 1);
    }

    @Override
    Class<MarketActionFetchColumnClientRequestValidator> getValidatorType() {
        return MarketActionFetchColumnClientRequestValidator.class;
    }

    @Override
    MarketActionFetchColumnClientRequest createMarketActionRequest(int num) {
        return new MarketActionFetchColumnClientRequest(
            player,
            num
        );
    }
}