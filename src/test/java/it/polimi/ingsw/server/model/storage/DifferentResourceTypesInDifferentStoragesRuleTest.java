package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.gameitems.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DifferentResourceTypesInDifferentStoragesRuleTest {
    /**
     * Test that verifies that the rule "there must be different resources in different storages" is respected.
     * Throw NotEnoughResourcesException if the resources you want to add don't respect this rule.
     */
    @Test
    void testCheckRule() throws NotEnoughResourcesException {
        DifferentResourceTypesInDifferentStoragesRule rule = new DifferentResourceTypesInDifferentStoragesRule();
        ResourceStorage storage1 =
                ResourceStorageBuilder.initResourceStorageBuilder().addRule(rule).createResourceStorage("s1");
        ResourceStorage storage2 =
                ResourceStorageBuilder.initResourceStorageBuilder().addRule(rule).createResourceStorage("s2");
        assertDoesNotThrow(() -> storage1.addResources(Map.of(ResourceType.COINS, 3)));
        assertDoesNotThrow(() -> storage1.addResources(Map.of(ResourceType.SERVANTS, 2)));
        assertThrows(
                ResourceStorageRuleViolationException.class,
                () -> storage2.addResources(Map.of(ResourceType.SERVANTS, 5))
        );
        storage1.removeResources(Map.of(ResourceType.SERVANTS, 1));
        assertThrows(
                ResourceStorageRuleViolationException.class,
                () -> storage2.addResources(Map.of(ResourceType.SERVANTS, 5))
        );
        storage1.removeResources(Map.of(ResourceType.SERVANTS, 1));
        assertDoesNotThrow(() -> storage2.addResources(Map.of(ResourceType.SERVANTS, 5)));
        assertThrows(
                ResourceStorageRuleViolationException.class,
                () -> storage1.addResources(Map.of(ResourceType.SERVANTS, 1))
        );
        ResourceStorage storage3 =
                ResourceStorageBuilder.initResourceStorageBuilder().addRule(rule).createResourceStorage("s3");
        assertThrows(
                ResourceStorageRuleViolationException.class,
                () -> storage3.addResources(Map.of(ResourceType.COINS, 4))
        );
        ResourceStorage storage4 = ResourceStorageBuilder.initResourceStorageBuilder()
                .addRule(new DifferentResourceTypesInDifferentStoragesRule())
                .createResourceStorage("s4");
        assertDoesNotThrow(() -> storage4.addResources(Map.of(ResourceType.COINS, 4)));
    }
}