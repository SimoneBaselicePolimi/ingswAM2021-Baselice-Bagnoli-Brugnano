package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

public class ClientDevelopmentActionRepresentation extends ClientGameActionRepresentation {

    @SerializeIdOnly
    public final Player player;

    @SerializeIdOnly
    public final ClientDevelopmentCardRepresentation developmentCard;

    public ClientDevelopmentActionRepresentation(
        @JsonProperty("player") Player player,
        @JsonProperty("developmentCard") ClientDevelopmentCardRepresentation developmentCard
    ) {
        this.player = player;
        this.developmentCard = developmentCard;
    }

    @JsonIgnore
    @Override
    public String getActionMessage() {
        return Localization.getLocalizationInstance().getString(
            "gameState.mainTurn.developmentAction",
            player.getName()
        );
    }

}
