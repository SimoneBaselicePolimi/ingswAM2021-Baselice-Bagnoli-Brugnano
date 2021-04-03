package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.gameitems.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test that verifies that the rule "there must be same type of resources in the storage" is respected.
 * Throw NotEnoughResourcesException if the resources you want to add don't respect this rule.
 */
class SameResourceTypeRuleTest {

    @Test
    void testCheckRule() throws NotEnoughResourcesException {
        ResourceStorage storage = ResourceStorageBuilder.initResourceStorageBuilder()
                .addRule(new SameResourceTypeRule())
                .createResourceStorage("s");
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