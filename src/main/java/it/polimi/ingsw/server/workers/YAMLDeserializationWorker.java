package it.polimi.ingsw.server.workers;

import it.polimi.ingsw.logger.LogLevel;
import it.polimi.ingsw.logger.ProjectLogger;
import it.polimi.ingsw.network.NetworkProto;
import it.polimi.ingsw.server.controller.clientmessage.ClientMessage;
import it.polimi.ingsw.server.controller.servermessage.ServerMessage;
import it.polimi.ingsw.server.network.ClientRawMessage;
import it.polimi.ingsw.utils.serialization.SerializationHelper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Consumer;

public class YAMLDeserializationWorker extends Thread {

    public static final int MESSAGES_TO_DESERIALIZE_QUEUE_SIZE = 1024;

    ArrayBlockingQueue<ClientRawMessage> clientMessagesToDeserialize = new ArrayBlockingQueue<>(
        MESSAGES_TO_DESERIALIZE_QUEUE_SIZE);

    Consumer<ClientMessage> deserializedMessageProcessingPolicy;

    public YAMLDeserializationWorker(Consumer<ClientMessage> deserializedMessageProcessingPolicy) {
        this.deserializedMessageProcessingPolicy = deserializedMessageProcessingPolicy;
    }

    public void addMessageToDeserialize(ClientRawMessage clientRawMessage) {
        clientMessagesToDeserialize.add(clientRawMessage);
    }

    @Override
    public void run() {

        while (true) {

            ClientRawMessage messageToDeserialize = null;
            try {
                messageToDeserialize = clientMessagesToDeserialize.take();
            } catch (InterruptedException e) {
                ProjectLogger.getLogger().log(e);
            }

            if(messageToDeserialize != null) {
                try {
                    if (messageToDeserialize.type == NetworkProto.MESSAGE_TYPE.GAME_MESSAGE &&
                        messageToDeserialize.valueFormat == NetworkProto.MESSAGE_FORMAT.YAML) {
                        ClientMessage clientMessage = SerializationHelper.deserializeYamlFromBytes(
                            messageToDeserialize.value,
                            ClientMessage.class,
                            messageToDeserialize.sender.getDeserializationContextMap()
                        );
                        deserializedMessageProcessingPolicy.accept(clientMessage);
                    }
                } catch (IOException e) {
                    ProjectLogger.getLogger().log(
                        LogLevel.ERROR,
                        "Unexpected error while deserializing message from client:\n%s",
                        new String(messageToDeserialize.value, StandardCharsets.UTF_8)
                    );
                    ProjectLogger.getLogger().log(e);
                }
            }

        }
    }

}
