package it.polimi.ingsw.server.model.gameitems.leadercard;

import it.polimi.ingsw.server.model.Representable;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.DevelopmentCardCostDiscount;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.WhiteMarbleSubstitution;
import it.polimi.ingsw.server.model.storage.ResourceStorage;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ServerLeaderCardRepresentation;

import java.util.Set;

public interface LeaderCard extends IdentifiableItem, Representable<ServerLeaderCardRepresentation> {
    /**
     * Method to verify that the player has the necessary requirements to activate the leader card
     * @param playerContext reference to the single player
     * @return true if the player satisfies all requirements of the leader card
     */
    boolean areRequirementsSatisfied(PlayerContext playerContext);

    /**
     * Method to change the state of the leader card from HIDDEN to ACTIVE
     * (this method should be called when the player wants to activate the card)
     * @param playerContext reference to the single player
     * @throws LeaderCardRequirementsNotSatisfiedException if the leader card of the player
     * doesn't satisfy some requirements
     */
    void activateLeaderCard(PlayerContext playerContext) throws LeaderCardRequirementsNotSatisfiedException;

    /**
     * Method to change the state of the leader card from HIDDEN to DISCARDED
     * (the player no longer has that leader card in his hand)
     */
    void discardLeaderCard() throws LeaderCardRequirementsNotSatisfiedException;

    /**
     * Method to get the state of the leader card
     * @return the state of the leader card: ACTIVE, DISCARDED or HIDDEN
     */
    LeaderCardState getState();

    /**
     * Method to get the list of productions of the leader card
     * @return list of productions (special skills) that the leader card can own (it can be an empty list)
     */
    Set<Production> getProductions();

    /**
     * Method to get the list of resource storages of the leader card
     * @return list of resource storages (special skills) that the leader card can own (it can be an empty list)
     */
    Set<ResourceStorage> getResourceStorages();

    /**
     * Method to get the list of discounts of the leader card
     * @return list of discounts (special skills) that the leader card can own (it can be an empty list)
     */
    Set<DevelopmentCardCostDiscount> getDevelopmentCardCostDiscount();

    /**
     * Method to get the list of substitutions of the leader card
     * @return list of substitutions with white marbles (special skills) that the leader card can own (it can be an empty list)
     */
    Set<WhiteMarbleSubstitution> getWhiteMarbleSubstitutions();

    /**
     * Method to get the number of victory points of the leader card
     * @return number of victory points that the card gives at the end of the game
     */
    int getVictoryPoints();
}
