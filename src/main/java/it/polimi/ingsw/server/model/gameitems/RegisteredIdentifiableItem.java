package it.polimi.ingsw.server.model.gameitems;


public abstract class RegisteredIdentifiableItem implements IdentifiableItem {

    protected final String itemID;

    protected RegisteredIdentifiableItem(String itemID, GameItemsManager gameItemsManager) {
        this.itemID = itemID;
        gameItemsManager.addItem(this);
    }

    @Override
    public String getItemId() {
        return itemID;
    }

}
