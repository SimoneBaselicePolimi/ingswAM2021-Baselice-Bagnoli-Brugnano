package it.polimi.ingsw.server.model.observableproxy;

import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;
import it.polimi.ingsw.server.model.gamemanager.GameManager;

public abstract class ObservableProxyForIdentifiableItem<I extends IdentifiableItem> extends ObservableProxy<I> implements IdentifiableItem {

    public ObservableProxyForIdentifiableItem(I imp, GameManager gameManager) {
        super(imp, gameManager);
        overrideGameItemsManagerRegistration();
    }

    protected void overrideGameItemsManagerRegistration() {
        gameManager.getGameItemsManager().addItem(
            this, (Class<IdentifiableItem>) getClass().getInterfaces()[0]
        );
    }

}
