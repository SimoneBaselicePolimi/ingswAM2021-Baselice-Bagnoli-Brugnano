package it.polimi.ingsw.client.gameupdate;

import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;

import java.util.Map;

public class ClientDevelopmentCardsTableUpdate extends ClientGameUpdate {

	public Map<DevelopmentCardLevel, Map<DevelopmentCardColour, DevelopmentCard>> developmentCardsOnTop;

}
