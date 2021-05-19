package it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation;

import it.polimi.ingsw.server.modelrepresentation.ServerPlayerRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ServerDevelopmentCardRepresentation;

public class ServerDevelopmentActionRepresentation extends ServerGameActionRepresentation {

    public final ServerPlayerRepresentation player;
    public final ServerDevelopmentCardRepresentation developmentCard;

    public ServerDevelopmentActionRepresentation(
        ServerPlayerRepresentation player,
        ServerDevelopmentCardRepresentation developmentCard
    ) {
        this.player = player;
        this.developmentCard = developmentCard;
    }

}
