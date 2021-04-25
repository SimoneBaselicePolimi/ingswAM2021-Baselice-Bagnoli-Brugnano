package it.polimi.ingsw.utils.serialization.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import it.polimi.ingsw.utils.serialization.annotations.SerializeInsideWrapper;
import it.polimi.ingsw.utils.serialization.serializers.MultiLevelWrappedPropertiesSerializer;

import java.util.*;

public class SerializeInsideWrapperModule extends Module {

    public static final String WRAPPERS_HIERARCHY_DELIMITER = "\\.";

    @Override
    public String getModuleName() {
        return "SerializeInsideWrapperModule";
    }

    @Override
    public Version version() {
        return new Version(0,0,1, null, null, null);
    }

    @Override
    public void setupModule(SetupContext setupContext) {
        setupContext.addBeanSerializerModifier(new SerializeInsideWrapperSerializerModifier());
    }

    private static class SerializeInsideWrapperSerializerModifier extends BeanSerializerModifier {

        @Override
        public List<BeanPropertyWriter> changeProperties(
            SerializationConfig config,
            BeanDescription beanDesc,
            List<BeanPropertyWriter> beanProperties
        ) {
            Map<String, MultiLevelWrappedPropertiesSerializer> topLevelWrapperSerializers = new HashMap<>();
            ListIterator<BeanPropertyWriter> propIter = beanProperties.listIterator();
            while(propIter.hasNext()){
                BeanPropertyWriter property = propIter.next();
                SerializeInsideWrapper wrapperAnnotation = property.getAnnotation(SerializeInsideWrapper.class);
                if (wrapperAnnotation != null) {
                    List<String> propertyWrapperHierarchy = Arrays.asList(
                        wrapperAnnotation.value().split(WRAPPERS_HIERARCHY_DELIMITER)
                    );
                    String topLevelWrapperName = propertyWrapperHierarchy.get(0);
                    MultiLevelWrappedPropertiesSerializer topLevelWrapperSerializer;
                    if(topLevelWrapperSerializers.containsKey(topLevelWrapperName)) {
                        topLevelWrapperSerializer = topLevelWrapperSerializers.get(topLevelWrapperName);
                        propIter.remove();
                    } else {
                        topLevelWrapperSerializer = new MultiLevelWrappedPropertiesSerializer(topLevelWrapperName);
                        BeanPropertyWriter newWrapperProperty = new PropertiesTopLevelWrapperWriter(
                            property,
                            topLevelWrapperName,
                            topLevelWrapperSerializer
                        );
                        propIter.set(newWrapperProperty);
                        topLevelWrapperSerializers.put(topLevelWrapperName, topLevelWrapperSerializer);
                    }
                    topLevelWrapperSerializer.addMultiLevelNestedProperty(
                        property.getName(),
                        property,
                        propertyWrapperHierarchy.subList(1, propertyWrapperHierarchy.size())
                    );
                }
            }
            return beanProperties;
        }

        private static class PropertiesTopLevelWrapperWriter extends BeanPropertyWriter {

            public PropertiesTopLevelWrapperWriter(
                BeanPropertyWriter baseProp,
                String wrapperName,
                MultiLevelWrappedPropertiesSerializer serializer
            ) {
                super(baseProp, new PropertyName(wrapperName));
                assignSerializer(serializer);
            }

            @Override
            public void serializeAsField(
                Object bean, JsonGenerator gen, SerializerProvider prov
            ) throws Exception {
                //We need to pass the whole object bean to our serializer instead of just the value of the original
                // property
                getSerializer().serialize(bean, gen, prov);
            }

        }

    }

}
