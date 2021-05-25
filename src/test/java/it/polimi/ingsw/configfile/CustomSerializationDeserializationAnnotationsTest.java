package it.polimi.ingsw.configfile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import it.polimi.ingsw.server.model.gameitems.GameItemsManager;
import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsMapWithIdKey;
import it.polimi.ingsw.utils.serialization.annotations.SerializeAsSetOfIds;
import it.polimi.ingsw.utils.serialization.annotations.SerializeIdOnly;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

class TestConfig {

    @SerializeIdOnly
    public final IdentifiableItemMock item;

    @SerializeAsSetOfIds
    public final Set<IdentifiableItemMock> itemsSet;

    @SerializeAsMapWithIdKey
    public final Map<IdentifiableItemMock, Integer> itemsToIntegerMap;

    @SerializeAsMapWithIdKey
    public final Map<IdentifiableItemMock, String> itemsToStringMap;

    public TestConfig(
        @JsonProperty("item") IdentifiableItemMock item,
        @JsonProperty("itemsSet") Set<IdentifiableItemMock> itemsSet,
        @JsonProperty("itemsToIntegerMap") Map<IdentifiableItemMock, Integer> itemsToIntegerMap,
        @JsonProperty("itemsToStringMap") Map<IdentifiableItemMock, String> itemsToStringMap
    ) {
        this.item = item;
        this.itemsSet = itemsSet;
        this.itemsToIntegerMap = itemsToIntegerMap;
        this.itemsToStringMap = itemsToStringMap;
    }

}

class CustomSerializationDeserializationAnnotationsTest {

    IdentifiableItemMock item1;
    IdentifiableItemMock item2;
    IdentifiableItemMock item3;

    TestConfig testConfig;

    GameItemsManager gameItemsManager;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

        gameItemsManager = new GameItemsManager();

        item1 = new IdentifiableItemMock("ID1", 123);
        gameItemsManager.addItem(item1);

        item2 = new IdentifiableItemMock("ID2", 456);
        gameItemsManager.addItem(item2);

        item3 = new IdentifiableItemMock("ID3", 789);
        gameItemsManager.addItem(item3);

        testConfig = new TestConfig(
            item1,
            Set.of(item1, item2, item3),
            Map.of(
                item1, 10,
                item2, 2
            ),
            Map.of(
                item1, "hello",
                item2, "magic",
                item3, "serializer"
            )
        );

        objectMapper = new ObjectMapper(new YAMLFactory());

    }

    @Test
    void testSerializeAndDeserialize() throws IOException {

        String serializedConfig = serializeTestConfig(testConfig);
        assertEqualsYAMLStrings(
            "item: \"ID1\"\n" +
            "itemsSet:\n" +
            "  - \"ID2\"\n" +
            "  - \"ID1\"\n" +
            "  - \"ID3\"\n" +
            "itemsToIntegerMap:\n" +
            "  ID2: 2\n" +
            "  ID1: 10\n" +
            "itemsToStringMap:\n" +
            "  ID2: \"magic\"\n" +
            "  ID1: \"hello\"\n" +
            "  ID3: \"serializer\"",
            serializedConfig
        );

        TestConfig deserializedTestConfig = deserializeTestConfig(serializedConfig);
        assertEquals(testConfig.item, deserializedTestConfig.item);
        assertEquals(testConfig.itemsSet, deserializedTestConfig.itemsSet);
        assertEquals(testConfig.itemsToIntegerMap, deserializedTestConfig.itemsToIntegerMap);
        assertEquals(testConfig.itemsToStringMap, deserializedTestConfig.itemsToStringMap);

    }

    String serializeTestConfig(TestConfig testConfig) throws JsonProcessingException {
        return objectMapper.writeValueAsString(testConfig);
    }

    TestConfig deserializeTestConfig(String serializedTestConfig) throws IOException {
        //readerFor() returns the ObjectReader that can deserialize a serialized TestConfig object.
        return objectMapper.readerFor(TestConfig.class)
            // we need to pass the gameItemManager to our custom deserializer so that it can retrieve the object
            // reference using the ID.
            .withAttribute("GameItemManager", gameItemsManager)
            // here we finally pass the serialized object and obtain the deserialized TestConfig instance
            .readValue(serializedTestConfig);
    }

    void assertEqualsYAMLStrings(String s1, String s2) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Map m1 = mapper.readValue(s1, Map.class);
        Map m2 = mapper.readValue(s2, Map.class);
        assertEquals(m1, m2);
    }

}

class IdentifiableItemMock implements IdentifiableItem {

    private final String id;
    private final int testProp1;

    IdentifiableItemMock(@JsonProperty("id") String id, @JsonProperty("testProp1") int testProp1) {
        this.id = id;
        this.testProp1 = testProp1;
    }

    @Override
    public String getItemID() {
        return id;
    }

}

