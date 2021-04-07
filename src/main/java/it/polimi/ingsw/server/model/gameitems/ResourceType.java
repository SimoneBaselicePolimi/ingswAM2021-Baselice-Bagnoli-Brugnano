package it.polimi.ingsw.server.model.gameitems;

/**
 * This enumeration lists all the Resource types used in the Game.
 */
public enum ResourceType {
    COINS("COINS"),
    STONES("STONES"),
    SERVANTS("SERVANTS"),
    SHIELDS("SHIELDS");

    String serializationName;

    ResourceType(String serializationName) {
        this.serializationName = serializationName;
    }

    public String serialize() {
        return this.serializationName;
    }

    public static ResourceType deserialize(String serializationName) {
        for(ResourceType resourceType : ResourceType.values())
            if(resourceType.serializationName.equals(serializationName))
                return resourceType;
        throw new IllegalArgumentException("Deserialization failed: invalid serialization name");
    }

}