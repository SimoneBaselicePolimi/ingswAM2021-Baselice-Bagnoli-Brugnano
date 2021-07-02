package it.polimi.ingsw.server.model.storage;

import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class) //Needed to use annotation @Mock
class ResourceStorageTest {

    @Mock
    GameItemsManager gameItemsManager;

    @Mock
    ResourceStorageRule trueRule1;

    @Mock
    ResourceStorageRule trueRule2;

    @Mock
    ResourceStorageRule falseRule1;

    @BeforeEach
    void setUp() {
        /*
        Note on lenient():
        lenient() is needed here because otherwise mockito would throw a UnnecessaryStubbingException. This exception is
        thrown when mockito thinks that your are stubbing a method without actually ever using it in any test.
        In the case of the 3 line below the false positive is caused by the fact that checkRule() is never called in any
        test directly (but it will be called, and thus those stubbing are needed, when calling
        ResourceStorage.addResources() )
        See https://github.com/mockito/mockito/blob/release/2.x/src/main/java/org/mockito/exceptions/misusing/UnnecessaryStubbingException.java
         */
        lenient().when(trueRule1.checkRule(any(), any())).thenReturn(true);
        lenient().when(trueRule2.checkRule(any(), any())).thenReturn(true);
        lenient().when(falseRule1.checkRule(any(), any())).thenReturn(false);
    }

    /**
     * Tests the construction of a ResourceStorage calling the builder and tests that ID is correct.
     */
    @Test
    void testBuilder(){
        ResourceStorage storage1 = ResourceStorageBuilder.initResourceStorageBuilder(gameItemsManager)
            .createResourceStorage("firstStorage");
        ResourceStorage storage2 = ResourceStorageBuilder.initResourceStorageBuilder(gameItemsManager)
            .createResourceStorage("secondStorage");

        assertEquals(storage1.getItemID(), "firstStorage");
        assertEquals(storage2.getItemID(), "secondStorage");
    }

    /**
     * Tests the construction of a storage with 2 true rules
     */
    @Test
    void testResourceStorageWith2TrueRules() {
        ResourceStorage storage = ResourceStorageBuilder.initResourceStorageBuilder(gameItemsManager)
            .addRule(trueRule1)
            .addRule(trueRule2)
            .createResourceStorage("s");
        Map<ResourceType, Integer> resourcesToAdd = Map.of(
                ResourceType.STONES, 1,
                ResourceType.SERVANTS, 4,
                ResourceType.SHIELDS, 0
        );
        assertDoesNotThrow(() -> storage.addResources(resourcesToAdd));
        //If ResourceStorage.addResources() succeeds, then the method checkRule() of every rule applied to the storage
        // should be called
        verify(trueRule1).checkRule(eq(storage), eq(resourcesToAdd));
        verify(trueRule2).checkRule(eq(storage), eq(resourcesToAdd));
    }

    /**
     * Tests the construction of a storage with 2 true rules and 1 false rule
     */
    @Test
    void testResourceStorageWith2TrueRulesAnd1False() {
        ResourceStorage storage = ResourceStorageBuilder.initResourceStorageBuilder(gameItemsManager)
            .addRule(trueRule1)
            .addRule(falseRule1)
            .addRule(trueRule2)
            .createResourceStorage("s");
        Map<ResourceType, Integer> resourcesToAdd = Map.of(
                ResourceType.STONES, 1,
                ResourceType.SERVANTS, 4,
                ResourceType.SHIELDS, 0
        );
        assertThrows(ResourceStorageRuleViolationException.class, () -> storage.addResources(resourcesToAdd));
        //If ResourceStorage.addResources() throws an exception, then falseRule1.checkRule() should have been called.
        // Note that, depending on the order used to check the rules, trueRule1.checkRule() and trueRule2.checkRule()
        // may or may not have been called.
        verify(falseRule1).checkRule(eq(storage), eq(resourcesToAdd));
    }

    /**
     * Tests canAddResources and addResources methods.
     * If canAddResources return true, you can add these resources to the storage.
     * If canAddResources return false and you try to add resources, ResourceStorageRuleViolationException is thrown.
     */
    @Test
    void testCanAddResources() throws ResourceStorageRuleViolationException {

        ResourceStorage storage = ResourceStorageBuilder.initResourceStorageBuilder(gameItemsManager).createResourceStorage("s");
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
     * Tests to add new resources to a storage calling addResources method
     */
    @Test
    void testAddResources() throws ResourceStorageRuleViolationException {
        ResourceStorage storage = ResourceStorageBuilder.initResourceStorageBuilder(gameItemsManager).createResourceStorage("s");
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
     * Tests to add new resources to a storage calling addResources method
     * Tests to remove resources from a storage calling removeResources method
     */
    @Test
    void testAddAndRemoveResources() throws ResourceStorageRuleViolationException, NotEnoughResourcesException {

        ResourceStorage storage = ResourceStorageBuilder.initResourceStorageBuilder(gameItemsManager).createResourceStorage("s");
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
     * Tests to remove resources from a storage calling removeResources method
     * and if there aren't enough resources it throws a ResourceStorageRuleViolationException.
     */
    @Test
    void testTryToRemoveTooManyResources() throws ResourceStorageRuleViolationException {
        ResourceStorage storage = ResourceStorageBuilder.initResourceStorageBuilder(gameItemsManager).createResourceStorage("s");
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
        ResourceStorage storage1 = ResourceStorageBuilder.initResourceStorageBuilder(gameItemsManager)
                .createResourceStorage("s1");
        ResourceStorage storage2 = ResourceStorageBuilder.initResourceStorageBuilder(gameItemsManager)
                .createResourceStorage("s2");
        ResourceStorage storage3 = ResourceStorageBuilder.initResourceStorageBuilder(gameItemsManager)
                .createResourceStorage("s3");
        ResourceStorage storage4 = ResourceStorageBuilder.initResourceStorageBuilder(gameItemsManager)
                .createResourceStorage("s1");
        ResourceStorage storage5 = ResourceStorageBuilder.initResourceStorageBuilder(gameItemsManager)
                .createResourceStorage("s1");
        ResourceStorage storage6 = ResourceStorageBuilder.initResourceStorageBuilder(gameItemsManager)
                .createResourceStorage("s6");

        assertEquals(storage1, storage4);
        assertEquals(storage1, storage5);
        assertNotEquals(storage3, storage6);
        assertNotEquals(storage6, storage5);
        assertNotEquals(storage4, storage2);
    }

}