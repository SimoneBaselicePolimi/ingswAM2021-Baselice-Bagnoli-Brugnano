package it.polimi.ingsw.model.notifier;

import it.polimi.ingsw.model.gameitems.developmentcard.DevelopmentCardsTable;
import it.polimi.ingsw.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.model.gameitems.developmentcard.DevelopmentCardColour;

public class DevelopmentCardsTableNotifier extends DevelopmentCardsTable implements Notifier {

	public Option<DevelopmentCardsTableUpdate> getUpdate() {
		return null;
	}

	public DevelopmentCard popCard(DevelopmentCardLevel level, DevelopmentCardColour colour) {
		return null;
	}


	/**
	 * @see Notifier#getUpdate()
	 */
	public Optional<GameUpdate> getUpdate() {
		return null;
	}

}
