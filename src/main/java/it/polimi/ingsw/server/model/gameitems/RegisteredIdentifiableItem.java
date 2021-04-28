package it.polimi.ingsw.server.model.gameitems;

/**
 * This class permits the registration of all the Items identified by a unique ID.
 * It contains the method to get the ID of an Item and the override implementation of equals() and hashCode() methods.
 */
public abstract class RegisteredIdentifiableItem implements IdentifiableItem {

    /**
     * Unique ID that identifies this Item
     */
    protected final String itemID;

    /**
     * Class constructor.
     * @param itemID unique ID that identifies this Item
     * @param gameItemsManager a reference to gameItemsManager is needed to register this new Item
     */
    protected RegisteredIdentifiableItem(String itemID, GameItemsManager gameItemsManager) {
        this.itemID = itemID;
        gameItemsManager.addItem(this);
    }

    /**
     * Override of the getItemId method in IdentifiableItem interface.
     * @return ID of this Item
     */
    @Override
    public String getItemId() {
        return null;
    }

    /**
     * Override of the equals method used to compare the equality between two Items.
     * @param obj Object to compare to this Item
     * @return true if the Object passed as parameter is identified by the same ID of the Item
     * which invokes this method, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass())
            return false;
        return (this.itemID.equals(((RegisteredIdentifiableItem) obj).itemID));
    }

    /**
     * Override of the hashCode method used to return the hash code of this Item.
     * @return hash code of this Item based on its ID
     */
    @Override
    public int hashCode() {
        return itemID.hashCode();
    }

}
