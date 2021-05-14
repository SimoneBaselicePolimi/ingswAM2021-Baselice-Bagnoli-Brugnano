package it.polimi.ingsw.server.network;

import it.polimi.ingsw.server.controller.Client;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ClientRawMessageProcessor {

    private Consumer<Client> newConnectionProcessingPolicy;

    private Consumer<RawMessage> defaultMessageProcessingPolicy;

    private Map<Byte, Consumer<RawMessage>> processingPoliciesByMessageType = new HashMap<>();

    private Consumer<Client> onConnectionDroppedProcessingPolicy;

    public ClientRawMessageProcessor(
        Consumer<Client> newConnectionProcessingPolicy,
        Consumer<RawMessage> defaultMessageProcessingPolicy,
        Consumer<Client> onConnectionDroppedProcessingPolicy
    ) {
        this.newConnectionProcessingPolicy = newConnectionProcessingPolicy;
        this.defaultMessageProcessingPolicy = defaultMessageProcessingPolicy;
        this.onConnectionDroppedProcessingPolicy = onConnectionDroppedProcessingPolicy;
    }

    public ClientRawMessageProcessor(
        Consumer<Client> newConnectionProcessingPolicy,
        Map<Byte, Consumer<RawMessage>> processingPoliciesByMessageType,
        Consumer<RawMessage> defaultMessageProcessingPolicy,
        Consumer<Client> onConnectionDroppedProcessingPolicy
    ) {
        this.newConnectionProcessingPolicy = newConnectionProcessingPolicy;
        this.processingPoliciesByMessageType = new HashMap<>(processingPoliciesByMessageType);
        this.defaultMessageProcessingPolicy = defaultMessageProcessingPolicy;
        this.onConnectionDroppedProcessingPolicy = onConnectionDroppedProcessingPolicy;
    }

    public void addPolicyForMessageType(Byte messageType, Consumer<RawMessage> processingPolicy) {
        processingPoliciesByMessageType.put(messageType, processingPolicy);
    }

    public void processNewClientConnection(Client client) {
        newConnectionProcessingPolicy.accept(client);
    }

    public void processNewMessage(RawMessage message) {
        Consumer<RawMessage> policyToApply = processingPoliciesByMessageType.getOrDefault(
            message.type,
            defaultMessageProcessingPolicy
        );
        policyToApply.accept(message);
    }

    public void processConnectionDropped(Client client) {
        onConnectionDroppedProcessingPolicy.accept(client);
    }

}
