package org.wirez.core.definition.util;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.FactoryManager;
import org.wirez.core.definition.adapter.*;
import org.wirez.core.definition.adapter.binding.HasInheritance;
import org.wirez.core.definition.morph.MorphDefinition;
import org.wirez.core.definition.morph.MorphPolicy;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.Node;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ApplicationScoped
public class DefinitionUtils {

    DefinitionManager definitionManager;
    FactoryManager factoryManager;

    protected DefinitionUtils() {
        this( null, null );
    }
    
    @Inject
    @SuppressWarnings("all")
    public DefinitionUtils( final DefinitionManager definitionManager,
                            final FactoryManager factoryManager ) {
        this.definitionManager = definitionManager;
        this.factoryManager = factoryManager;
    }

    public <T> String getDefinitionId( final T definition ) {
        
        final DefinitionAdapter<Object> adapter = definitionManager.getDefinitionAdapter( definition.getClass() );
        
        return adapter.getId( definition );
        
    }

    public <T> String getDefinitionTitle( final T definition ) {

        final DefinitionAdapter<Object> adapter = definitionManager.getDefinitionAdapter( definition.getClass() );

        return adapter.getTitle( definition );

    }

    public <T> String getDefinitionCategory( final T definition ) {

        final DefinitionAdapter<Object> adapter = definitionManager.getDefinitionAdapter( definition.getClass() );

        return adapter.getCategory( definition );

    }

    public <T> String getDefinitionDescription( final T definition ) {

        final DefinitionAdapter<Object> adapter = definitionManager.getDefinitionAdapter( definition.getClass() );

        return adapter.getDescription( definition );

    }

    public  <T> Set<String> getDefinitionLabels( final T definition ) {
        
        final DefinitionAdapter<Object> adapter = definitionManager.getDefinitionAdapter( definition.getClass() );
        
        return adapter.getLabels( definition );
        
    }

    public <T> Object getProperty( final T definition, final String propertyId ) {

        final DefinitionAdapter<Object> definitionAdapter = definitionManager.getDefinitionAdapter( definition.getClass() );
        final Set<?> properties = definitionAdapter.getProperties( definition );
        
        if ( null != properties && !properties.isEmpty() ) {
            
            for ( final Object property : properties ) {
                
                final PropertyAdapter<Object, ?> propertyAdapter = definitionManager.getPropertyAdapter( property.getClass() );
                
                final String pId = propertyAdapter.getId( property );
                
                if ( pId.equals( propertyId ) ) {
                    
                    return property;
                    
                }
                
            }
            
        }
        
        return null;

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

    public <T> MorphDefinition getMorphDefinition( final T definition ) {

        final MorphAdapter<Object> adapter = definitionManager.getMorphAdapter( definition.getClass() );

        final Iterable<MorphDefinition> definitions = adapter.getMorphDefinitions( definition );

        if ( null != definitions && definitions.iterator().hasNext() ) {

            return definitions.iterator().next();

        }

        return null;

    }

    /**
     * Returns the identifiers for the defintion type and its parent, if any.
     */
    public <T> String[] getDefinitionIds( final T definition ) {

        final Class<?> type = definition.getClass();
        final DefinitionAdapter<Object> definitionAdapter = definitionManager.getDefinitionAdapter( type );
        final String definitionId = definitionAdapter.getId( definition );

        String baseId = null;
        if ( definitionAdapter instanceof HasInheritance) {

            baseId = ( (HasInheritance) definitionAdapter).getBaseType( type );

        }

        return new String[] { definitionId, baseId };

    }

    public String getDefaultConnectorId( final String definitionSetId ) {

        final Object defSet = getDefinitionManager().getDefinitionSet( definitionSetId );

        if ( null != defSet ) {

            final DefinitionSetAdapter<Object> definitionSetAdapter = getDefinitionManager().getDefinitionSetAdapter( defSet.getClass() );
            final Set<String> definitions = definitionSetAdapter.getDefinitions( defSet );

            if ( null != definitions && !definitions.isEmpty() ) {

                for ( final String defId : definitions ) {

                    // TODO: Find a way to have a default connector for a DefSet or at least do not create objects here.

                    final Object def = factoryManager.newDomainObject( defId );

                    if ( null != def ) {

                        final DefinitionAdapter<Object> definitionAdapter = getDefinitionManager().getDefinitionAdapter( def.getClass() );
                        final Class<? extends Element> graphElement = definitionAdapter.getGraphElement( def );

                        if ( isEdge( graphElement ) ) {

                            return defId;

                        }


                    }

                }

            }

        }

        return null;
    }

    public boolean isAllPolicy( final MorphDefinition definition ) {
        return MorphPolicy.ALL.equals( definition.getPolicy() );
    }

    public boolean isNonePolicy( final MorphDefinition definition ) {
        return MorphPolicy.NONE.equals( definition.getPolicy() );
    }

    public boolean isDefaultPolicy( final MorphDefinition definition ) {
        return MorphPolicy.DEFAULT.equals( definition.getPolicy() );
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

    public static boolean isNode( final Class<?> graphElementClass) {

        return graphElementClass.equals( Node.class );

    }

    public static boolean isEdge( final Class<?> graphElementClass) {

        return graphElementClass.equals( Edge.class );

    }
    
}
