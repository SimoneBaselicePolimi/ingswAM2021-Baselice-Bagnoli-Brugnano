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

}
