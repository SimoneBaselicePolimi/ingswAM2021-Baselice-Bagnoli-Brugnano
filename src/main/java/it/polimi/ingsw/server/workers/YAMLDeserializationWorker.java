package it.polimi.ingsw.server.workers;

import it.polimi.ingsw.network.NetworkProto;
import it.polimi.ingsw.server.controller.clientmessage.ClientMessage;
import it.polimi.ingsw.server.network.ClientRawMessage;
import it.polimi.ingsw.utils.serialization.SerializationHelper;

import java.io.IOException;
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
            try {
                ClientRawMessage messageToDeserialize = clientMessagesToDeserialize.take();
                if(messageToDeserialize.type == NetworkProto.MESSAGE_TYPE.GAME_MESSAGE &&
                    messageToDeserialize.valueFormat == NetworkProto.MESSAGE_FORMAT.YAML) {
                    ClientMessage clientMessage = SerializationHelper.deserializeYamlFromBytes(
                        messageToDeserialize.value,
                        ClientMessage.class,
                        messageToDeserialize.sender.getDeserializationContextMap()
                    );
                    deserializedMessageProcessingPolicy.accept(clientMessage);
                }
            } catch (InterruptedException e) {
                //TODO
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                //TODO
            }


        }
    }

}
