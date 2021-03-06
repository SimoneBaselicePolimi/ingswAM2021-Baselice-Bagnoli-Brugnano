package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ClientLeaderCardRepresentation;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class ClientDiscardLeaderCardsActionRepresentation extends ClientGameActionRepresentation {

    @SerializeIdOnly
    public final Player player;

    @SerializeIdOnly
    public final ClientLeaderCardRepresentation leaderCard;

    public ClientDiscardLeaderCardsActionRepresentation(
        @JsonProperty("player") Player player,
        @JsonProperty("leaderCard") ClientLeaderCardRepresentation leaderCard
    ) {
        this.player = player;
        this.leaderCard = leaderCard;
    }

    @JsonIgnore
    @Override
    public String getActionMessage() {
        return Localization.getLocalizationInstance().getString(
            "gameHistory.leaderCard.discardCard",
            player.getName()
        );
    }

}
