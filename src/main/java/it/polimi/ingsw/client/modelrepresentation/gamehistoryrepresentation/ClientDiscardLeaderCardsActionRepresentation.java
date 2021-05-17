package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.client.modelrepresentation.ClientPlayerRepresentation;

public class ClientDiscardLeaderCardsActionRepresentation extends ClientGameActionRepresentation {
    private final ClientPlayerRepresentation player;
    private final ClientLeaderCardRepresentation leaderCard;

    public ClientDiscardLeaderCardsActionRepresentation(ClientPlayerRepresentation player, ClientLeaderCardRepresentation leaderCard) {
        this.player = player;
        this.leaderCard = leaderCard;
    }

    public ClientPlayerRepresentation getPlayer() {
        return player;
    }

    public ClientLeaderCardRepresentation getLeaderCard() {
        return leaderCard;
    }
}
