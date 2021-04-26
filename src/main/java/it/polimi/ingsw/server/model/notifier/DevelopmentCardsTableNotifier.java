package it.polimi.ingsw.server.model.notifier;

import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTable;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.notifier.gameupdate.GameUpdate;

import java.util.List;
import java.util.Set;

public class DevelopmentCardsTableNotifier extends DevelopmentCardsTable implements Notifier {

	public DevelopmentCardsTableNotifier(List<DevelopmentCard> cards) {
		super(cards);
	}

	public Set<GameUpdate> getUpdates() {
		return null;
	}

	public DevelopmentCard popCard(DevelopmentCardLevel level, DevelopmentCardColour colour) {
		return null;
	}

}
