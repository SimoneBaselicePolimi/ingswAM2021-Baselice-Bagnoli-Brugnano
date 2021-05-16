package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.client.gameupdatehandler.DevelopmentCardsTableUpdateHandler;
import it.polimi.ingsw.client.gameupdatehandler.GameUpdateHandler;
import it.polimi.ingsw.client.model.DevelopmentCardRepresentation;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;

import java.util.Map;

public class ClientDevelopmentCardsTableUpdate extends ClientGameUpdate {

	public Map<DevelopmentCardLevel, Map<DevelopmentCardColour, DevelopmentCardRepresentation>> developmentCardsOnTop;

	@Override
	public GameUpdateHandler getHandler() {
		return new DevelopmentCardsTableUpdateHandler();
	}
}
