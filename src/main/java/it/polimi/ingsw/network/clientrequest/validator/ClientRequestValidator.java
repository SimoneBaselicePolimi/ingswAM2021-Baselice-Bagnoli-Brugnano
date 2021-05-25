package it.polimi.ingsw.network.clientrequest.validator;

import it.polimi.ingsw.network.clientrequest.ClientRequest;
import it.polimi.ingsw.server.controller.servermessage.InvalidRequestServerMessage;
import it.polimi.ingsw.server.model.gamemanager.GameManager;

import java.util.Optional;

/**
 * Validator classes are used to verify that requests sent by clients are requests
 * that comply with the rules of the game
 */
public abstract class ClientRequestValidator<T extends ClientRequest> {

    /**
     * Method that sends an error message if the player's request is not a valid request
     * @param requestToValidate specific request sent by the client
     * @param gameManager GameManager, see {@link GameManager}
     * @return Optional<InvalidRequestServerMessage>, see {@link InvalidRequestServerMessage}
     */
    public abstract Optional<InvalidRequestServerMessage> getErrorMessage(
        T requestToValidate,
        GameManager gameManager
    );

    /**
     * Method for creating an error message
     * @param errorMessage message explaining why the request is not valid
     * @param messageArgs optional elements to be included within the message
     * @return Optional<InvalidRequestServerMessage>, see {@link InvalidRequestServerMessage}
     */
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
