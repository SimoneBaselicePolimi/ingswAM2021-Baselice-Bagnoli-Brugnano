package it.polimi.ingsw.configfile;

import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardColour;
import it.polimi.ingsw.server.model.gameitems.developmentcard.DevelopmentCardLevel;
import it.polimi.ingsw.utils.serialization.SerializeIdOnly;

import java.util.List;
import java.util.Map;

public class DevelopmentCardsConfig {

    @SerializeIdOnly
    public final DevelopmentCard developmentCardID;

    public final DevelopmentCardLevel level;

    public final DevelopmentCardColour colour;

    public final List<ProductionConfig> productions;

    public final int victoryPoints;

    public final Map<ResourceType, Integer> purchaseCost;

    public DevelopmentCardsConfig(DevelopmentCard developmentCardID, DevelopmentCardLevel level, DevelopmentCardColour colour,
                                  List<ProductionConfig> productions, int victoryPoints, Map<ResourceType, Integer> purchaseCost) {
        this.developmentCardID = developmentCardID;
        this.level = level;
        this.colour = colour;
        this.productions = productions;
        this.victoryPoints = victoryPoints;
        this.purchaseCost = purchaseCost;
    }

}
