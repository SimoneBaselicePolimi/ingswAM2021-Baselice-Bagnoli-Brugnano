package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.gameitems.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SpecificResourceTypeRuleTest {

    @Test
    void testCheckRule() {
        ResourceStorage storage = ResourceStorageBuilder.initResourceStorageBuilder("s")
                .addRule(new SpecificResourceTypeRule(ResourceType.COINS))
                .createResourceStorage();
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