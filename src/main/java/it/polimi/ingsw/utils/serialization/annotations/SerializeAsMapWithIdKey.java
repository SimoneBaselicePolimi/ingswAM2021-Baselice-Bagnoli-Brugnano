package it.polimi.ingsw.utils.serialization.annotations;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.polimi.ingsw.utils.serialization.deserializers.MapWithIdKeyDeserializer;
import it.polimi.ingsw.utils.serialization.serializers.MapWithIdKeySerializer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Allows to serialize a map of type {@code Map<I extends IdentifiableItem, V>} using only the ID as key instead of the
 * whole object of type I
 */
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize(using = MapWithIdKeySerializer.class)
@JsonDeserialize(using = MapWithIdKeyDeserializer.class)
public @interface SerializeAsMapWithIdKey {}
