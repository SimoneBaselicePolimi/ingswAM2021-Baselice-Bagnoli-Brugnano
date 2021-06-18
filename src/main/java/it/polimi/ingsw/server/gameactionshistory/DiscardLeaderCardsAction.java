package it.polimi.ingsw.server.gameactionshistory;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class DiscardLeaderCardsAction extends GameAction {

    @SerializeIdOnly
    private final Player player;

    @SerializeIdOnly
    private final LeaderCard leaderCard;

    public DiscardLeaderCardsAction(
        @JsonProperty("player") Player player,
        @JsonProperty("leaderCard") LeaderCard leaderCard
    ) {
        this.player = player;
        this.leaderCard = leaderCard;
    }

}
