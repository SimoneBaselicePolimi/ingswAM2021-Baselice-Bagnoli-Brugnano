package it.polimi.ingsw.client;

import it.polimi.ingsw.network.servermessage.ServerMessage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class ServerMessageUtils {

    public static <M extends ServerMessage, T> ServerMessageTypeDependentComputation<T> ifMessageTypeCompute(
        ServerMessage message,
        Class<M> messageType,
        Function<M, T> computation
    ) {
        return new ServerMessageTypeDependentComputation<>(message, messageType, computation);
    }

    public static class ServerMessageTypeDependentComputation<T> {

        class TypeBasedComputation<M extends ServerMessage> {
            final Class<M> messageType;
            final Function<M, T> computation;

            public TypeBasedComputation(Class<M> messageType, Function<M, T> computation) {
                this.messageType = messageType;
                this.computation = computation;
            }

            public T applyComp(ServerMessage message) {
                return computation.apply(messageType.cast(message));
            }

        }

        final ServerMessage message;
        final Set<TypeBasedComputation<? extends ServerMessage>> typeBasedComputation;
        final TypeBasedComputation<ServerMessage> genericTypeComputation;

        ServerMessageTypeDependentComputation(ServerMessage message) {
            this.message = message;
            typeBasedComputation = new HashSet<>();
            genericTypeComputation = new TypeBasedComputation<>(ServerMessage.class, m -> null);
        }

        <M extends ServerMessage> ServerMessageTypeDependentComputation(
            ServerMessage message,
            Class<M> messageType,
            Function<M, T> computation
        ) {
            this(message);
            typeBasedComputation.add(new TypeBasedComputation<>(messageType, computation));
        }

        <M extends ServerMessage> ServerMessageTypeDependentComputation(
            ServerMessageTypeDependentComputation<T> base,
            Class<M> messageType,
            Function<M, T> computation
        ) {
            this.message = base.message;
            typeBasedComputation = base.typeBasedComputation;
            genericTypeComputation = base.genericTypeComputation;
            typeBasedComputation.add(new TypeBasedComputation<>(messageType, computation));
        }

        ServerMessageTypeDependentComputation(
            ServerMessageTypeDependentComputation<T> base,
            Function<ServerMessage, T> genericTypeComputation
        ) {
            this.message = base.message;
            typeBasedComputation = base.typeBasedComputation;
            this.genericTypeComputation = new TypeBasedComputation<>(ServerMessage.class, genericTypeComputation);
        }

        public <M extends ServerMessage> ServerMessageTypeDependentComputation<T> elseIfMessageTypeCompute(
            Class<M> messageType,
            Function<M, T> computation
        ) {
            return new ServerMessageTypeDependentComputation<T>(this, messageType, computation);
        }

        public ServerMessageTypeDependentComputation<T> elseCompute(
            Function<ServerMessage, T> computation
        ) {
            return new ServerMessageTypeDependentComputation<T>(this, computation);
        }

        public T apply() {
            Optional<TypeBasedComputation<? extends ServerMessage>> compForType = typeBasedComputation.stream()
                .filter(c -> c.messageType.equals(message.getClass()))
                .findAny();
            if(compForType.isPresent())
                return compForType.get().applyComp(message);
            else
                return genericTypeComputation.applyComp(message);

        }
    }
}
