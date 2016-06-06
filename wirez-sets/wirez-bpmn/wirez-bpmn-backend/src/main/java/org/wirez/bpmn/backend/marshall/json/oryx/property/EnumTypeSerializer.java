package org.wirez.bpmn.backend.marshall.json.oryx.property;

import org.apache.commons.lang3.StringUtils;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.definition.adapter.PropertyAdapter;
import org.wirez.core.definition.property.PropertyType;
import org.wirez.core.definition.property.type.EnumType;
import org.wirez.core.definition.property.type.StringType;
import org.wirez.core.definition.util.DefinitionUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Map;

@ApplicationScoped
public class EnumTypeSerializer implements Bpmn2OryxPropertySerializer<Object> {
    
    
    DefinitionUtils definitionUtils;

    protected EnumTypeSerializer() {
        this( null );
    }

    @Inject
    public EnumTypeSerializer(DefinitionUtils definitionUtils) {
        this.definitionUtils = definitionUtils;
    }

    @Override
    public boolean accepts( PropertyType type ) {
        return EnumType.name.equals( type.getName() );
    }

    @Override
    public Object parse( Object property, String value ) {
        
        if ( null == value ) {
            
            return null;
            
        } else {

            return definitionUtils.getPropertyAllowedValue( property, value.toUpperCase() );

        }
        
    }

    @Override
    public String serialize( Object property, Object value ) {
        
        return StringUtils.capitalize( value.toString().toLowerCase() );
        
    }
    
}
