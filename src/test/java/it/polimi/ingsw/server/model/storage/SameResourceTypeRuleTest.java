package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.gameitems.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SameResourceTypeRuleTest {

    @Test
    void testCheckRule() throws NotEnoughResourcesException {
        ResourceStorage storage = ResourceStorageBuilder.initResourceStorageBuilder("s")
                .addRule(new SameResourceTypeRule())
                .createResourceStorage();
        assertThrows(
                ResourceStorageRuleViolationException.class,
                () -> storage.addResources(Map.of(
                        ResourceType.COINS, 4,
                        ResourceType.SHIELDS, 2
                ))
        );
        assertDoesNotThrow(() -> storage.addResources(Map.of(ResourceType.COINS, 4)));
        assertThrows(
                ResourceStorageRuleViolationException.class,
                () -> storage.addResources(Map.of(
                        ResourceType.COINS, 4,
                        ResourceType.SHIELDS, 2
                ))
        );
        assertDoesNotThrow(() -> storage.addResources(Map.of(ResourceType.COINS, 1)));
        storage.removeResources(Map.of(ResourceType.COINS, 5));
        assertDoesNotThrow(() -> storage.addResources(Map.of(ResourceType.SHIELDS, 2)));
    }
}