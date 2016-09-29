package org.kie.workbench.common.stunner.bpmn.backend.marshall.json.oryx.property;

import org.kie.workbench.common.stunner.core.definition.property.PropertyType;
import org.kie.workbench.common.stunner.core.definition.property.type.DoubleType;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DoubleTypeSerializer implements Bpmn2OryxPropertySerializer<Double> {
    
    @Override
    public boolean accepts( PropertyType type ) {
        return DoubleType.name.equals( type.getName() );
    }

    @Override
    public Double parse( Object property, String value ) {
        return Double.parseDouble( value );
    }

    @Override
    public String serialize( Object property, Double value ) {
        return Double.toString( value );
    }
}
