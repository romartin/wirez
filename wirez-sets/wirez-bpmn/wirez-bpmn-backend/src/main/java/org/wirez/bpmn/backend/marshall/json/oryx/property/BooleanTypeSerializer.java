package org.wirez.bpmn.backend.marshall.json.oryx.property;

import org.wirez.core.api.definition.property.PropertyType;
import org.wirez.core.api.definition.property.type.BooleanType;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BooleanTypeSerializer implements Bpmn2OryxPropertySerializer<Boolean> {
    
    @Override
    public boolean accepts( PropertyType type ) {
        return BooleanType.name.equals( type.getName() );
    }

    @Override
    public Boolean parse( String value ) {
        return Boolean.parseBoolean( value );
    }

    @Override
    public String serialize( Boolean value ) {
        return Boolean.toString( value );
    }
}
