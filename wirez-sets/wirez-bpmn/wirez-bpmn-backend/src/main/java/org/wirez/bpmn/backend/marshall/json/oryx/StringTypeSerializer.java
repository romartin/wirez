package org.wirez.bpmn.backend.marshall.json.oryx;

import org.wirez.core.api.definition.property.PropertyType;
import org.wirez.core.api.definition.property.type.StringType;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StringTypeSerializer implements Bpmn2OryxPropertySerializer<String> {
    
    @Override
    public boolean accepts( PropertyType type ) {
        return StringType.name.equals( type.getName() );
    }

    @Override
    public String parse( String value ) {
        return value;
    }

    @Override
    public String serialize( String value ) {
        return value;
    }
}
