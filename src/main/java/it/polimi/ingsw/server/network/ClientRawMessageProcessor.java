package it.polimi.ingsw.server.network;

import it.polimi.ingsw.server.controller.Client;
import it.polimi.ingsw.server.controller.ServerMessageSender;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ClientRawMessageProcessor {

    private BiConsumer<Client, ServerRawMessageSender> newConnectionProcessingPolicy = (c, s) -> {};

    private BiConsumer<ClientRawMessage, ServerRawMessageSender> defaultMessageProcessingPolicy = (m, s) -> {};;

    private Map<Byte, BiConsumer<ClientRawMessage, ServerRawMessageSender>> processingPoliciesByMessageType = new HashMap<>();

    private Consumer<Client> onConnectionDroppedProcessingPolicy = c -> {};

    public ClientRawMessageProcessor() {};

    @Deprecated
    public ClientRawMessageProcessor(
        BiConsumer<Client, ServerRawMessageSender> newConnectionProcessingPolicy,
        BiConsumer<ClientRawMessage, ServerRawMessageSender> messageProcessingPolicy,
        Consumer<Client> onConnectionDroppedProcessingPolicy
    ) {
        this.newConnectionProcessingPolicy = newConnectionProcessingPolicy;
        this.defaultMessageProcessingPolicy = messageProcessingPolicy;
        this.onConnectionDroppedProcessingPolicy = onConnectionDroppedProcessingPolicy;
    }

    @Deprecated
    public ClientRawMessageProcessor(
        BiConsumer<Client, ServerRawMessageSender> newConnectionProcessingPolicy,
        Map<Byte, BiConsumer<ClientRawMessage, ServerRawMessageSender>> processingPoliciesByMessageType,
        BiConsumer<ClientRawMessage, ServerRawMessageSender> defaultMessageProcessingPolicy,
        Consumer<Client> onConnectionDroppedProcessingPolicy
    ) {
        this.newConnectionProcessingPolicy = newConnectionProcessingPolicy;
        this.processingPoliciesByMessageType = new HashMap<>(processingPoliciesByMessageType);
        this.defaultMessageProcessingPolicy = defaultMessageProcessingPolicy;
        this.onConnectionDroppedProcessingPolicy = onConnectionDroppedProcessingPolicy;
    }

    public void setNewConnectionProcessingPolicy(BiConsumer<Client, ServerRawMessageSender> newConnectionProcessingPolicy) {
        this.newConnectionProcessingPolicy = newConnectionProcessingPolicy;
    }

    public void setMessageProcessingPolicy(BiConsumer<ClientRawMessage, ServerRawMessageSender> defaultMessageProcessingPolicy) {
        this.defaultMessageProcessingPolicy = defaultMessageProcessingPolicy;
    }

    public void setPolicyForMessageType(Byte messageType, BiConsumer<ClientRawMessage, ServerRawMessageSender> processingPolicy) {
        processingPoliciesByMessageType.put(messageType, processingPolicy);
    }

    public void setOnConnectionDroppedProcessingPolicy(Consumer<Client> onConnectionDroppedProcessingPolicy) {
        this.onConnectionDroppedProcessingPolicy = onConnectionDroppedProcessingPolicy;
    }

    public void processNewClientConnection(Client client, ServerRawMessageSender sender) {
        newConnectionProcessingPolicy.accept(client, sender);
    }

    public void processNewMessage(ClientRawMessage message, ServerRawMessageSender sender) {
        BiConsumer<ClientRawMessage, ServerRawMessageSender> policyToApply = processingPoliciesByMessageType.getOrDefault(
            message.type,
            defaultMessageProcessingPolicy
        );
        policyToApply.accept(message, sender);
    }

    public void processConnectionDropped(Client client) {
        onConnectionDroppedProcessingPolicy.accept(client);
    }

}
