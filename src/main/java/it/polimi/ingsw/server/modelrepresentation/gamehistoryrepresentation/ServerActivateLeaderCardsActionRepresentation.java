package it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation;

import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;

public class ServerActivateLeaderCardsActionRepresentation extends ServerGameActionRepresentation {

    public final ServerPlayerRepresentation player;
    public final ServerGameActionRepresentation leaderCard;

    public ServerActivateLeaderCardsActionRepresentation(
        ServerPlayerRepresentation player,
        ServerGameActionRepresentation leaderCard
    ) {
        this.player = player;
        this.leaderCard = leaderCard;
    }
}
