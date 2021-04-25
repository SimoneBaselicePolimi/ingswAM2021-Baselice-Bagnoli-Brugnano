package it.polimi.ingsw.utils.serialization.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;

import java.io.IOException;
import java.util.*;

public class MultiLevelWrappedPropertiesSerializer extends JsonSerializer<Object> {

    private NestedWrapper rootWrapper;

    public MultiLevelWrappedPropertiesSerializer(String topLevelWrapperName) {
        this.rootWrapper = new NestedWrapper(topLevelWrapperName);
    }

    /**
     * Wrap the specified property inside multiple wrappers. Wrappers that do not yet exist will be created.
     * @param propertyName the name of the property to add
     * @param propertyValue the value of the property to add
     * @param nestedWrappersHierarchyPath this path specifies inside which nested wrappers the property should be
     *                                    added. As an example if nestedWrappersHierarchyPath = ['A', 'B'], the
     *                                    property will be put inside of a wrapper called B that will be in turn be
     *                                    put inside wrapper A where A is direct child of the top level wrapper.
     *                                    Note that it is possible to add a property with Name 'N' and value 'V' as
     *                                    direct child of the top level wrapper using
     *                                    {@code addMultiLevelNestedProperty(N, V, emptyList)}
     */
    public void addMultiLevelNestedProperty(
        String propertyName,
        BeanPropertyWriter propertyValue,
        List<String> nestedWrappersHierarchyPath
    ) {
        rootWrapper.addMultiLevelNestedProperty(propertyName, propertyValue, nestedWrappersHierarchyPath);
    }

    @Override
    public void serialize(
        Object t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider
    ) throws IOException {
        try {
            rootWrapper.serializeObjectValueOnly(t, jsonGenerator, serializerProvider);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    private interface SerializableNestedObject {

        /**
         * @return the object name
         */
        String getName();

        /**
         * serialize the whole field (object name and value)
         */
        void serialize(Object bean, JsonGenerator gen, SerializerProvider prov) throws Exception;

    }

    private static class NestedProperty implements SerializableNestedObject {
        private final String propName;
        private final BeanPropertyWriter valueWriter;

        private NestedProperty(String propName, BeanPropertyWriter valueWriter) {
            this.propName = propName;
            this.valueWriter = valueWriter;
        }

        @Override
        public String getName() {
            return propName;
        }

        /**
         * serialize the whole field (object name and value)
         */
        @Override
        public void serialize(Object bean, JsonGenerator gen, SerializerProvider prov) throws Exception {
            //gen.writeObjectField(propName, "test");
            valueWriter.serializeAsField(bean, gen, prov);
        }

    }

    private static class NestedWrapper implements SerializableNestedObject {

        private final String wrapperName;
        private Map<String, NestedWrapper> nestedWrappers = new HashMap<>();
        private Map<String, NestedProperty> nestedProperties =  new HashMap<>();
        private List<SerializableNestedObject> nestedObjects = new ArrayList<>();
        private Set<String> nestedObjectsNamespace = new HashSet<>();

        /**
         * Creates a new nested wrapper with the specified name
         * @param wrapperName wrapper name
         */
        private NestedWrapper(String wrapperName) {
            this.wrapperName = wrapperName;
        }

        /**
         * Creates a new nested wrapper inside this wrapper.
         * Note that add operations are order dependent.
         * @param name the name of the new nested wrapper
         * @return the new nested wrapper created
         */
        public NestedWrapper addNestedWrapper(String name) {
            NestedWrapper childWrapper = new NestedWrapper(name);
            addNestedObject(childWrapper);
            nestedWrappers.put(name, childWrapper);
            return childWrapper;
        }

        /**
         * Creates a new property inside this wrapper
         * Note that add operations are order dependent.
         * @param name the name of the new nested property
         * @param value the value of the new nested property
         * @return the new nested property created
         */
        public NestedProperty addNestedProperty(String name, BeanPropertyWriter value) {
            NestedProperty prop = new NestedProperty(name, value);
            addNestedObject(prop);
            nestedProperties.put(name, prop);
            return prop;
        }

        protected void addNestedObject(SerializableNestedObject obj) {
            if(nestedObjectsNamespace.contains(obj.getName()))
                throw new IllegalArgumentException(String.format(
                    "A nested object called %s already exists",
                    obj.getName())
                );
            nestedObjectsNamespace.add(obj.getName());
            nestedObjects.add(obj);
        }

        /**
         * Wrap the specified property inside multiple wrappers. Wrappers that do not yet exist will be created.
         * @param propertyName the name of the property to add
         * @param propertyValue the value of the property to add
         * @param nestedWrappersHierarchyPath this path specifies inside which nested wrappers the property should be
         *                                    added. As an example if nestedWrappersHierarchyPath = ['A', 'B'], the
         *                                    property will be put inside of a wrapper called B that will be in turn be
         *                                    put inside wrapper A where A is direct child of the current wrapper.
         *                                    Also note that {@code addMultiLevelNestedProperty(N, V, emptyList) is
         *                                    equivalent to {@code addNestedProperty(N, V)}
         */
        public void addMultiLevelNestedProperty(
            String propertyName,
            BeanPropertyWriter propertyValue,
            List<String> nestedWrappersHierarchyPath
        ) {
            if(nestedWrappersHierarchyPath.size() == 0)
                addNestedProperty(propertyName, propertyValue);
            else{
                String topLevelChildWrapperName = nestedWrappersHierarchyPath.get(0);
                NestedWrapper childWrapper;
                if(nestedWrappers.containsKey(topLevelChildWrapperName))
                    //Get the child wrapper specified in the path
                    childWrapper = nestedWrappers.get(topLevelChildWrapperName);
                else
                    //Create new child wrapper with the name specified in the path
                    childWrapper = addNestedWrapper(topLevelChildWrapperName);
                childWrapper.addMultiLevelNestedProperty(
                    propertyName,
                    propertyValue,
                    //Remove name of already added wrapper
                    nestedWrappersHierarchyPath.subList(1, nestedWrappersHierarchyPath.size())
                );
            }
        }

        @Override
        public String getName() {
            return wrapperName;
        }

        /**
         * serialize only the object value, not the whole field (name and value)
         */
        public void serializeObjectValueOnly(Object bean, JsonGenerator gen, SerializerProvider prov) throws Exception {
            gen.writeStartObject();
            for(SerializableNestedObject o : nestedObjects)
                o.serialize(bean, gen, prov);
            gen.writeEndObject();
        }

        /**
         * serialize the whole field (object name and value)
         */
        @Override
        public void serialize(Object bean, JsonGenerator gen, SerializerProvider prov) throws Exception {
            gen.writeFieldName(wrapperName);
            gen.writeStartObject();
            for(SerializableNestedObject o : nestedObjects)
                o.serialize(bean, gen, prov);
            gen.writeEndObject();
        }

    }

}
