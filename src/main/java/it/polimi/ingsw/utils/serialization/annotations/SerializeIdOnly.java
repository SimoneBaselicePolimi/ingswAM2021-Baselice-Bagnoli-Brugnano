package it.polimi.ingsw.utils.serialization.annotations;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.polimi.ingsw.utils.serialization.deserializers.IdentifiableEntityIdDeserializer;
import it.polimi.ingsw.utils.serialization.serializers.IdentifiableEntityIdSerializer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Allows to serialize just the ID of a game item instead of all the properties of the object.
 * It also supports the deserialization of the game item (it will use the GameItemManager to obtain the object reference
 * from the serialized ID).
 * <p>
 * To make deserialization work you need to pass a key-value attribute to Jackson Reader with key "GameItemManager" and
 * the current gameItemManager as value. An example:
 * <pre>
 *  //DESERIALIZED_OBJECT_CLASS is a class with one or more attribute annotated with @SerializedIdOnly
 *  deserializedObject = mapper.readerFor(DESERIALIZED_OBJECT_CLASS.class).withAttribute("GameItemManager", gameItemsManager).readValue(s);
 * </pre>
 * <p>
 * Note: should only be applied to objects of type IdentifiableItem (see
 * {@link it.polimi.ingsw.server.model.gameitems.IdentifiableItem})
 * @see com.fasterxml.jackson.databind.ObjectReader
 */
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize(using = IdentifiableEntityIdSerializer.class)
@JsonDeserialize(using = IdentifiableEntityIdDeserializer.class)
public @interface SerializeIdOnly {}
