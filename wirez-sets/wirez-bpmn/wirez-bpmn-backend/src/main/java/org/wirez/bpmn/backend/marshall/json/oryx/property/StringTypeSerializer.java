package org.wirez.bpmn.backend.marshall.json.oryx.property;

import org.wirez.core.definition.property.PropertyType;
import org.wirez.core.definition.property.type.StringType;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StringTypeSerializer implements Bpmn2OryxPropertySerializer<String> {
    
    @Override
    public boolean accepts( PropertyType type ) {
        return StringType.name.equals( type.getName() );
    }

    @Override
    public String parse( Object property, String value ) {
        return value;
    }

    @Override
    public String serialize( Object property, String value ) {
        return value;
    }
}
