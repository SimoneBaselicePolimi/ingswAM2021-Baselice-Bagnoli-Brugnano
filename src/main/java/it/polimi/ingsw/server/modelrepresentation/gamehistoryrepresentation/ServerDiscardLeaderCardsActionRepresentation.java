package it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation;

import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ServerLeaderCardRepresentation;

public class ServerDiscardLeaderCardsActionRepresentation extends ServerGameActionRepresentation {
    public final ServerPlayerRepresentation player;
    public final ServerLeaderCardRepresentation leaderCard;

    public ServerDiscardLeaderCardsActionRepresentation(
        ServerPlayerRepresentation player,
        ServerLeaderCardRepresentation leaderCard
    ) {
        this.player = player;
        this.leaderCard = leaderCard;
    }

}
