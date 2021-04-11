package it.polimi.ingsw.configfile;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class SetOfIdsDeserializer<I extends IdentifiableItem>  extends JsonDeserializer<Set<I>>
    implements ContextualDeserializer {

    private Class<I> typeOfItem;

    public SetOfIdsDeserializer() {}

    public SetOfIdsDeserializer(Class<I> typeOfItem) {
        this.typeOfItem = typeOfItem;
    }

    @SuppressWarnings("unchecked")
    @Override
    public JsonDeserializer<?> createContextual(
        DeserializationContext deserializationContext,
        BeanProperty beanProperty
    ) throws JsonMappingException {
        return new SetOfIdsDeserializer<>(
            (Class<I>) beanProperty.getType().containedType(0).getRawClass()
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<I> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException {
        GameItemsManager manager = (GameItemsManager) deserializationContext.getAttribute("GameItemManager");
        Set<String> setOfIds = jsonParser.readValueAs(Set.class);
        return setOfIds.stream().map(id -> manager.getItem(typeOfItem, id)).collect(Collectors.toSet());
    }

}
