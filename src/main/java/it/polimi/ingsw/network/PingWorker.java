package it.polimi.ingsw.network;

import it.polimi.ingsw.logger.ProjectLogger;
import it.polimi.ingsw.server.network.RawMessage;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class PingWorker extends Thread {

    final Consumer<RawMessage> messageSender;
    final Runnable onConnectionDropped;

    AtomicLong lastPingReceivedTime = new AtomicLong();

    public PingWorker(Consumer<RawMessage> messageSender, Runnable onConnectionDropped) {
        this.messageSender = messageSender;
        this.onConnectionDropped = onConnectionDropped;
    }

    @Override
    public void run() {
        lastPingReceivedTime.set(System.currentTimeMillis());
        sendRequestPing();
        while (true) {
            try {
                long timeSinceLastPing = System.currentTimeMillis() - lastPingReceivedTime.get();
                sleep(Math.max(NetworkProto.PING_PROTO.PING_REQUEST_FREQUENCY_MILLIS - timeSinceLastPing, 0));

                timeSinceLastPing = System.currentTimeMillis() - lastPingReceivedTime.get();
                if (NetworkProto.PING_PROTO.NO_COMMUNICATION_ERROR_THRESHOLD < timeSinceLastPing) {
                    onConnectionDropped.run();
                    return;
                } else if (NetworkProto.PING_PROTO.PING_REQUEST_FREQUENCY_MILLIS < timeSinceLastPing) {
                    sendRequestPing();
                    sleep(NetworkProto.PING_PROTO.MIN_INTERVAL_BETWEEN_PINGS);
                }
            } catch (InterruptedException e) {
                ProjectLogger.getLogger().log(e);
            }
        }

    }

    public synchronized void handlePingMessage(RawMessage message) {

        if(message.type != NetworkProto.MESSAGE_TYPE.PING_MESSAGE)
            throw new IllegalArgumentException();

        if (Arrays.equals(message.value, NetworkProto.PING_PROTO.PING_TYPE_REQUEST)) {
            sendAnswerPing();
        } else if (Arrays.equals(message.value, NetworkProto.PING_PROTO.PING_TYPE_ANSWER))  {
            lastPingReceivedTime.set(System.currentTimeMillis());
        }
    }

    protected void sendMessage(RawMessage message) {
        messageSender.accept(message);
    }

    protected void sendRequestPing() {
        sendMessage(new RawMessage(
            NetworkProto.MESSAGE_TYPE.PING_MESSAGE,
            (byte) 0,
            1,
            NetworkProto.PING_PROTO.PING_TYPE_REQUEST
        ));
    }

    protected void sendAnswerPing() {
        sendMessage(new RawMessage(
            NetworkProto.MESSAGE_TYPE.PING_MESSAGE,
            (byte) 0,
           1,
            NetworkProto.PING_PROTO.PING_TYPE_ANSWER
        ));
    }

}
