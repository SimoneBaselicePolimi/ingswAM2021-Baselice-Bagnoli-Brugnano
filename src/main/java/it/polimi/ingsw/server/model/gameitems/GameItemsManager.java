package it.polimi.ingsw.server.model.gameitems;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The GameItemsManager contains a reference to every IdentifiableItem in the game (see {@link IdentifiableItem}).
 * It allows to retrieve a game item knowing only the ID and the type. It is also possible to get all the items of the
 * specified type (e.g. get all the LeaderCards).
 * <p>
 * The GameItemsManager is particularly useful in handling IDs while deserializing configuration files or server-client
 * messages.
 */
public class GameItemsManager {

    protected Map<Class<IdentifiableItem>, Map<String, IdentifiableItem>> items = new HashMap<>();

    /**
     * Returns all the game items of the specified type,
     *
     * @param itemType the item class
     * @param <T>      the item type
     * @return         a set containing all the items of the given type. If no items of the specified type have been
     *                 registered with the GameItemsManager an empty set will be returned
     */
    public <T extends IdentifiableItem> Set<T> getAllItemsOfType(Class<T> itemType) {
        return items.getOrDefault(itemType, new HashMap<>()).values().stream()
                .map(itemType::cast)
                .collect(Collectors.toSet());
    }

    /**
     * Retrieves the item with the specified ID of the specified type.
     *
     * @param itemType the class of the item to retrieve
     * @param id       the id of the item to retrieve
     * @param <T>      the item type
     * @return         the retrieved item
     * @throws IllegalArgumentException if no item of the specified type has the specified ID
     */
    public <T extends IdentifiableItem> T getItem(Class<T> itemType, String id) throws IllegalArgumentException {
        if(items.containsKey(itemType)) {
            if(items.get(itemType).containsKey(id))
                return itemType.cast(items.get(itemType).get(id));
            else
                throw new IllegalArgumentException(String.format("ID '%s' not found for item of type %s", id, itemType));
        } else {
            throw new IllegalArgumentException(String.format("Item type %s not found", itemType));
        }
    }

    /**
     * Registers an item with the GameItemsManager. Should always be called when initializing an instance of a class
     * that implements IdentifiableItem (see {@link IdentifiableItem})
     * @param item the item to add (the type will be inferred automatically)
     */
    @SuppressWarnings("unchecked")
    public void addItem(IdentifiableItem item) {
        items.computeIfAbsent( (Class<IdentifiableItem>) item.getClass(), (k) -> new HashMap<>())
            .put(item.getItemId(), item);
    }

    /**
     * Registers an item with the GameItemsManager. Should always be called when initializing an instance of a class
     * that implements IdentifiableItem (see {@link IdentifiableItem})
     * @param item the item to add
     * @param itemType type that should be used to register the item
     */
    @SuppressWarnings("unchecked")
    public <T extends IdentifiableItem> void addItem(T item, Class<T> itemType) {
        items.computeIfAbsent( (Class<IdentifiableItem>) itemType, (k) -> new HashMap<>())
            .put(item.getItemId(), item);
    }

}
