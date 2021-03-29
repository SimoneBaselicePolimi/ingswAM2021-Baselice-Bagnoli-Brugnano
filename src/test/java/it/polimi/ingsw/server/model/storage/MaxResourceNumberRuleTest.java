package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.gameitems.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test that verifies that the rule "there must be a maximum number of resources in the storage" is respected.
 * Throw NotEnoughResourcesException if the resources you want to add don't respect this rule.
 */
class MaxResourceNumberRuleTest {
    /**
     * Test with Max Resource Number = 0
     */
    @Test
    void testCheckRuleLimit0() {
        ResourceStorage storage = ResourceStorageBuilder.initResourceStorageBuilder("s")
                .addRule(new MaxResourceNumberRule(0))
                .createResourceStorage();
        assertThrows(
                ResourceStorageRuleViolationException.class,
                () -> storage.addResources(Map.of(ResourceType.COINS, 1))
        );
    }

    /**
     * Test with Max Resource Number = 3
     */
    @Test
    void testCheckRuleLimit3() throws NotEnoughResourcesException {
        ResourceStorage storage = ResourceStorageBuilder.initResourceStorageBuilder("s")
                .addRule(new MaxResourceNumberRule(3))
                .createResourceStorage();
        assertThrows(
                ResourceStorageRuleViolationException.class,
                () -> storage.addResources(Map.of(ResourceType.COINS, 4))
        );
        assertDoesNotThrow(
            () -> storage.addResources(Map.of(ResourceType.COINS, 3))
        );
        storage.removeResources(Map.of(ResourceType.COINS, 1));
        assertDoesNotThrow(
                () -> storage.addResources(Map.of(ResourceType.STONES, 1))
        );
        storage.removeResources(Map.of(ResourceType.COINS, 2));
        assertDoesNotThrow(
                () -> storage.addResources(Map.of(
                        ResourceType.SHIELDS, 1,
                        ResourceType.SERVANTS, 1
                ))
        );
        assertThrows(
                ResourceStorageRuleViolationException.class,
                () -> storage.addResources(Map.of(ResourceType.COINS, 1))
        );
    }
}