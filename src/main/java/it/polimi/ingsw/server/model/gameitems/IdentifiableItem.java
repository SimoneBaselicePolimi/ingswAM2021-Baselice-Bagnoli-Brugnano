package it.polimi.ingsw.server.model.gameitems;

/**
 * This interface should be implemented by all the game items that can be identified using a unique Id.
 * This is particularly useful when we need to refer to an item in a data structure that will be serialized.
 */
public interface IdentifiableItem {
    String getItemId();
}
