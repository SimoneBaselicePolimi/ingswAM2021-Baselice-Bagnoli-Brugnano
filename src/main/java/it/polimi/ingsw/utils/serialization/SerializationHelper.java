package it.polimi.ingsw.utils.serialization;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import it.polimi.ingsw.server.controller.Client;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

public class SerializationHelper {

    protected static ObjectMapper yamlObjectMapper;

    protected static ObjectMapper getObjectMapper() {
        if(yamlObjectMapper == null) {
            YAMLFactory yamlFactory = new YAMLFactory();
            yamlFactory.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
            yamlObjectMapper = new ObjectMapper(yamlFactory);
            yamlObjectMapper.configOverride(List.class).setSetterInfo(JsonSetter.Value.forValueNulls(Nulls.AS_EMPTY));
        }
        return yamlObjectMapper;
    }

    private SerializationHelper() {}

    public static String serializeYamlAsString(Object obj) throws JsonProcessingException {
        return getObjectMapper().writeValueAsString(obj);
    }

    public static byte[] serializeYamlAsBytes(Object obj) throws JsonProcessingException {
        return getObjectMapper().writeValueAsBytes(obj);
    }

    protected static <T> ObjectReader getReaderFor(GameItemsManager gameItemsManager, Class<T> objType) {
        //readerFor() returns the ObjectReader that can deserialize an object of type objType.
        return getObjectMapper().readerFor(objType)
            // we need to pass the gameItemManager to our custom deserializer so that it can retrieve the object
            // reference using the ID.
            .withAttribute("GameItemManager", gameItemsManager);
    }

    public static <T> T deserializeYamlFromString(
        GameItemsManager gameItemsManager,
        String serializedObj,
        Class<T> objType
    ) throws JsonProcessingException {
        return getReaderFor(gameItemsManager, objType).readValue(serializedObj);
    }

    public static <T> T deserializeYamlFromInputStream(
        GameItemsManager gameItemsManager,
        InputStream serializedObj,
        Class<T> objType
    ) throws IOException {
        return getReaderFor(gameItemsManager, objType).readValue(serializedObj);
    }

    public static <T> T deserializeYamlFromString(
        String serializedObj,
        Class<T> objType
    ) throws JsonProcessingException {
        return getObjectMapper().readValue(serializedObj, objType);
    }

    public static <T> T deserializeYamlFromInputStream(
        InputStream serializedObj,
        Class<T> objType
    ) throws IOException {

        return getObjectMapper().readValue(serializedObj, objType);
    }

    public static <T> T deserializeYamlFromBytes(
        GameItemsManager gameItemsManager,
        byte[] serializedObj,
        Class<T> objType
    ) throws IOException {
        return getReaderFor(gameItemsManager, objType).readValue(serializedObj);
    }

    public static <T> T deserializeYamlFromBytes(
        byte[] serializedObj,
        Class<T> objType
    ) throws IOException {
        return getObjectMapper().readValue(serializedObj, objType);
    }

    public static <T> T deserializeYamlFromBytes(
        Client client,
        byte[] serializedObj,
        Class<T> objType
    ) throws IOException {
        InjectableValues.Std iv = new InjectableValues.Std();
        iv.addValue("client", client);
        ObjectMapper mapper = getObjectMapper();
        mapper.setInjectableValues(iv);
        return mapper.readValue(serializedObj, objType);
    }

}
