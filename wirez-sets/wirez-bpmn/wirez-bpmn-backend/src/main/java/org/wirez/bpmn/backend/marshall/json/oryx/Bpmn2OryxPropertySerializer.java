package org.wirez.bpmn.backend.marshall.json.oryx;

import org.wirez.core.api.definition.property.PropertyType;

/**
 * Serializer for the different property values expected by oryx/jbpmdesigner marshallers.
 * @param <T> The type for the value.
 */
public interface Bpmn2OryxPropertySerializer<T> {
    
    boolean accepts(PropertyType type );
    
    T parse( String value );
    
    String serialize( T value );
    
}
