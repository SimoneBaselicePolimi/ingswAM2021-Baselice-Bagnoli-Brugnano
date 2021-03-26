package it.polimi.ingsw.server.model.notifier.gameupdate;


import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.faith.PopeFavorCardState;

import java.util.List;
import java.util.Map;

public class FaithAndPopeCardsUpdate extends FaithUpdate {

	public Map<Player, List<PopeFavorCardState>> faithPopeCards;

}
