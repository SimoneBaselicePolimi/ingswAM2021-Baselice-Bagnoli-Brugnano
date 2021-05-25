package it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ServerLeaderCardRepresentation;

public class ServerActivateLeaderCardsActionRepresentation extends ServerGameActionRepresentation {

    public final Player player;
    public final ServerLeaderCardRepresentation leaderCard;

    public ServerActivateLeaderCardsActionRepresentation(
        @JsonProperty("player") Player player,
        @JsonProperty("leaderCard") ServerLeaderCardRepresentation leaderCard
    ) {
        this.player = player;
        this.leaderCard = leaderCard;
    }
}
