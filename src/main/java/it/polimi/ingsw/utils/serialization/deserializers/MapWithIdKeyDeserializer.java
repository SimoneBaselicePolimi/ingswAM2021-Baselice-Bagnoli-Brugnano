package it.polimi.ingsw.utils.serialization.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MapWithIdKeyDeserializer<K extends IdentifiableItem, V>  extends JsonDeserializer<Map<K, V>>
    implements ContextualDeserializer {

    private Class<K> typeOfKey;
    private JavaType typeOfValue;

    public MapWithIdKeyDeserializer() {}

    public MapWithIdKeyDeserializer(Class<K> typeOfKey, JavaType typeOfValue) {
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
            beanProperty.getType().containedType(1)
        );
    }

    @Override
    public Map<K, V> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException {
        TypeFactory typeFactory = TypeFactory.defaultInstance();
        JavaType typeOfMapWithIdKey = typeFactory.constructMapType(
            HashMap.class,
            typeFactory.constructFromCanonical(String.class.getName()),
            typeOfValue
        );
        HashMap<String, V> mapWithIdKey = deserializationContext.readValue(jsonParser, typeOfMapWithIdKey);
        GameItemsManager manager = (GameItemsManager) deserializationContext.getAttribute("gameItemsManager");
        return mapWithIdKey.entrySet().stream().collect(Collectors.toMap(
            (e) -> manager.getItem(typeOfKey, e.getKey()),
            Map.Entry::getValue
        ));
    }

}
