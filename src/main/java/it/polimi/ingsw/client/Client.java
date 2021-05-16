package it.polimi.ingsw.client;

import it.polimi.ingsw.logger.LogLevel;
import it.polimi.ingsw.logger.ProjectLogger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class Client {

    protected static final ProjectLogger logger = ProjectLogger.getLogger();

    public static void main( String[] args ) {

        try {
            startClient();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    
    public static void startClient() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 11056));

        while(!socketChannel.finishConnect());

        logger.log(LogLevel.INFO, socketChannel.isConnected() ? "ok" : "ko");

        ByteBuffer h = ByteBuffer.allocate(1 + 1 + 4);
        ByteBuffer s = ByteBuffer.allocate(10);
        Byte b = 0;
        Byte f = 0;
        int len = s.limit();
        h.put(b);
        h.put(f);
        h.putInt(len);
        s.put(new byte[]{1,2,3,4,5,6,7,8,9,10});
        h.flip();
        s.flip();
        while (h.hasRemaining())
            socketChannel.write(h);
        while (s.hasRemaining())
            socketChannel.write(s);
        h.flip();
        s.flip();
        while (h.hasRemaining())
            socketChannel.write(h);
        while (s.hasRemaining())
            socketChannel.write(s);
        logger.log(LogLevel.INFO, "written!!!!");

        ByteBuffer h2 = ByteBuffer.allocate(1 + 1 + 4);
        ByteBuffer r = ByteBuffer.allocate(4);
        while (h2.hasRemaining())
            socketChannel.read(h2);
        while (r.hasRemaining())
            socketChannel.read(r);
        r.flip();
        byte[] cont = r.array();
        logger.log(LogLevel.INFO, Arrays.toString(cont));



    }

}
