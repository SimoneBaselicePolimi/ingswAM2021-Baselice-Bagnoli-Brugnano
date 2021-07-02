package it.polimi.ingsw.server.controller.clientrequest.validator;

import it.polimi.ingsw.server.controller.clientrequest.EndTurnClientRequest;
import it.polimi.ingsw.server.controller.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.server.model.gamemanager.GameManager;

import java.util.Optional;

public class EndTurnClientRequestValidator extends ClientRequestValidator<EndTurnClientRequest>{

    @Override
    public Optional<InvalidRequestServerMessage> getErrorMessage(
        EndTurnClientRequest requestToValidate,
        GameManager gameManager
    ) {
        return Optional.empty();
    }
}
