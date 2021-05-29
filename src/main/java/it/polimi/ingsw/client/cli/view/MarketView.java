package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.UserChoicesUtils;

import java.util.concurrent.CompletableFuture;

public class MarketView extends CliView{

    protected GameView gameView;

    public MarketView(CliClientManager clientManager, GameView gameView) {
        super(clientManager);
        this.gameView = gameView;
        startMarketDialog();
    }

    void startMarketDialog() {

        // TEST -------
        UserChoicesUtils.makeUserChoose(clientManager)
            .addUserChoice(
                () -> clientManager.askUserLocalized("Quale riga vuoi?")
                    .thenCompose(input -> CompletableFuture.completedFuture(null)),
                "Prendi riga dal mercato"
            ).addUserChoice(
                () -> clientManager.askUserLocalized("Quale colonna vuoi?")
                .thenCompose(input -> CompletableFuture.completedFuture(null)),
            "Prendi colonna dal mercato"
            ).addUserChoice(
                () -> gameView.setMainContentView(new PlayerDashboardView(clientManager)),
                "Torna al menu"
            ).apply();
        // TEST -------
    }
}
