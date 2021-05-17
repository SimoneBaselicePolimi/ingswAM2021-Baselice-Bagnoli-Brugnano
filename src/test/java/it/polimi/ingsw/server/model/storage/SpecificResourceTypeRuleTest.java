package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SpecificResourceTypeRuleTest {

    @Mock
    GameItemsManager gameItemsManager;

    /**
     * Tests the compliance with the rule "there must be only one specific type of resource in the storage".
     * Throw NotEnoughResourcesException if the resources you want to add don't respect this rule.
     */
    @Test
    void testCheckRule() {
        ResourceStorage storage = ResourceStorageBuilder.initResourceStorageBuilder(gameItemsManager)
                .addRule(new SpecificResourceTypeRule(ResourceType.COINS))
                .createResourceStorage("s");
        assertThrows(
                ResourceStorageRuleViolationException.class,
                () -> storage.addResources(Map.of(
                        ResourceType.COINS, 4,
                        ResourceType.SHIELDS, 2
                ))
        );
        assertDoesNotThrow(() -> storage.addResources(Map.of(ResourceType.COINS, 4)));
    }
}