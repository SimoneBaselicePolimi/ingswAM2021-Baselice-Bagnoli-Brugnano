package it.polimi.ingsw.configfile;

import it.polimi.ingsw.server.model.gameitems.MarbleColour;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.utils.serialization.SerializeIdOnly;

import java.util.List;

public class MarblesConfig {

    public final List<MarbleTypes> marbleTypes;

    public MarblesConfig(List<MarbleTypes> marbleTypes) {
        this.marbleTypes = marbleTypes;
    }

    class MarbleTypes {

        @SerializeIdOnly
        public final MarbleColour marbleID;

        public final ResourceType resourceType;

        public final int numberOfFaithPoints;

        public final boolean isSpecial;

        public MarbleTypes(MarbleColour marbleID, ResourceType resourceType, int numberOfFaithPoints, boolean isSpecial) {
            this.marbleID = marbleID;
            this.resourceType = resourceType;
            this.numberOfFaithPoints = numberOfFaithPoints;
            this.isSpecial = isSpecial;
        }
    }
}