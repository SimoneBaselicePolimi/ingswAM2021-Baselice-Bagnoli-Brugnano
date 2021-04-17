package it.polimi.ingsw.server.model.notifier;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.cardstack.PlayerOwnedDevelopmentCardDeck;
import it.polimi.ingsw.server.model.gameitems.leadercard.LeaderCard;
import it.polimi.ingsw.server.model.notifier.gameupdate.GameUpdate;
import it.polimi.ingsw.server.model.storage.ResourceStorage;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PlayerContextNotifier extends PlayerContext implements Notifier {

    protected PlayerContextNotifier(Player player, Set<ResourceStorage> shelves, List<PlayerOwnedDevelopmentCardDeck> decks, ResourceStorage infiniteChest, ResourceStorage temporaryStorage) {
        super(player, shelves, decks, infiniteChest, temporaryStorage);
    }

    @Override
    public void setLeaderCards(Set<LeaderCard> cards) {

    }

    @Override
    public Set<GameUpdate> getUpdates() {
        return null;
    }
}
