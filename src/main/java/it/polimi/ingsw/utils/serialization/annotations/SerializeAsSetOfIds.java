package it.polimi.ingsw.utils.serialization.annotations;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.polimi.ingsw.utils.serialization.deserializers.SetOfIdsDeserializer;
import it.polimi.ingsw.utils.serialization.serializers.SetOfIdsSerializer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Allows to serialize a set of type {@code Set<I extends IdentifiableItem>} as set of IDs instead of as whole objects
 * of type I
 */
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize(using = SetOfIdsSerializer.class)
@JsonDeserialize(using = SetOfIdsDeserializer.class)
public @interface SerializeAsSetOfIds {}
