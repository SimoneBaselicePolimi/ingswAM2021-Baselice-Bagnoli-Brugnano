package it.polimi.ingsw.client.model;

import it.polimi.ingsw.client.view.View;

import java.util.HashSet;
import java.util.Set;

public abstract class Representation<V extends View> {
    Set<V> subscribedView = new HashSet<>();

    public void subscribe(V view) {
        subscribedView.add(view);
    }

    public void unsubscribe(V view) {
        subscribedView.remove(view);
    }

    public void notifyViews() {
        subscribedView.forEach(v -> v.updateView(this));
    }

}
