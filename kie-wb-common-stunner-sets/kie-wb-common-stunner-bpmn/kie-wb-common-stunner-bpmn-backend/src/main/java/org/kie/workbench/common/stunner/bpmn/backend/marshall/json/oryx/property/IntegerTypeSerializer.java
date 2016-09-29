package org.kie.workbench.common.stunner.bpmn.backend.marshall.json.oryx.property;

import org.kie.workbench.common.stunner.core.definition.property.PropertyType;
import org.kie.workbench.common.stunner.core.definition.property.type.IntegerType;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class IntegerTypeSerializer implements Bpmn2OryxPropertySerializer<Integer> {
    
    @Override
    public boolean accepts( PropertyType type ) {
        return IntegerType.name.equals( type.getName() );
    }

    @Override
    public Integer parse( Object property, String value ) {
        return Integer.parseInt( value );
    }

    @Override
    public String serialize( Object property, Integer value ) {
        return Integer.toString( value );
    }
}
