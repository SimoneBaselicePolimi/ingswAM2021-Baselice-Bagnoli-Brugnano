package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.gameitems.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ResourceStorageTest {

    @Test
    void testResourceStorageWith2TrueRules() {
        ResourceStorage storage = ResourceStorageBuilder.initResourceStorageBuilder()
            .addRule(new TrueRule1())
            .addRule(new TrueRule2())
            .createResourceStorage();
        assertDoesNotThrow(() -> storage.addResources(Map.of(
            ResourceType.STONES, 1,
            ResourceType.SERVANTS, 4,
            ResourceType.SHIELDS, 0
        )));
    }

    @Test
    void testResourceStorageWith2TrueRulesAnd1False() {
        ResourceStorage storage = ResourceStorageBuilder.initResourceStorageBuilder()
            .addRule(new TrueRule1())
            .addRule(new FalseRule1())
            .addRule(new TrueRule2())
            .createResourceStorage();
        assertThrows(ResourceStorageRuleViolationException.class, () -> storage.addResources(Map.of(
            ResourceType.STONES, 1,
            ResourceType.SERVANTS, 4,
            ResourceType.SHIELDS, 0
        )));
    }

    @Test
    void testCanAddResources() throws ResourceStorageRuleViolationException {

        ResourceStorage storage = ResourceStorageBuilder.initResourceStorageBuilder().createResourceStorage();
        assertNotNull(storage);
        assertEquals(new HashMap<>(), storage.peekResources(), "The storage should be empty");

        Map<ResourceType, Integer> resources1 = Map.of(
            ResourceType.STONES, 2,
            ResourceType.COINS, 1
        );
        assertTrue(storage.canAddResources(resources1));
        storage.addResources(resources1);
        assertEquals(resources1, storage.peekResources());

        Map<ResourceType, Integer> resources2 = Map.of(
                ResourceType.SERVANTS, 2,
                ResourceType.COINS, 1
        );
        assertTrue(storage.canAddResources(resources2));
        storage.addResources(resources2);
        assertEquals(
            Map.of(
                ResourceType.STONES, 2,
                ResourceType.COINS, 2,
                ResourceType.SERVANTS, 2
            ),
            storage.peekResources()
        );

    }

    @Test
    void testAddResources() throws ResourceStorageRuleViolationException {
        ResourceStorage storage = ResourceStorageBuilder.initResourceStorageBuilder().createResourceStorage();
        assertNotNull(storage);
        assertEquals(new HashMap<>(), storage.peekResources(), "The storage should be empty");

        Map<ResourceType, Integer> resources1 = Map.of(
                ResourceType.STONES, 2,
                ResourceType.COINS, 1
        );
        storage.addResources(resources1);
        assertEquals(resources1, storage.peekResources());

        Map<ResourceType, Integer> resources2 = Map.of(
                ResourceType.SERVANTS, 2,
                ResourceType.COINS, 1
        );
        storage.addResources(resources2);
        assertEquals(
                Map.of(
                        ResourceType.STONES, 2,
                        ResourceType.COINS, 2,
                        ResourceType.SERVANTS, 2
                ),
                storage.peekResources()
        );

    }

    @Test
    void testAddAndRemoveResources() throws ResourceStorageRuleViolationException, NotEnoughResourcesException {

        ResourceStorage storage = ResourceStorageBuilder.initResourceStorageBuilder().createResourceStorage();
        assertTrue(storage.canAddResources(Map.of(
            ResourceType.STONES, 2,
            ResourceType.COINS, 1,
            ResourceType.SERVANTS, 3
        )));
        storage.addResources(Map.of(
                ResourceType.STONES, 2,
                ResourceType.COINS, 1,
                ResourceType.SERVANTS, 3
        ));
        assertTrue(storage.canRemoveResources(Map.of(
                ResourceType.SERVANTS, 1
        )));
        storage.removeResources(Map.of(
                ResourceType.SERVANTS, 1
        ));

        assertEquals(
            Map.of(
                ResourceType.STONES, 2,
                ResourceType.COINS, 1,
                ResourceType.SERVANTS, 2
            ),
            storage.peekResources()
        );
        assertTrue(storage.canRemoveResources(Map.of(
                ResourceType.SERVANTS, 2,
                ResourceType.COINS, 1
        )));
        storage.removeResources(Map.of(
            ResourceType.SERVANTS, 2,
            ResourceType.COINS, 1
        ));
        assertEquals(
            Map.of(
                    ResourceType.STONES, 2
            ),
            storage.peekResources()
        );
        assertTrue(storage.canRemoveResources(Map.of(ResourceType.STONES, 2)));
        storage.removeResources(Map.of(ResourceType.STONES, 2));
        assertEquals(new HashMap<>(), storage.peekResources());
        assertTrue(storage.canRemoveResources(Map.of()));
    }

    @Test
    void testTryToRemoveTooManyResources() throws ResourceStorageRuleViolationException {
        ResourceStorage storage = ResourceStorageBuilder.initResourceStorageBuilder().createResourceStorage();
        assertTrue(storage.canAddResources(Map.of(
                ResourceType.STONES, 2,
                ResourceType.COINS, 1,
                ResourceType.SERVANTS, 3
        )));
        storage.addResources(Map.of(
                ResourceType.STONES, 2,
                ResourceType.COINS, 1,
                ResourceType.SERVANTS, 3
        ));
        assertFalse(storage.canRemoveResources(Map.of(
                ResourceType.COINS, 1,
                ResourceType.SERVANTS, 4)));

        assertThrows(
            NotEnoughResourcesException.class,
            () -> storage.removeResources(Map.of(
                ResourceType.COINS, 1,
                ResourceType.SERVANTS, 4
            )),
            "It should not be possible to remove 4 SERVANTS from the storage because it has only 3 SERVANTS"
        );
    }

    class TrueRule1 extends ResourceStorageRule {

        @Override
        public boolean checkRule(ResourceStorage storage, Map<ResourceType, Integer> newResources) {
            return true;
        }

    }

    class TrueRule2 extends ResourceStorageRule {

        @Override
        public boolean checkRule(ResourceStorage storage, Map<ResourceType, Integer> newResources) {
            return true;
        }

    }

    class FalseRule1 extends ResourceStorageRule {

        @Override
        public boolean checkRule(ResourceStorage storage, Map<ResourceType, Integer> newResources) {
            return false;
        }

    }

}