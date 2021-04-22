package it.polimi.ingsw.network.clientrequest.validator;

import it.polimi.ingsw.network.clientrequest.ClientRequest;
import it.polimi.ingsw.network.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.server.model.gamemanager.GameManager;

import java.util.Optional;

public abstract class ClientRequestValidator<T extends ClientRequest> {

    public abstract Optional<InvalidRequestServerMessage> getErrorMessage(
        T requestToValidate,
        GameManager gameManager
    );

    protected static Optional<InvalidRequestServerMessage> createInvalidRequestServerMessage(
        String errorMessage,
        Object... messageArgs
    ) {
        InvalidRequestServerMessage serverMessage = new InvalidRequestServerMessage(
            String.format(errorMessage, messageArgs)
        );
        return Optional.of(serverMessage);
    }

}
