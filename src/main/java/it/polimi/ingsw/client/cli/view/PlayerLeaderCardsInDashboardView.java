package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.ServerMessageUtils;
import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.UserChoicesUtils;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.clientmessage.PlayerRequestClientMessage;
import it.polimi.ingsw.client.clientrequest.ActivateLeaderCardClientRequest;
import it.polimi.ingsw.client.clientrequest.DiscardLeaderCardClientRequest;
import it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.playercontextrepresentation.ClientPlayerContextRepresentation;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.client.servermessage.GameUpdateServerMessage;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCardState;
import it.polimi.ingsw.utils.Colour;

import java.util.concurrent.CompletableFuture;

/**
 *Vview representing the set of leader cards the player has and can choose to activate
 */
public class PlayerLeaderCardsInDashboardView extends AbstractPlayerLeaderCardsInDashboardView {

    protected Player player;
    protected ClientPlayerContextRepresentation playerContext;

    public PlayerLeaderCardsInDashboardView(CliClientManager clientManager, Player player, GameView gameView) {
        super(player, clientManager, gameView);
        this.player = player;
        this.playerContext = clientManager.getGameContextRepresentation().getPlayerContext(player);
        startLeaderCardsInDashboardDialog();
    }

    void startLeaderCardsInDashboardDialog() {
        UserChoicesUtils.PossibleUserChoices userChoices = UserChoicesUtils.makeUserChoose(clientManager);

        if(player.equals(clientManager.getMyPlayer())
            && player.equals(clientManager.getGameContextRepresentation().getActivePlayer())) {
            userChoices.addUserChoiceLocalized(
                this::activateNewLeaderCard,
                "client.cli.playerDashboard.activateNewLeaderCard"
            );
            userChoices.addUserChoiceLocalized(
                this::discardLeaderCard,
                "client.cli.playerDashboard.discardLeaderCard"
            );
        }

        userChoices.addUserChoiceLocalized(
            () -> gameView.setMainContentView(new MainMenuView(clientManager, gameView)),
            "client.cli.game.returnToMenu"
        );

        userChoices.apply();
    }

    protected void discardLeaderCard() {
        if(leaderCardList.stream().anyMatch(c -> c.getState() == LeaderCardState.HIDDEN)) {
            UserChoicesUtils.PossibleUserChoices userChoices = UserChoicesUtils.makeUserChoose(clientManager);
            leaderCardList.stream()
                .filter(c -> c.getState() == LeaderCardState.HIDDEN)
                .forEach(c -> userChoices.addUserChoiceLocalized(
                    () -> clientManager.sendMessageAndGetAnswer(new PlayerRequestClientMessage(
                        new DiscardLeaderCardClientRequest(
                            clientManager.getMyPlayer(),
                            c
                        )
                    )).thenCompose(serverMessage ->
                        ServerMessageUtils.ifMessageTypeCompute(
                            serverMessage,
                            GameUpdateServerMessage.class,
                            message -> {
                                clientManager.handleGameUpdates(message.gameUpdates);
                                startLeaderCardsInDashboardDialog();
                                return CompletableFuture.completedFuture(null);
                            }
                        ).elseCompute(message -> {
                            startLeaderCardsInDashboardDialog();
                            return CompletableFuture.completedFuture(null);
                        }).apply()
                    ),
                    "client.cli.playerDashboard.selectLeaderCardToDiscard",
                    leaderCardList.indexOf(c)+1
                ));
            userChoices.apply();
        }
        else {
            clientManager.tellUserLocalized("client.cli.playerDashboard.canNotDiscardLeaderCard");
            startLeaderCardsInDashboardDialog();
        }
    }

    protected void activateNewLeaderCard() {

        if(leaderCardList.stream().anyMatch(c -> c.canBeActivated() && c.getState() == LeaderCardState.HIDDEN)) {
            UserChoicesUtils.PossibleUserChoices userChoices = UserChoicesUtils.makeUserChoose(clientManager);
            leaderCardList.stream()
                .filter(c -> c.canBeActivated() && c.getState() == LeaderCardState.HIDDEN)
                .forEach(c -> userChoices.addUserChoiceLocalized(
                    () -> clientManager.sendMessageAndGetAnswer(new PlayerRequestClientMessage(
                        new ActivateLeaderCardClientRequest(
                            clientManager.getMyPlayer(),
                            c
                        )
                    )).thenCompose(serverMessage ->
                        ServerMessageUtils.ifMessageTypeCompute(
                            serverMessage,
                            GameUpdateServerMessage.class,
                            message -> {
                                clientManager.handleGameUpdates(message.gameUpdates);
                                startLeaderCardsInDashboardDialog();
                                return CompletableFuture.completedFuture(null);
                            }
                        ).elseCompute(message -> {
                            startLeaderCardsInDashboardDialog();
                            return CompletableFuture.completedFuture(null);
                        }).apply()
                    ),
                    "client.cli.playerDashboard.selectLeaderCardToActivate",
                    leaderCardList.indexOf(c)+1
                ));
            userChoices.apply();
        }
        else {
            clientManager.tellUserLocalized("client.cli.playerDashboard.canNotActivateNewLeaderCard");
            startLeaderCardsInDashboardDialog();
        }

    }

    @Override
    public FormattedCharsBuffer getContentAsFormattedCharsBuffer() {

        if(leaderCardsPlayer.equals(clientManager.getMyPlayer())) {
            descriptionView.setText(FormattedChar.convertStringToFormattedCharList(
                Localization.getLocalizationInstance().getString(
                    "client.cli.playerDashboard.lookAtLeaderCardsOfMyPlayer"
                )
            ));
        } else {
            descriptionView.setText(FormattedChar.convertStringToFormattedCharList(
                Localization.getLocalizationInstance().getString(
                    "client.cli.playerDashboard.lookAtLeaderCardsOfAnotherPlayer",
                    leaderCardsPlayer.playerName,
                    leaderCardsPlayerContext.getNumberOfLeaderCardsGivenToThePlayer()
                        - leaderCardsPlayerContext.getLeaderCardsPlayerOwns().size()
                )
            ));
        }

        for (ClientLeaderCardRepresentation card : leaderCardList) {
            AbstractLeaderCardView cardView = cardListView.getLeaderCardViewByLeaderCardRepresentation(card);
            if (card.getState().equals(LeaderCardState.ACTIVE))
                cardView.setBorderColour(Colour.GREEN, false);
            else if (card.getState().equals(LeaderCardState.DISCARDED))
                cardView.setBorderColour(Colour.RED, false);
            else if (card.canBeActivated())
                cardView.setBorderColour(Colour.YELLOW, false);
            else
                cardView.setBorderColour(Colour.GREY, false);
        }

        return super.getContentAsFormattedCharsBuffer();
    }

}
