package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.model.Representation;

public abstract class View<R extends Representation> {
    public abstract void updateView(R representation);
}
