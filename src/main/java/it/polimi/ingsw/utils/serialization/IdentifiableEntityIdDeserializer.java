package it.polimi.ingsw.utils.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;

import java.io.IOException;

public class IdentifiableEntityIdDeserializer<T extends IdentifiableItem> extends JsonDeserializer<T> implements ContextualDeserializer {

    private Class<T> typeOfItemToDeserialize;

    public IdentifiableEntityIdDeserializer() {}

    private IdentifiableEntityIdDeserializer(Class<T> typeOfItemToDeserialize) {
        this.typeOfItemToDeserialize = typeOfItemToDeserialize;
    }

    @SuppressWarnings("unchecked")
    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
        return new IdentifiableEntityIdDeserializer<>( (Class<T>) beanProperty.getType().getRawClass() );
    }

    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        GameItemsManager manager = (GameItemsManager) deserializationContext.getAttribute("GameItemManager");
        return manager.getItem(typeOfItemToDeserialize, jsonParser.getValueAsString());
    }

}
