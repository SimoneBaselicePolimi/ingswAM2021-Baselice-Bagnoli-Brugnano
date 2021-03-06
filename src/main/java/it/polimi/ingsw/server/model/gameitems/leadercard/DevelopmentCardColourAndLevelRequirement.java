package it.polimi.ingsw.server.model.gameitems.leadercard;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.gamecontext.playercontext.PlayerContext;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ServerDevelopmentCardColourAndLevelRequirementRepresentation;
import it.polimi.ingsw.server.modelrepresentation.gameitemsrepresentation.leadercardrepresentation.ServerLeaderCardRequirementRepresentation;

/**
 * This class represent the request for a specific number of development cards of a certain colour and level
 */
public class DevelopmentCardColourAndLevelRequirement extends LeaderCardRequirement {
    public final DevelopmentCardColour cardColour;
    public final DevelopmentCardLevel cardLevel;
    public final int numberOfCards;

    /**
     * DevelopmentCardColorAndLevelRequirement constructor
     * @param cardColour colour of development cards required
     * @param cardLevel level of development cards required
     * @param numberOfCards number of development cards required
     */
    public DevelopmentCardColourAndLevelRequirement(
            DevelopmentCardColour cardColour,
            DevelopmentCardLevel cardLevel,
            int numberOfCards
    ) {
        this.cardColour=cardColour;
        this.cardLevel=cardLevel;
        this.numberOfCards=numberOfCards;
    }

    /**
     * Method to verify that the player has the necessary development cards (with a specific colour and level)
     * to activate a leader card
     * @param playerContext reference to the single player
     * @return true if the player has a specific number of development cards of a certain color and level
     */
    @Override
    public boolean checkRequirement(PlayerContext playerContext) {
        int sumOfRightColourAndLevelCard = 0;
        for (DevelopmentCard developmentCard : playerContext.getAllDevelopmentCards()){
            if (developmentCard.getColour() == cardColour && developmentCard.getLevel() == cardLevel)
                sumOfRightColourAndLevelCard ++;
        }
        return sumOfRightColourAndLevelCard >= numberOfCards;
    }

    @Override
    public ServerLeaderCardRequirementRepresentation getServerRepresentation() {
        return new ServerDevelopmentCardColourAndLevelRequirementRepresentation(cardColour,cardLevel, numberOfCards);
    }

    @Override
    public ServerLeaderCardRequirementRepresentation getServerRepresentationForPlayer(Player player) {
        return getServerRepresentation();
    }
}
