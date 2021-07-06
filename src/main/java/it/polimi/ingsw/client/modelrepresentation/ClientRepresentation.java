package it.polimi.ingsw.client.modelrepresentation;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.server.model.gamecontext.market.MarketImp;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents the client-side representation of the game.
 * The classes that extend this class represent a "snapshot" of the state of the game
 * and do not contain the game logic, which is only present in the server-side components.
 * Each representation contains a reference to one or more views.
 * The task of the representation is to notify all views registered in this class
 * of the changes that occur to the representation during the game.
 */
public abstract class ClientRepresentation {
    /**
     * Set of views subscribed to this representation
     */
    Set<View> subscribedView = new HashSet<>();

    /**
     * Method used to subscribe a view to a specific representation of the game.
     * Each view is subscribed to representations it needs in order to show to the user
     * the state of the game at a given time
     * @param view, see {@link View}
     */
    public void subscribe(View view) {
        subscribedView.add(view);
    }

    /**
     * Method used to subscribe a view to a specific representation of the game.
     * @param view, see {@link View}
     */
    public void unsubscribe(View view) {
        subscribedView.remove(view);
    }

    /**
     * Method used to notify all views subscribed to this representation:
     * when the representation of the game changes, subscribed views must be notified
     * in order to show the new updated game representation
     */
    protected void notifyViews() {
        subscribedView.forEach(View::updateView);
    }

}
