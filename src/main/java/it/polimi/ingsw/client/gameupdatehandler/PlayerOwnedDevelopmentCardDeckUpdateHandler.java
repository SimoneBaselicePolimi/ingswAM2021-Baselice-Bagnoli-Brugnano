package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientPlayerOwnedDevelopmentCardDeckUpdate;
import it.polimi.ingsw.client.model.GameContextRepresentation;
import it.polimi.ingsw.client.model.PlayerOwnedDevelopmentCardDeckRepresentation;
import it.polimi.ingsw.client.model.PlayerRepresentation;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerMarketUpdate;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerPlayerOwnedDevelopmentCardDeckUpdate;

import java.util.List;

public class PlayerOwnedDevelopmentCardDeckUpdateHandler extends GameUpdateHandler<ClientPlayerOwnedDevelopmentCardDeckUpdate> {
    @Override
    public void handleGameUpdate(
        ClientPlayerOwnedDevelopmentCardDeckUpdate update,
        GameContextRepresentation gameContextRepresentation
    ) {

        PlayerRepresentation sender = gameContextRepresentation.getActivePlayer();
        List<PlayerOwnedDevelopmentCardDeckRepresentation> playerOwnedDevelopmentCardDecks = gameContextRepresentation
            .getPlayerContexts().get(sender).getDevelopmentCardDecks();
        for (PlayerOwnedDevelopmentCardDeckRepresentation deck : playerOwnedDevelopmentCardDecks) {
            if (deck.equals(update.deck))
                deck.setCardDeck(update.developmentCardsDeck);

        }
    }
}