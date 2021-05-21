package it.polimi.ingsw.client.modelrepresentation.gamecontextrepresentation.faithrepresentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.client.modelrepresentation.ClientPlayerRepresentation;
import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;
import it.polimi.ingsw.server.model.gamecontext.faith.PopeFavorCardState;

import java.util.List;
import java.util.Map;

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
    protected Map<ClientPlayerRepresentation,Integer> faithPositions;

    /**
     * State of the Pope's Favor cards of each Player
     */
    protected Map<ClientPlayerRepresentation, List<PopeFavorCardState>> popeFavorCards;

    /**
     * Victory points scored by each Player
     */
    protected Map<ClientPlayerRepresentation,Integer> victoryPoints;

    public ClientFaithPathRepresentation(
        @JsonProperty("faithPathLength") int faithPathLength,
        @JsonProperty("vaticanReportSections") List<ClientVaticanReportSectionRepresentation> vaticanReportSections,
        @JsonProperty("victoryPointsByPosition") int[] victoryPointsByPosition,
        @JsonProperty("faithPositions") Map<ClientPlayerRepresentation, Integer> faithPositions,
        @JsonProperty("popeFavorCards") Map<ClientPlayerRepresentation, List<PopeFavorCardState>> popeFavorCards,
        @JsonProperty("victoryPoints") Map<ClientPlayerRepresentation, Integer> victoryPoints
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

    public Map<ClientPlayerRepresentation, Integer> getFaithPositions() {
        return faithPositions;
    }

    public Map<ClientPlayerRepresentation, List<PopeFavorCardState>> getPopeFavorCards() {
        return popeFavorCards;
    }

    public Map<ClientPlayerRepresentation, Integer> getVictoryPoints() {
        return victoryPoints;
    }

    public void setFaithPositions(Map<ClientPlayerRepresentation, Integer> faithPositions) {
        this.faithPositions = faithPositions;
    }

    public void setPopeFavorCards(Map<ClientPlayerRepresentation, List<PopeFavorCardState>> popeFavorCards) {
        this.popeFavorCards = popeFavorCards;
    }

    public void setVictoryPoints(Map<ClientPlayerRepresentation, Integer> victoryPoints) {
        this.victoryPoints = victoryPoints;
    }
}
