package it.polimi.ingsw.network;

public class NetworkProto {

    public static class MESSAGE_TYPE {

        public static final byte PING_MESSAGE = 11;

        public static final byte GAME_MESSAGE = 12;

    }

    public static class MESSAGE_FORMAT {

        public static final byte YAML = 2;

        public static final byte JSON = 1;

    }

    public static class PING_PROTO {

        public static final long MIN_INTERVAL_BETWEEN_PINGS = 450; //500
        public static final long PING_REQUEST_FREQUENCY_MILLIS = 1300; //400
        public static final long NO_COMMUNICATION_ERROR_THRESHOLD = 3550; //1550

        public static final byte[] PING_TYPE_REQUEST = {(byte) 0};
        public static final byte[] PING_TYPE_ANSWER = {(byte) 1};

        public static final long STARTUP_DELAY_MILLIS = 5000;

    }


}
