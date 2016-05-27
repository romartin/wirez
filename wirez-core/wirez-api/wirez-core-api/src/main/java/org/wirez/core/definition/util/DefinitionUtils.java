package org.wirez.core.definition.util;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.definition.adapter.DefinitionAdapter;
import org.wirez.core.definition.adapter.PropertyAdapter;
import org.wirez.core.definition.adapter.PropertySetAdapter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ApplicationScoped
public class DefinitionUtils {

    DefinitionManager definitionManager;

    protected DefinitionUtils() {
    }
    
    @Inject
    @SuppressWarnings("all")
    public DefinitionUtils(DefinitionManager definitionManager) {
        this.definitionManager = definitionManager;
    }

    public <T> String getDefinitionId( final T definition ) {
        
        final DefinitionAdapter<Object> adapter = definitionManager.getDefinitionAdapter( definition.getClass() );
        
        return adapter.getId( definition );
        
    }

    public <T> String getDefinitionTitle( final T definition ) {

        final DefinitionAdapter<Object> adapter = definitionManager.getDefinitionAdapter( definition.getClass() );

        return adapter.getTitle( definition );

    }

    public <T> String getDefinitionDescription( final T definition ) {

        final DefinitionAdapter<Object> adapter = definitionManager.getDefinitionAdapter( definition.getClass() );

        return adapter.getDescription( definition );

    }

    public  <T> Set<String> getDefinitionLabels( final T definition ) {
        
        final DefinitionAdapter<Object> adapter = definitionManager.getDefinitionAdapter( definition.getClass() );
        
        return adapter.getLabels( definition );
        
    }
    
    public <T> String getName( final T definition ) {

        final DefinitionAdapter<Object> adapter = definitionManager.getDefinitionAdapter( definition.getClass() );
        
        final Object name = adapter.getNameProperty( definition );
        
        if ( null != name ) {
            
            final PropertyAdapter<Object, ?> propertyAdapter = definitionManager.getPropertyAdapter( name.getClass() );
            return (String) propertyAdapter.getValue( name );
            
        }
        
        return null;
    }

    public <T> String getNameIdentifier( final T definition ) {

        final DefinitionAdapter<Object> adapter = definitionManager.getDefinitionAdapter( definition.getClass() );

        final Object name = adapter.getNameProperty( definition );

        if ( null != name ) {

            final PropertyAdapter<Object, ?> propertyAdapter = definitionManager.getPropertyAdapter( name.getClass() );
            return propertyAdapter.getId( name );

        }

        return null;
    }

    public DefinitionManager getDefinitionManager() {
        return definitionManager;
    }

    /**
     * Returns all properties from Definition's property sets.
     */
    public Set<?> getPropertiesFromPropertySets( final Object definition ) {

        final DefinitionAdapter<Object> definitionAdapter = definitionManager.getDefinitionAdapter( definition.getClass() );
        final Set<Object> properties = new HashSet<>();

        // And properties on each definition's annotated PropertySet.
        final Set<?> propertySets = definitionAdapter.getPropertySets(definition);
        if ( null != propertySets && !propertySets.isEmpty() ) {
            for (Object propertySet : propertySets) {
                final PropertySetAdapter<Object> propertySetAdapter = definitionManager.getPropertySetAdapter(propertySet.getClass());
                final Set<?> setProperties = propertySetAdapter.getProperties(propertySet);
                if( null != setProperties ) {
                    properties.addAll(setProperties);
                }
            }
        }

        return properties;
    }

    @SuppressWarnings("unchecked")
    public Object getPropertyAllowedValue( final Object property,
                                           final String value ) {

        final PropertyAdapter propertyAdapter = definitionManager.getPropertyAdapter(property.getClass());

        final Map<Object, String> allowedValues = propertyAdapter.getAllowedValues( property );

        if ( null != value && null != allowedValues && !allowedValues.isEmpty() ) {

            for ( final Map.Entry<Object, String> entry : allowedValues.entrySet() ) {

                final String v = entry.getValue();

                if ( value.equals( v ) ) {

                    return entry.getKey();

                }

            }

        }

        return null;
    }
    
}
