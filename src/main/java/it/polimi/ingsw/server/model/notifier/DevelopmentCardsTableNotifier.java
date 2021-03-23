package it.polimi.ingsw.server.model.notifier;

import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTable;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.notifier.gameupdate.DevelopmentCardsTableUpdate;

import java.util.List;
import java.util.Optional;

public class DevelopmentCardsTableNotifier extends DevelopmentCardsTable implements Notifier<DevelopmentCardsTableUpdate> {

	public DevelopmentCardsTableNotifier(List<DevelopmentCard> cards) {
		super(cards);
	}

	public Optional<DevelopmentCardsTableUpdate> getUpdate() {
		return null;
	}

	public DevelopmentCard popCard(DevelopmentCardLevel level, DevelopmentCardColour colour) {
		return null;
	}

}
