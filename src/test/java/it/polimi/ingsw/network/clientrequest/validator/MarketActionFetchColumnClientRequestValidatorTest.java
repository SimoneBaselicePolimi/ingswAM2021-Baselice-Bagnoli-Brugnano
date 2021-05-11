package it.polimi.ingsw.network.clientrequest.validator;
import it.polimi.ingsw.network.clientrequest.DiscardLeaderCardClientRequest;
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
import static org.mockito.Mockito.when;

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