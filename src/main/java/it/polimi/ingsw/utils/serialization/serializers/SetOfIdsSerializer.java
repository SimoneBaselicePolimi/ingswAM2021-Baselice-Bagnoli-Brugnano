package it.polimi.ingsw.utils.serialization.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class SetOfIdsSerializer<I extends IdentifiableItem> extends JsonSerializer<Set<I>> {

    @Override
    public void serialize(Set<I> setOfItems, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
        throws IOException {
        Set<String> setOfIds = setOfItems.stream().map(IdentifiableItem::getItemID).collect(Collectors.toSet());
        jsonGenerator.writeObject(setOfIds);
    }

}
