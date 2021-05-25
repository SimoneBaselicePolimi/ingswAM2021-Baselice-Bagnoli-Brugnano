package it.polimi.ingsw.server.model.gamecontext.faith;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamehistory.GameHistory;
import it.polimi.ingsw.server.modelrepresentation.gamecontextrepresentation.faithrepresentation.ServerFaithPathSinglePlayerRepresentation;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class represents the Faith Track in the Solo mode, in which a Single Player moves a Faith Marker challenging
 * the Black Cross token (representing Lorenzo il Magnifico) to reach first the final space of the Track.
 */
public class FaithPathSinglePlayerImp extends FaithPathImp implements FaithPathSinglePlayer {
	/**
	 * The position in the Faith Track of Lorenzo Magnifico, represented by a Black Cross token
	 */
    private int blackCrossFaithPosition;

	/**
	 * Faith Path in Solo mode constructor.
	 * @param faithPathLength length of the Faith Track
	 * @param vaticanSections spaces in which a Vatican Report can occur
	 * @param victoryPointsByPosition victory points given by each space in the Faith Track
	 * @param player single Player who's playing
	 */
	public FaithPathSinglePlayerImp(int faithPathLength, List<VaticanReportSection> vaticanSections,
									int[] victoryPointsByPosition, Player player, GameHistory gameHistory) {
		super(faithPathLength, vaticanSections, victoryPointsByPosition, Stream.of(player).collect(Collectors.toSet()), gameHistory);
	}

	/**
	 * Returns the position of Lorenzo il Magnifico in the Faith Track.
	 * @return position of the Black Cross token in the Faith Track
	 */
	@Override
	public int getBlackCrossFaithPosition() {
		return blackCrossFaithPosition;
	}

	/**
	 This method represent the action of the Black Cross token (representing Lorenzo il Magnifico) moving
	 in the Faith Track for a specific number of steps forward. This action can trigger a Vatican Report if a Pope space
	 is reached. In this case, the single Player has to change the state of his Pope's Favor cards based on his position
	 in the Track.
	 * @param steps number of steps the Black Cross token has to move forward in the Track
	 * @return a Faith Path Event, which states if a Vatican Report happened or if the end of the Faith Track
	 * is reached by the Black Cross token after moving, triggering the immediate end of the Game
	 */
	@Override
	public FaithPathEvent moveBlackCross(int steps) {
        blackCrossFaithPosition = Math.min(blackCrossFaithPosition + steps, faithPathLength - 1);

        boolean endReached = false;
		boolean vaticanReport = false;

        if(blackCrossFaithPosition == faithPathLength - 1)
        	endReached = true;

		int numSection = 0;
		for (VaticanReportSection section : vaticanReportSections) {
			if (blackCrossFaithPosition >= section.getPopeSpacePos()) {
				vaticanReport = true;
				Player singlePlayer =  popeFavorCards.keySet().iterator().next();
				turnPopeFavorCard(singlePlayer, section, numSection);
			}
			numSection++;
		}
		return new FaithPathEvent(endReached, vaticanReport);
	}

	@Override
	public ServerFaithPathSinglePlayerRepresentation getServerRepresentation() {
		return new ServerFaithPathSinglePlayerRepresentation(
			faithPathLength,
			vaticanReportSections.stream().map(VaticanReportSection::getServerRepresentation).collect(Collectors.toList()),
			victoryPointsByPosition,
			faithPositions.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)),
			popeFavorCards.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)),
			victoryPoints.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)),
			blackCrossFaithPosition
		);
	}

	@Override
	public ServerFaithPathSinglePlayerRepresentation getServerRepresentationForPlayer(Player player) {
		return getServerRepresentation();
	}

}
