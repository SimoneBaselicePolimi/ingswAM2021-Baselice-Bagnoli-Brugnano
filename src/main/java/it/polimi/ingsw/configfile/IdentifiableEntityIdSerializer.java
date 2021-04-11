package it.polimi.ingsw.configfile;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;

import java.io.IOException;

public class IdentifiableEntityIdSerializer extends StdSerializer<IdentifiableItem> {

   public IdentifiableEntityIdSerializer() {
       super(IdentifiableItem.class);
   }

    @Override
    public void serialize(
            IdentifiableItem identifiableItem,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider
    ) throws IOException {
        jsonGenerator.writeString(identifiableItem.getItemId());
    }

}
