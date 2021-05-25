package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.server.model.Player;

public class ClientDiscardLeaderCardsActionRepresentation extends ClientGameActionRepresentation {
    private final Player player;
    private final ClientLeaderCardRepresentation leaderCard;

    public ClientDiscardLeaderCardsActionRepresentation(
        @JsonProperty("player") Player player,
        @JsonProperty("leaderCard") ClientLeaderCardRepresentation leaderCard
    ) {
        this.player = player;
        this.leaderCard = leaderCard;
    }

    public Player getPlayer() {
        return player;
    }

    public ClientLeaderCardRepresentation getLeaderCard() {
        return leaderCard;
    }
}
