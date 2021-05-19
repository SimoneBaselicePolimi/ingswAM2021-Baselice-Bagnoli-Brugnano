package it.polimi.ingsw.server.model.gameitems.cardstack;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Representable;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;
import it.polimi.ingsw.server.modelrepresentation.ServerRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.cardstackrepresentation.ServerCoveredCardsDeckRepresentation;

public interface ShuffledCoveredCardDeck<RC extends ServerRepresentation, C extends Representable<RC>> extends IdentifiableItem, CardDeck<C>, Representable<ServerCoveredCardsDeckRepresentation<RC>> {
    @Override
    ServerCoveredCardsDeckRepresentation<RC> getServerRepresentation();

    @Override
    ServerCoveredCardsDeckRepresentation<RC> getServerRepresentationForPlayer(Player player);
}
