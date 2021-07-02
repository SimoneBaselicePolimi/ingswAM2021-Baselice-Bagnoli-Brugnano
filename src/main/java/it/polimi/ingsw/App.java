package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.gui.GuiClient;
import it.polimi.ingsw.client.network.ClientNotConnectedException;
import it.polimi.ingsw.server.Server;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws IOException, ClientNotConnectedException, InterruptedException {
        System.out.println( "Hello World!" );

        if(args[0].equals("server"))
            Server.startServer();
        else if(args[0].equals("cli"))
            new Client().startClient();
        else if(args[0].equals("gui"))
            GuiClient.startClient();
    }

}
