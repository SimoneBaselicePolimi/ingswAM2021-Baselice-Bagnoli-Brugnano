package it.polimi.ingsw.server.workers;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.polimi.ingsw.logger.LogLevel;
import it.polimi.ingsw.logger.ProjectLogger;
import it.polimi.ingsw.network.NetworkProto;
import it.polimi.ingsw.server.controller.servermessage.ServerMessage;
import it.polimi.ingsw.server.controller.Client;
import it.polimi.ingsw.server.network.ServerRawMessage;
import it.polimi.ingsw.utils.serialization.SerializationHelper;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class YAMLSerializationWorker extends Thread {

    public static final int MESSAGES_TO_SERIALIZE_QUEUE_SIZE = 1024;

    ArrayBlockingQueue<ServerMessage> serverMessagesToSerialize = new ArrayBlockingQueue<>(MESSAGES_TO_SERIALIZE_QUEUE_SIZE);
    Map<ServerMessage, Client> messageReceivers = new ConcurrentHashMap<>();

    Consumer<ServerRawMessage> serializedMessageProcessingPolicy;

    public YAMLSerializationWorker(Consumer<ServerRawMessage> serializedMessageProcessingPolicy) {
        this.serializedMessageProcessingPolicy = serializedMessageProcessingPolicy;
    }

    public void addMessageToSerialize(ServerMessage serverMessage, Client receiver) {
        messageReceivers.put(serverMessage, receiver);
        serverMessagesToSerialize.add(serverMessage);
    }

    @Override
    public void run() {

        while (true) {

            ServerMessage messageToSerialize = null;
            try {
                messageToSerialize = serverMessagesToSerialize.take();
            } catch (InterruptedException e) {
                ProjectLogger.getLogger().log(e);
            }

            if(messageToSerialize != null) {
                try {
                    byte[] messageContent = SerializationHelper.serializeYamlAsBytes(messageToSerialize);
                    ServerRawMessage serverRawMessage = new ServerRawMessage(
                        messageReceivers.get(messageToSerialize),
                        NetworkProto.MESSAGE_TYPE.GAME_MESSAGE,
                        NetworkProto.MESSAGE_FORMAT.YAML,
                        messageContent.length,
                        messageContent
                    );
                    serializedMessageProcessingPolicy.accept(serverRawMessage);
                    messageReceivers.remove(messageToSerialize);
                } catch (JsonProcessingException e) {
                    ProjectLogger.getLogger().log(
                        LogLevel.ERROR,
                        "Unexpected error while serializing server message: %s",
                        messageToSerialize
                    );
                    ProjectLogger.getLogger().log(e);
                }
            }

        }
    }

}
