package org.wirez.backend.definition.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wirez.core.definition.adapter.DefinitionAdapter;
import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.definition.annotation.Description;
import org.wirez.core.definition.annotation.definition.*;
import org.wirez.core.definition.annotation.property.NameProperty;
import org.wirez.core.definition.util.DefinitionUtils;
import org.wirez.core.graph.Element;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

@Dependent
public class AnnotatedDefinitionAdapter<T> extends AbstractAnnotatedAdapter<T> implements DefinitionAdapter<T> {

    private static final Logger LOG = LoggerFactory.getLogger(AnnotatedDefinitionAdapter.class);

    DefinitionUtils definitionUtils;

    @Inject
    public AnnotatedDefinitionAdapter( final DefinitionUtils definitionUtils ) {
        this.definitionUtils = definitionUtils;
    }

    @Override
    public boolean accepts(Class<?> pojo) {
        return pojo.getAnnotation(Definition.class) != null;
    }

    @Override
    public String getId(T definition) {
        String result = null;

        if ( null != definition ) {
            result = BindableAdapterUtils.getDefinitionId(definition.getClass());
        }

        return result;
    }

    @Override
    public Object getNameProperty(T pojo) {

        Set<?> properties = getProperties( pojo );
        if ( null != properties && !properties.isEmpty() ) {
            for ( Object property : properties ) {
                if ( null != property.getClass().getAnnotation(NameProperty.class) ) {
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
        Set<Object> result = null;

        if ( null != definition ) {
            Field[] fields = definition.getClass().getDeclaredFields();
            if ( null != fields ) {
                result = new HashSet<>();
                for (Field field : fields) {
                    PropertySet annotation = field.getAnnotation(PropertySet.class);
                    if ( null != annotation ) {
                        try {
                            result.add( _getValue( field, annotation, definition) );
                        } catch (Exception e) {
                            LOG.error("Error obtaining annotated property sets for Definition with id " + getId( definition ));
                        }
                    }
                }
            }
        }

        return result;
    }

    @Override
    public Set<?> getProperties(final T definition) {
        Set<Object> result = null;

        if ( null != definition ) {
            Field[] fields = definition.getClass().getDeclaredFields();
            if ( null != fields ) {
                result = new HashSet<>();
                
                // Obtain all properties from property sets.
                Set<?> propertySetProperties = definitionUtils.getPropertiesFromPropertySets( definition );
                if ( null != propertySetProperties ) {
                    result.addAll( propertySetProperties );
                }

                // Find annotated runtime properties on the pojo.
                for (Field field : fields) {
                    Property annotation = field.getAnnotation(Property.class);
                    if ( null != annotation ) {
                        try {
                            result.add( _getValue( field, annotation, definition) );
                        } catch (Exception e) {
                            LOG.error("Error obtaining annotated properties for Definition with id " + getId( definition ));
                        }
                    }
                }
            }
        }
        
        return result;
    }
    
    @SuppressWarnings("unchecked")
    private <V> V _getValue( Field field, Object annotation, T definition) throws IllegalAccessException {
        if ( null != annotation) {
            field.setAccessible(true);
            V property = (V) field.get(definition);
            return property;
        }
        return null;
    }

    @Override
    public Class<? extends Element> getGraphElement(T definition) {

        if ( null != definition ) {
            return getGraphElement( definition.getClass() );
        }
        
        return null;
    }

    public static Class<? extends Element> getGraphElement( Class<?> definitionClass ) {

        Class<? extends Element> result = null;

        if ( null != definitionClass ) {
            Definition annotation = definitionClass.getAnnotation(Definition.class);
            if ( null != annotation ) {
                result = annotation.type();
            }
        }

        return result;
    }

    @Override
    public String getElementFactory(T definition) {
        String result = null;

        if ( null != definition ) {
            Definition annotation = definition.getClass().getAnnotation(Definition.class);
            if ( null != annotation ) {
                result = annotation.factory();
            }
        }

        return result;
    }

    @Override
    public int getPriority() {
        return 100;
    }
    
}
