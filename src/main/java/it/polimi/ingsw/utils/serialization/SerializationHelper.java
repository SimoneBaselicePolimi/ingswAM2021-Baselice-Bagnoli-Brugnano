package it.polimi.ingsw.utils.serialization;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SerializationHelper {

    protected static ObjectMapper yamlObjectMapper;

    protected static ObjectMapper getObjectMapper() {
        if(yamlObjectMapper == null) {
            yamlObjectMapper = new ObjectMapper(new YAMLFactory());
            yamlObjectMapper.configOverride(List.class).setSetterInfo(JsonSetter.Value.forValueNulls(Nulls.AS_EMPTY));

        }
        return yamlObjectMapper;
    }

    private SerializationHelper() {}

    public static String serializeAsYaml(Object obj) throws JsonProcessingException {
        return getObjectMapper().writeValueAsString(obj);
    }

    protected static <T> ObjectReader getReaderFor(GameItemsManager gameItemsManager, Class<T> objType) {
        //readerFor() returns the ObjectReader that can deserialize an object of type objType.
        return getObjectMapper().readerFor(objType)
            // we need to pass the gameItemManager to our custom deserializer so that it can retrieve the object
            // reference using the ID.
            .withAttribute("GameItemManager", gameItemsManager);
    }

    public static <T> T deserializeYamlAsObject(
        GameItemsManager gameItemsManager,
        String serializedObj,
        Class<T> objType
    ) throws JsonProcessingException {
        return getReaderFor(gameItemsManager, objType).readValue(serializedObj);
    }

    public static <T> T deserializeYamlAsObject(
        GameItemsManager gameItemsManager,
        InputStream serializedObj,
        Class<T> objType
    ) throws IOException {
        return getReaderFor(gameItemsManager, objType).readValue(serializedObj);
    }

    public static <T> T deserializeYamlAsObject(
        String serializedObj,
        Class<T> objType
    ) throws JsonProcessingException {
        return getObjectMapper().readValue(serializedObj, objType);
    }

    public static <T> T deserializeYamlAsObject(
        InputStream serializedObj,
        Class<T> objType
    ) throws IOException {

        return getObjectMapper().readValue(serializedObj, objType);
    }

}
