package it.polimi.ingsw.server.model.notifier;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardsTableImp;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.notifier.gameupdate.ServerGameUpdate;

import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

public class DevelopmentCardsTableNotifier extends DevelopmentCardsTableImp implements Notifier {

	public DevelopmentCardsTableNotifier(
		List<DevelopmentCard> cards,
		GameItemsManager gameItemsManager,
		BiFunction<DevelopmentCardColour, DevelopmentCardLevel, String> getIdForDeckWithColourAndLevel
	) {
		super(cards, gameItemsManager, getIdForDeckWithColourAndLevel);
	}

	public Set<ServerGameUpdate> getUpdates() {
		return null;
	}

	public DevelopmentCard popCard(DevelopmentCardLevel level, DevelopmentCardColour colour) {
		return null;
	}

}
