package it.polimi.ingsw.server;

import it.polimi.ingsw.logger.LogLevel;
import it.polimi.ingsw.logger.ProjectLogger;
import it.polimi.ingsw.network.NetworkProto;
import it.polimi.ingsw.network.PingWorker;
import it.polimi.ingsw.server.controller.Client;
import it.polimi.ingsw.server.controller.PlayerRegistrationAndDispatchController;
import it.polimi.ingsw.server.controller.ServerMessageSender;
import it.polimi.ingsw.server.controller.clientmessage.PlayerRequestClientMessage;
import it.polimi.ingsw.server.network.ClientRawMessageProcessor;
import it.polimi.ingsw.server.network.NetworkLayer;
import it.polimi.ingsw.server.network.ServerRawMessage;
import it.polimi.ingsw.server.workers.YAMLDeserializationWorker;
import it.polimi.ingsw.server.workers.YAMLSerializationWorker;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    protected static final ProjectLogger logger = ProjectLogger.getLogger();

    private static final int TCP_SERVER_PORT = 11056;

    public static void main( String[] args ) {
        startServer();

    }

    public static void startServer() {

        ClientRawMessageProcessor clientRawMessageProcessor = new ClientRawMessageProcessor();

        NetworkLayer networkLayer = new NetworkLayer(TCP_SERVER_PORT, clientRawMessageProcessor);

        YAMLSerializationWorker serializationWorker = new YAMLSerializationWorker(networkLayer::sendMessage);

        Map<Client, PingWorker> pingWorkerForClientMap = new ConcurrentHashMap<>();

        YAMLDeserializationWorker deserializationWorker = new YAMLDeserializationWorker(
            deserializedClientMessage -> {

                String messageDescription;
                if(deserializedClientMessage instanceof PlayerRequestClientMessage)
                    messageDescription = String.format(
                        "%s [Player request: %s]",
                        deserializedClientMessage,
                        ((PlayerRequestClientMessage)deserializedClientMessage).request
                    );
                else
                    messageDescription = String.valueOf(deserializedClientMessage);

                logger.log(
                    LogLevel.BORING_INFO,
                    "A message received from client %s has been deserialized. Message type: %s",
                    deserializedClientMessage.client.getClientId(),
                    messageDescription
                );

                deserializedClientMessage.client.getHandler().addNewMessageToQueue(deserializedClientMessage);
            }
        );

        ServerMessageSender messageSendingPolicy = (serverMessage, receiver) -> {
            logger.log(
                LogLevel.BORING_INFO,
                "Sending message to client %s. Message type: %s",
                receiver.getClientId(),
                serverMessage
            );
            serializationWorker.addMessageToSerialize(serverMessage, receiver);
        };

        PlayerRegistrationAndDispatchController dispatcherController = new PlayerRegistrationAndDispatchController(
            messageSendingPolicy
        );

        clientRawMessageProcessor.setNewConnectionProcessingPolicy(
            (client, sender) -> {
                logger.log(LogLevel.BORING_INFO, "New client connected: %s", client.getClientId());
                dispatcherController.acceptNewClient(client);
                PingWorker pingWorker = new PingWorker(
                    rawMessage -> {
                        networkLayer.sendMessage(new ServerRawMessage(
                            client,
                            rawMessage.type,
                            rawMessage.valueFormat,
                            rawMessage.valueLength,
                            rawMessage.value
                        ));
                    },
                    () -> client.getHandler().onConnectionDropped(client)
                );
                pingWorkerForClientMap.put(client, pingWorker);
                //pingWorker.start();
            }
        );

        clientRawMessageProcessor.setPolicyForMessageType(
            NetworkProto.MESSAGE_TYPE.GAME_MESSAGE,
            (message, sender) -> {
                logger.log(
                    LogLevel.BORING_INFO,
                    "New message received from client %s. [%s bytes]",
                    message.sender,
                    message.valueLength
                );
                deserializationWorker.addMessageToDeserialize(message);
            }
        );

        clientRawMessageProcessor.setPolicyForMessageType(
            NetworkProto.MESSAGE_TYPE.PING_MESSAGE,
            (message, sender) -> {
                pingWorkerForClientMap.get(message.sender).handlePingMessage(message);
            }
        );

        clientRawMessageProcessor.setOnConnectionDroppedProcessingPolicy(
            client -> logger.log(LogLevel.INFO, "client disconnected %s", client.getClientId())
        );

        serializationWorker.start();
        deserializationWorker.start();
        dispatcherController.start();
        try {
            networkLayer.start();
        } catch (IOException e) {
            //TODO
            e.printStackTrace();
        }

    }

    public static void startTestServer() {
        ClientRawMessageProcessor clientRawMessageProcessor = new ClientRawMessageProcessor();
        clientRawMessageProcessor.setNewConnectionProcessingPolicy(
            (client, sender) -> {
                logger.log(LogLevel.INFO, "new conn %s", client.getClientId());
                sender.sendMessage(new ServerRawMessage(client, (byte)0, (byte)0, 4, new byte[]{4, 3, 2 ,1}));
            }
        );
        clientRawMessageProcessor.setMessageProcessingPolicy(
            (message, sender) -> logger.log(LogLevel.INFO, Arrays.toString(message.value))
        );
        clientRawMessageProcessor.setOnConnectionDroppedProcessingPolicy(
            client -> logger.log(LogLevel.INFO, "client disconnected %s", client.getClientId())
        );
        NetworkLayer net = new NetworkLayer(TCP_SERVER_PORT, clientRawMessageProcessor);

        try {
            net.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
