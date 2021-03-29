package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.gameitems.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ResourceStorageTest {

    /**
     * Test the construction of a storage with 2 true rules
     */
    @Test
    void testResourceStorageWith2TrueRules() {
        ResourceStorage storage = ResourceStorageBuilder.initResourceStorageBuilder("s")
            .addRule(new TrueRule1())
            .addRule(new TrueRule2())
            .createResourceStorage();
        assertDoesNotThrow(() -> storage.addResources(Map.of(
            ResourceType.STONES, 1,
            ResourceType.SERVANTS, 4,
            ResourceType.SHIELDS, 0
        )));
    }

    /**
     * test the construction of a storage with 2 true rules and 1 false rule
     */
    @Test
    void testResourceStorageWith2TrueRulesAnd1False() {
        ResourceStorage storage = ResourceStorageBuilder.initResourceStorageBuilder("s")
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

    /**
     * Test canAddResources method and addResourcesMethod
     * If canAddResources return true, you can add that resources.
     * If canAddResources return false and you try to add resources, ResourceStorageRuleViolationException is thrown.
     */
    @Test
    void testCanAddResources() throws ResourceStorageRuleViolationException {

        ResourceStorage storage = ResourceStorageBuilder.initResourceStorageBuilder("s").createResourceStorage();
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

    /**
     * Test addResources method
     */
    @Test
    void testAddResources() throws ResourceStorageRuleViolationException {
        ResourceStorage storage = ResourceStorageBuilder.initResourceStorageBuilder("s").createResourceStorage();
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

    /**
     * Test addResources and removeResources methods
     */
    @Test
    void testAddAndRemoveResources() throws ResourceStorageRuleViolationException, NotEnoughResourcesException {

        ResourceStorage storage = ResourceStorageBuilder.initResourceStorageBuilder("s").createResourceStorage();
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

    /**
     * Test removeResources method and if there aren't enough resources it throws exception
     */
    @Test
    void testTryToRemoveTooManyResources() throws ResourceStorageRuleViolationException {
        ResourceStorage storage = ResourceStorageBuilder.initResourceStorageBuilder("s").createResourceStorage();
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

    /**
     * Tests the equality between two storages only checking the equality of their ID.
     */
    @Test
    void testEqualsID () {
        ResourceStorage storage1 = ResourceStorageBuilder.initResourceStorageBuilder("s1")
                .createResourceStorage();
        ResourceStorage storage2 = ResourceStorageBuilder.initResourceStorageBuilder("s2")
                .createResourceStorage();
        ResourceStorage storage3 = ResourceStorageBuilder.initResourceStorageBuilder("s3")
                .createResourceStorage();
        ResourceStorage storage4 = ResourceStorageBuilder.initResourceStorageBuilder("s1")
                .createResourceStorage();
        ResourceStorage storage5 = ResourceStorageBuilder.initResourceStorageBuilder("s1")
                .createResourceStorage();
        ResourceStorage storage6 = ResourceStorageBuilder.initResourceStorageBuilder("s6")
                .createResourceStorage();

        assertEquals(storage1, storage4);
        assertEquals(storage1, storage5);
        assertNotEquals(storage1, storage6);
        assertNotEquals(storage6, storage5);
        assertNotEquals(storage4, storage2);
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