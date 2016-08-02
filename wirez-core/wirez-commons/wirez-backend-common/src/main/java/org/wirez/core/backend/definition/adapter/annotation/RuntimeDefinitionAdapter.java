package org.wirez.core.backend.definition.adapter.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wirez.core.backend.definition.adapter.AbstractRuntimeAdapter;
import org.wirez.core.definition.adapter.DefinitionAdapter;
import org.wirez.core.definition.adapter.binding.HasInheritance;
import org.wirez.core.definition.annotation.Description;
import org.wirez.core.definition.annotation.definition.*;
import org.wirez.core.definition.annotation.property.NameProperty;
import org.wirez.core.definition.util.DefinitionUtils;
import org.wirez.core.factory.graph.ElementFactory;
import org.wirez.core.graph.Element;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Dependent
public class RuntimeDefinitionAdapter<T> extends AbstractRuntimeAdapter<T>
        implements DefinitionAdapter<T>, HasInheritance {

    private static final Logger LOG = LoggerFactory.getLogger(RuntimeDefinitionAdapter.class);

    private static final Class[] DEF_ANNOTATIONS = new Class[] {
            Title.class,
            Category.class,
            Description.class,
            PropertySet.class,
            Property.class
    };

    DefinitionUtils definitionUtils;

    @Inject
    public RuntimeDefinitionAdapter(final DefinitionUtils definitionUtils ) {
        this.definitionUtils = definitionUtils;
    }

    @Override
    public boolean accepts(Class<?> pojo) {
        return pojo.getAnnotation(Definition.class) != null;
    }

    @Override
    public String getId(T definition) {
        return getDefinitionId( definition.getClass() );
    }

    @Override
    public Object getNameProperty(T pojo) {

        Set<?> properties = getProperties( pojo );
        if ( null != properties && !properties.isEmpty() ) {
            for ( Object property : properties ) {
                final Annotation annotation = getClassAnnotation( property.getClass(), NameProperty.class );
                if ( null != annotation ) {
                    return property;
                }
            }
        }
        
        return null;
    }

    @Override
    public String getCategory(T definition) {
        try {
            return getAnnotatedFieldValue( definition, Category.class );
        } catch (Exception e) {
            LOG.error("Error obtaining annotated category for Definition with id " + getId( definition ));
        }
        
        return null;
    }

    @Override
    public String getTitle(T definition) {
        try {
            return getAnnotatedFieldValue( definition, Title.class );
        } catch (Exception e) {
            LOG.error("Error obtaining annotated title for Definition with id " + getId( definition ));
        }

        return null;

    }

    @Override
    public String getDescription(T definition) {
        try {
            return getAnnotatedFieldValue( definition, Description.class );
        } catch (Exception e) {
            LOG.error("Error obtaining annotated description for Definition with id " + getId( definition ));
        }

        return null;

    }

    @Override
    public Set<String> getLabels(T definition) {
        try {
            return getAnnotatedFieldValue( definition, Labels.class );
        } catch (Exception e) {
            LOG.error("Error obtaining annotated labels for Definition with id " + getId( definition ));
        }

        return null;
    }

    @Override
    public Set<?> getPropertySets(final T definition) {

        Collection<Field> fields = getFieldAnnotations( definition.getClass(), PropertySet.class );
       
        if ( null != fields && !fields.isEmpty() ) {

            Set<Object> result = new LinkedHashSet<>();

            for ( Field field : fields ) {

                try {

                    Object v = _getValue( field, PropertySet.class, definition );
                    
                    result.add( v );

                } catch ( Exception e ) {

                    LOG.error("Error obtaining annotated property sets for Definition with id " + getId( definition ) );

                }
                
            }
                    
           return result;
            
        }
        
        return null;
    }

    @Override
    public Set<?> getProperties(final T definition) {
        Set<Object> result = null;

        if ( null != definition ) {

            result = new HashSet<>();
            
            // Obtain all properties from property sets.
            Set<?> propertySetProperties = definitionUtils.getPropertiesFromPropertySets( definition );
            
            if ( null != propertySetProperties ) {
                
                result.addAll( propertySetProperties );
                
            }

            Collection<Field> fields = getFieldAnnotations( definition.getClass(), Property.class );

            if ( null != fields && !fields.isEmpty() ) {

                for ( Field field : fields ) {

                    try {

                        Object v = _getValue( field, Property.class, definition );

                        result.add( v );

                    } catch ( Exception e ) {

                        LOG.error("Error obtaining annotated properties for Definition with id " + getId( definition ) );

                    }

                }

                return result;

            }
            
        }
        
        return result;
    }
    
    @SuppressWarnings("unchecked")
    private <V> V _getValue( Field field, Object annotation, T definition) throws IllegalAccessException {
        if ( null != annotation) {
            field.setAccessible(true);
            return  (V) field.get(definition);
        }
        return null;
    }

    @Override
    public Class<? extends ElementFactory> getGraphFactoryType( final T definition ) {

        Definition annotation = getDefinitionAnnotation( definition.getClass() );

        return null != annotation ? annotation.graphFactory() : null;

    }

    public static Class<? extends ElementFactory> getGraphFactory( final Class<?> type ) {

        Definition annotation = getDefinitionAnnotation( type );

        return null != annotation ? annotation.graphFactory() : null;

    }

    protected static Definition getDefinitionAnnotation( Class<?> type ) {
        if ( null != type ) {
            Definition annotation = getClassAnnotation( type, Definition.class );
            if ( null != annotation ) {
                return annotation;
            }
        }
        
        return null;
    }

    @Override
    public String getBaseType( Class<?> type ) {
        if ( null != type ) {
            Definition annotation = getClassAnnotation( type, Definition.class );
            if ( null != annotation ) {
                Class<?> parentType = type.getSuperclass();
                if (isBaseType( parentType ) ) {
                    return getDefinitionId( parentType );
                }
            }
        }

        return null;
    }

    @Override
    public String[] getTypes( String baseType ) {
        throw new UnsupportedOperationException( "Not implemented yet. Must keep some collection for this. " );
    }

    private boolean isBaseType( Class<?> type ) {
        Field[] fields = type.getDeclaredFields();
        if ( null != fields ) {
            for ( Field field : fields ) {
                for ( Class a : DEF_ANNOTATIONS ) {
                    Annotation annotation = field.getAnnotation( a );
                    if ( null != annotation) {
                        return true;
                    }
                }
            }
        }

        return false;
    }


}
