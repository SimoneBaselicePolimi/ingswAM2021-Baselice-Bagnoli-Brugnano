package it.polimi.ingsw.utils.serialization.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class MapWithIdKeyDeserializer<K extends IdentifiableItem, V>  extends JsonDeserializer<Map<K, V>>
    implements ContextualDeserializer {

    private Class<K> typeOfKey;
    private Class<V> typeOfValue;

    public MapWithIdKeyDeserializer() {}

    public MapWithIdKeyDeserializer(Class<K> typeOfKey, Class<V> typeOfValue) {
        this.typeOfKey = typeOfKey;
        this.typeOfValue = typeOfValue;
    }

    @SuppressWarnings("unchecked")
    @Override
    public JsonDeserializer<?> createContextual(
        DeserializationContext deserializationContext,
        BeanProperty beanProperty
    ) throws JsonMappingException {
        return new MapWithIdKeyDeserializer<>(
            (Class<K>) beanProperty.getType().containedType(0).getRawClass(),
            (Class<V>) beanProperty.getType().containedType(1).getRawClass()
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<K, V> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException {
        GameItemsManager manager = (GameItemsManager) deserializationContext.getAttribute("GameItemManager");
        Map<String, V> mapWithIdKey = jsonParser.readValueAs(Map.class);
        return mapWithIdKey.entrySet().stream().collect(Collectors.toMap(
            (e) -> manager.getItem(typeOfKey, e.getKey()),
            Map.Entry::getValue
        ));
    }

}
