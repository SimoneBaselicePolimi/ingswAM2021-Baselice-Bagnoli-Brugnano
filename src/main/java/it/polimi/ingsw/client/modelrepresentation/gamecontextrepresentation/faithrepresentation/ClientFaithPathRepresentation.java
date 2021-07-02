package it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.faithrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.PopeFavorCardState;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsMapWithIdKey;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClientFaithPathRepresentation extends ClientRepresentation {

    /**
     * Length of the Faith Track
     */
    protected final int faithPathLength;

    /**
     * List of spaces in which a Vatican Report occurs
     */
    protected final List<ClientVaticanReportSectionRepresentation> vaticanReportSections;

    /**
     * Array of victory points given at the end of the Game to the Player based on his position in the Faith Track
     */
    protected final int[] victoryPointsByPosition;

    /**
     * Position of each Player in the Faith Track
     */
    @SerializeAsMapWithIdKey
    protected Map<Player,Integer> faithPositions;

    /**
     * State of the Pope's Favor cards of each Player
     */
    @SerializeAsMapWithIdKey
    protected Map<Player, List<PopeFavorCardState>> popeFavorCards;

    /**
     * Victory points scored by each Player
     */
    @SerializeAsMapWithIdKey
    protected Map<Player,Integer> victoryPoints;

    public ClientFaithPathRepresentation(
        @JsonProperty("faithPathLength") int faithPathLength,
        @JsonProperty("vaticanReportSections") List<ClientVaticanReportSectionRepresentation> vaticanReportSections,
        @JsonProperty("victoryPointsByPosition") int[] victoryPointsByPosition,
        @JsonProperty("faithPositions") Map<Player, Integer> faithPositions,
        @JsonProperty("popeFavorCards") Map<Player, List<PopeFavorCardState>> popeFavorCards,
        @JsonProperty("victoryPoints") Map<Player, Integer> victoryPoints
        ) {
        this.faithPathLength = faithPathLength;
        this.vaticanReportSections = vaticanReportSections;
        this.victoryPointsByPosition = victoryPointsByPosition;
        this.faithPositions = faithPositions;
        this.popeFavorCards = popeFavorCards;
        this.victoryPoints = victoryPoints;
    }

    public int getFaithPathLength() {
        return faithPathLength;
    }

    public List<ClientVaticanReportSectionRepresentation> getVaticanReportSections() {
        return vaticanReportSections;
    }

    public int[] getVictoryPointsByPosition() {
        return victoryPointsByPosition;
    }

    public Map<Player, Integer> getFaithPositions() {
        return faithPositions;
    }

    public Map<Player, List<PopeFavorCardState>> getPopeFavorCards() {
        return popeFavorCards;
    }

    public Map<Player, Integer> getVictoryPoints() {
        return victoryPoints;
    }

    public void setFaithPositions(Map<Player, Integer> faithPositions) {
        this.faithPositions = faithPositions;
        notifyViews();
    }

    public void setPopeFavorCards(Map<Player, List<PopeFavorCardState>> popeFavorCards) {
        this.popeFavorCards = popeFavorCards;
        notifyViews();
    }

    public void setVictoryPoints(Map<Player, Integer> victoryPoints) {
        this.victoryPoints = victoryPoints;
        notifyViews();
    }

    public String getPositionOfPlayersAsString() {
        Set<Player> players = faithPositions.keySet();
        StringBuilder positionOfPlayers = new StringBuilder();
        for(Player player : players) {
            positionOfPlayers.append(player.playerName).append(": ");
            positionOfPlayers.append(
                Localization.getLocalizationInstance().getString("gameHistory.faithPath.position")
            );
            positionOfPlayers.append(" ").append(getFaithPositions().get(player));
            positionOfPlayers.append("\n");
        }
        return positionOfPlayers.toString();
    }
}
