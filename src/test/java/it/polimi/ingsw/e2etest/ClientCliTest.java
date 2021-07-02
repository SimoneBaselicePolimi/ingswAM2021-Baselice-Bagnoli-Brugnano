package it.polimi.ingsw.e2etest;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.cli.ConsoleWriter;
import it.polimi.ingsw.client.network.ClientNotConnectedException;
import it.polimi.ingsw.server.Server;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class ClientCliTest {

    void testCliClient() throws InterruptedException, IOException {

        Thread serverThread = new Thread(Server::startServer);
        serverThread.start();

        TimeUnit.MILLISECONDS.sleep(1000);

        new MockPlayer(200, "player1", true, 2).start();
        new MockPlayer(1000, "player2", false, 0).start();


        TimeUnit.SECONDS.sleep(100);


    }

    static class MockPlayer extends Thread {

        public final int startupDelayMillis;
        public final String mockPlayerName;
        public final boolean shouldCreateLobby;
        public final int lobbySize;

        PrintStream userInputMocker;

        MockPlayer(int startupDelayMillis, String mockPlayerName, boolean shouldCreateLobby, int lobbySize) {
            this.startupDelayMillis = startupDelayMillis;
            this.mockPlayerName = mockPlayerName;
            this.shouldCreateLobby = shouldCreateLobby;
            this.lobbySize = lobbySize;
        }

        @Override
        public void run() {

            PipedInputStream mockUserInputStream = new PipedInputStream();
            BufferedReader mockClientReader = new BufferedReader(new InputStreamReader(mockUserInputStream));

            try {
                userInputMocker = new PrintStream(new PipedOutputStream(mockUserInputStream));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            Client client = new Client(
                mockClientReader,
                new ConsoleWriter() {
                    @Override
                    public void writeToConsole(String text) {
                        System.out.print(text);
                    }

                    @Override
                    public void writeNewLineToConsole(String line) {
                        System.out.println(line);
                    }
                }
            );

            try {
                TimeUnit.MILLISECONDS.sleep(startupDelayMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Thread(() -> {
                try {
                    client.startClient();
                } catch (IOException | ClientNotConnectedException e) {
                    e.printStackTrace();
                }
            }).start();

            try {
                startPlayerDialog();
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        private void startPlayerDialog() throws InterruptedException {

            TimeUnit.MILLISECONDS.sleep(300);
            userInputMocker.println(mockPlayerName);


            TimeUnit.MILLISECONDS.sleep(1200);

            if(shouldCreateLobby) {
                userInputMocker.println(lobbySize);
                TimeUnit.MILLISECONDS.sleep(300);
            }

            userInputMocker.println(1);
            TimeUnit.MILLISECONDS.sleep(300);

            userInputMocker.println(1);
            TimeUnit.MILLISECONDS.sleep(300);

            userInputMocker.println(2);
            TimeUnit.MILLISECONDS.sleep(300);



        }

    }




}

