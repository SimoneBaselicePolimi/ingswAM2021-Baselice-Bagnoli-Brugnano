package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientProductionRepresentation;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsSetOfIds;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.Set;

public class ClientProductionActionRepresentation extends ClientGameActionRepresentation {

    @SerializeIdOnly
    public final Player player;

    @SerializeAsSetOfIds
    public final Set<ClientProductionRepresentation> productions;

    public ClientProductionActionRepresentation(
        @JsonProperty("player") Player player,
        @JsonProperty("productions") Set<ClientProductionRepresentation> productions
    ) {
        this.player = player;
        this.productions = productions;
    }

    @JsonIgnore
    @Override
    public String getActionMessage() {
        return Localization.getLocalizationInstance().getString(
            "gameHistory.gameState.mainTurn.productionAction",
            player.getName()
        );
    }

}
