package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the compliance with the rule "there must be a maximum number of resources in the storage".
 * Throw NotEnoughResourcesException if the resources you want to add don't respect this rule.
 */
@ExtendWith(MockitoExtension.class)
class MaxResourceNumberRuleTest {

    @Mock
    GameItemsManager gameItemsManager;

    /**
     * Test with Max Resource Number = 0
     */
    @Test
    void testCheckRuleLimit0() {
        ResourceStorage storage = ResourceStorageBuilder.initResourceStorageBuilder(gameItemsManager)
                .addRule(new MaxResourceNumberRule(0))
                .createResourceStorage("s");
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
        ResourceStorage storage = ResourceStorageBuilder.initResourceStorageBuilder(gameItemsManager)
                .addRule(new MaxResourceNumberRule(3))
                .createResourceStorage("s");
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