package org.wirez.bpmn.backend.marshall.json.oryx;

import org.wirez.core.api.definition.property.PropertyType;
import org.wirez.core.api.definition.property.type.IntegerType;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class IntegerTypeSerializer implements Bpmn2OryxPropertySerializer<Integer> {
    
    @Override
    public boolean accepts( PropertyType type ) {
        return IntegerType.name.equals( type.getName() );
    }

    @Override
    public Integer parse( String value ) {
        return Integer.parseInt( value );
    }

    @Override
    public String serialize( Integer value ) {
        return Integer.toString( value );
    }
}
