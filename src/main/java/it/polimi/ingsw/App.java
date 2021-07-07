package it.polimi.ingsw;

import it.polimi.ingsw.client.cli.CliClient;
import it.polimi.ingsw.client.gui.GuiClient;
import it.polimi.ingsw.client.network.ClientNotConnectedException;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.Server;
import javafx.application.Application;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws IOException, ClientNotConnectedException, InterruptedException {

        if(args.length == 0) {
            System.out.println("Argomenti non validi");
        } else if(args[0].equals("server")) {
            Server.startServer();
        } else if(args[0].equals("cli")) {
            if(args.length == 1)
                new CliClient().startClient();
            else {
                if(args.length > 2)
                    setLang(args[2]);
                new CliClient().startClient(args[1]);
            }
        } else if(args[0].equals("gui")) {
            if(args.length == 1) {
                GuiClient.startClient();
            } else {
                if(args.length > 2)
                    setLang(args[2]);
                GuiClient.startClient(args[1]);
            }
        }

    }


    static void setLang(String lang) {
        if (lang.equals("it"))
            Localization.getLocalizationInstance().setLocalizationLanguage("it");
        else if (lang.equals("en"))
            Localization.getLocalizationInstance().setLocalizationLanguage("en");
    }

}
