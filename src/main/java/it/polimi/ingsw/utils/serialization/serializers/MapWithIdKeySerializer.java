package it.polimi.ingsw.utils.serialization.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.ser.std.CollectionSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class MapWithIdKeySerializer<K extends IdentifiableItem, V> extends JsonSerializer<Map<K, V>> {

    @Override
    public void serialize(Map<K, V> kvMap, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
        throws IOException {
        Map<String, V> mapWithIdKey = kvMap.entrySet().stream().collect(Collectors.toMap(
            (e) -> e.getKey().getItemId(),
            Map.Entry::getValue)
        );
        jsonGenerator.writeObject(mapWithIdKey);
    }

}
