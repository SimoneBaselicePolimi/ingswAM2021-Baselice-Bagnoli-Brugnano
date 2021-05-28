package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.modelrepresentation.ClientRepresentation;

import java.util.ArrayList;
import java.util.List;

public abstract class View {

    protected boolean isVisible = true;
    protected List<ClientRepresentation> subscribedRepresentations = new ArrayList<>();

    public abstract void updateView();

    protected void subscribeToRepresentation(ClientRepresentation representation) {
        subscribedRepresentations.add(representation);
        representation.subscribe(this);
    }

    public void destroyView() {
        isVisible = false;
        subscribedRepresentations.forEach(r -> r.unsubscribe(this));
    }
}
