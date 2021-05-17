package it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation;

import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;

public class ServerDiscardLeaderCardsActionRepresentation extends ServerGameActionRepresentation {
    public final ServerPlayerRepresentation player;
    public final ServerGameActionRepresentation leaderCard;

    public ServerDiscardLeaderCardsActionRepresentation(
        ServerPlayerRepresentation player,
        ServerGameActionRepresentation leaderCard
    ) {
        this.player = player;
        this.leaderCard = leaderCard;
    }

}
