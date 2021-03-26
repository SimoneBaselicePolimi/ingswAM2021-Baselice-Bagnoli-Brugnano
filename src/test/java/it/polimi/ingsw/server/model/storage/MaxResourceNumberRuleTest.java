package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.gameitems.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MaxResourceNumberRuleTest {

    @Test
    void testCheckRuleLimit0() {
        ResourceStorage storage = ResourceStorageBuilder.initResourceStorageBuilder()
                .addRule(new MaxResourceNumberRule(0))
                .createResourceStorage();
        assertThrows(
                ResourceStorageRuleViolationException.class,
                () -> storage.addResources(Map.of(ResourceType.COINS, 1))
        );
    }

    @Test
    void testCheckRuleLimit3() throws NotEnoughResourcesException {
        ResourceStorage storage = ResourceStorageBuilder.initResourceStorageBuilder()
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