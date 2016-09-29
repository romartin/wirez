package org.kie.workbench.common.stunner.bpmn.backend.marshall.json.oryx.property;

import org.kie.workbench.common.stunner.core.definition.property.PropertyType;

/**
 * Serializer for the different property values expected by oryx/jbpmdesigner marshallers.
 * @param <T> The type for the value.
 */
public interface Bpmn2OryxPropertySerializer<T> {
    
    boolean accepts( PropertyType type );
    
    T parse( Object property, String value );
    
    String serialize( Object property, T value );
    
}
