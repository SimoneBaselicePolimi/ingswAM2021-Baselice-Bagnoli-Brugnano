package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.ClientMarbleColourRepresentation;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.localization.LocalizationUtils;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsMapWithIdKey;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClientObtainedMarblesMarketActionRepresentation extends ClientGameActionRepresentation {

    @SerializeIdOnly
    public final Player player;

    @SerializeAsMapWithIdKey
    public final Map<ClientMarbleColourRepresentation, Integer> marbleColours;

    public ClientObtainedMarblesMarketActionRepresentation(
        @JsonProperty("player") Player player,
        @JsonProperty("marbleColours") Map<ClientMarbleColourRepresentation, Integer> marbleColours
    ) {
        this.player = player;
        this.marbleColours = marbleColours;
    }

    @JsonIgnore
    @Override
    public List<FormattedChar> getActionMessage() {
        List<FormattedChar> message = new ArrayList<>();
        message.addAll(FormattedChar.convertStringToFormattedCharList(Localization.getLocalizationInstance().getString(
            "gameHistory.gameState.mainTurn.obtainedMarblesMarketAction",
            player.getName()
        )));
        message.addAll(LocalizationUtils.getMarblesAsListOfFormattedChar(marbleColours));
        return message;
    }

}
