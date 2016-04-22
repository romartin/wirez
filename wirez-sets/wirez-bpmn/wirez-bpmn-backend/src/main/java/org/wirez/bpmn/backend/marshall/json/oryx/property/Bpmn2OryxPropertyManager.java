package org.wirez.bpmn.backend.marshall.json.oryx.property;

import org.wirez.core.api.definition.property.PropertyType;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

/**
 * Provides the Property Serializers for the serialization expected by oryx/jbpmdesigner marshallers.
 */
@Dependent
public class Bpmn2OryxPropertyManager {

    Instance<Bpmn2OryxPropertySerializer<?>> propertySerializerInstances;
    private final List<Bpmn2OryxPropertySerializer<?>> propertySerializers = new LinkedList<>();

    protected Bpmn2OryxPropertyManager() {
    }

    public Bpmn2OryxPropertyManager(List<Bpmn2OryxPropertySerializer<?>> propertySerializers) {
        this.propertySerializers.addAll( propertySerializers );
    }

    @Inject
    public Bpmn2OryxPropertyManager(Instance<Bpmn2OryxPropertySerializer<?>> propertySerializerInstances) {
        this.propertySerializerInstances = propertySerializerInstances;
    }

    @PostConstruct
    public void init() {
        initPropertySerializers();
    }

    private void initPropertySerializers() {
        for (Bpmn2OryxPropertySerializer<?> serializerInstance : propertySerializerInstances ) {
            propertySerializers.add( serializerInstance );
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T parse( PropertyType propertyType, String value ) {
        Bpmn2OryxPropertySerializer<T> serializer = (Bpmn2OryxPropertySerializer<T>) getSerializer( propertyType );
        return serializer.parse( value );
    }

    @SuppressWarnings("unchecked")
    public <T> String serialize( PropertyType propertyType, T value ) {
        Bpmn2OryxPropertySerializer<T> serializer = (Bpmn2OryxPropertySerializer<T>) getSerializer( propertyType );
        return serializer.serialize( value );
    }
    
    protected Bpmn2OryxPropertySerializer<?> getSerializer( PropertyType type ) {
        for( Bpmn2OryxPropertySerializer<?> serializer : propertySerializers ) {
            if ( serializer.accepts( type) ) {
                return serializer;
            }
        }
        throw new RuntimeException("No property serializer found for type [" + type + "]");
    }
    
}
