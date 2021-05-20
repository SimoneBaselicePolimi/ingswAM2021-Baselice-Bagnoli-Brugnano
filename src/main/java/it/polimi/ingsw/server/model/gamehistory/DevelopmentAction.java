package it.polimi.ingsw.server.model.gamehistory;

import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerDevelopmentActionRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gamehistoryrepresentation.ServerGameActionRepresentation;

public class DevelopmentAction extends GameAction {
    private final Player player;
    private final DevelopmentCard developmentCard;

    public DevelopmentAction(Player player, DevelopmentCard developmentCard) {
        this.player = player;
        this.developmentCard = developmentCard;
    }

    @Override
    public String getActionMessage() {
        return Localization.getLocalizationInstance().getString(
            "gameState.mainTurn.developmentAction",
            player,
            developmentCard
        );
    }

    @Override
    public ServerGameActionRepresentation getServerRepresentation() {
        return new ServerDevelopmentActionRepresentation(
            player.getServerRepresentation(),
            developmentCard.getServerRepresentation()
        );
    }

    @Override
    public ServerGameActionRepresentation getServerRepresentationForPlayer(Player player) {
        return getServerRepresentation();
    }
}
