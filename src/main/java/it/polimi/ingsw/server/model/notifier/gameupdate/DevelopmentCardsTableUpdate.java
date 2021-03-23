package it.polimi.ingsw.server.model.notifier.gameupdate;

import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;

import java.util.Map;

public class DevelopmentCardsTableUpdate extends GameUpdate {

	public Map<DevelopmentCardLevel, Map<DevelopmentCardColour, DevelopmentCard>> developmentCardsOnTop;

}
