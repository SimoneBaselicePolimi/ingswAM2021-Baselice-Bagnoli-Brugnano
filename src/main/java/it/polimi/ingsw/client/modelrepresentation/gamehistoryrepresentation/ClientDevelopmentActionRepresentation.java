package it.polimi.ingsw.client.modelrepresentation.gamehistoryrepresentation;

import it.polimi.ingsw.client.modelrepresentation.gameitemsrepresentation.developmentcardrepresentation.ClientDevelopmentCardRepresentation;
import it.polimi.ingsw.client.modelrepresentation.ClientPlayerRepresentation;

public class ClientDevelopmentActionRepresentation extends ClientGameActionRepresentation {

    private final ClientPlayerRepresentation player;
    private final ClientDevelopmentCardRepresentation developmentCard;

    public ClientDevelopmentActionRepresentation(ClientPlayerRepresentation player, ClientDevelopmentCardRepresentation developmentCard) {
        this.player = player;
        this.developmentCard = developmentCard;
    }

    public ClientPlayerRepresentation getPlayer() {
        return player;
    }

    public ClientDevelopmentCardRepresentation getDevelopmentCard() {
        return developmentCard;
    }
}
