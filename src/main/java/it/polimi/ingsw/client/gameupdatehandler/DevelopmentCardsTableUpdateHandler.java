package it.polimi.ingsw.client.gameupdatehandler;

import it.polimi.ingsw.client.gameupdate.ClientDevelopmentCardsTableUpdate;
import it.polimi.ingsw.client.model.DevelopmentCardsTableRepresentation;
import it.polimi.ingsw.client.model.GameContextRepresentation;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;

public class DevelopmentCardsTableUpdateHandler extends GameUpdateHandler<ClientDevelopmentCardsTableUpdate> {

    @Override
    public void handleGameUpdate(ClientDevelopmentCardsTableUpdate update, GameContextRepresentation gameContextRepresentation) {

        // TODO: INUTILE SE METTIAMO SOLO LE CARDS ON TOP NELLA DevelopmentCardsTableRepresentation
        DevelopmentCardsTableRepresentation developmentCardsTableRepresentation = gameContextRepresentation.getDevelopmentCardsTable();
        for(DevelopmentCardLevel level : DevelopmentCardLevel.values())
            for(DevelopmentCardColour colour : DevelopmentCardColour.values()) {
                if (!developmentCardsTableRepresentation.getCards().get(level).get(colour).peek().equals(update.developmentCardsOnTop.get(level).get(colour)))
                    developmentCardsTableRepresentation.getCards().get(level).get(colour).pop();
            }
    }
}
