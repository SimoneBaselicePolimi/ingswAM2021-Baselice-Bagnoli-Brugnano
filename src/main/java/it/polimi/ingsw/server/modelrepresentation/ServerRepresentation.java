package it.polimi.ingsw.server.modelrepresentation;

import it.polimi.ingsw.client.view.View;

import java.util.HashSet;
import java.util.Set;

public abstract class ServerRepresentation {
    Set<View> subscribedView = new HashSet<>();

    public void subscribe(View view) {
        subscribedView.add(view);
    }

    public void unsubscribe(View view) {
        subscribedView.remove(view);
    }

    protected void notifyViews() {
        subscribedView.forEach(View::updateView);
    }

}
