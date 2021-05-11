package it.polimi.ingsw.server.model.gamehistory;

import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;

public class DevelopmentAction extends GameAction {
    private final Player player;
    private final DevelopmentCard developmentCard;

    public DevelopmentAction(Player player, DevelopmentCard developmentCard) {
        this.player = player;
        this.developmentCard = developmentCard;
    }

    @Override
    public String getActionMessage() {
        return Localization.getLocalization().getString(
            "gameState.mainTurn.developmentAction",
            player,
            developmentCard
        );
    }
}
