package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.modelrepresentation.ServerRepresentation;

public interface Representable<R extends ServerRepresentation> {
    R getServerRepresentation();

    R getServerRepresentationForPlayer(Player player);
}
