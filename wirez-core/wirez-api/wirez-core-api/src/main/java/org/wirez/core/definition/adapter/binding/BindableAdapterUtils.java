package org.wirez.core.definition.adapter.binding;

import org.jboss.errai.databinding.client.BindableProxy;
import org.jboss.errai.databinding.client.HasProperties;
import org.jboss.errai.databinding.client.PropertyType;
import org.jboss.errai.databinding.client.api.DataBinder;

import java.util.Map;

public class BindableAdapterUtils {

    public static final String SHAPE_SET_SUFFIX = "ShapeSet";
    
    public static String getDefinitionId( final Class<?> pojoClass ) {
        return getGenericClassName( pojoClass );
    }

    public static String getDefinitionSetId( final Class<?> pojoClass ) {
        return getGenericClassName(pojoClass);
    }

    public static String getPropertySetId( final Class<?> pojoClass ) {
        return getGenericClassName(pojoClass);
    }

    public static String getPropertyId( final Class<?> pojoClass ) {
        return getGenericClassName(pojoClass);
    }

    public static String getShapeSetId( final Class<?> defSetClass ) {
        final String id = getGenericClassId( defSetClass );
        return id + SHAPE_SET_SUFFIX;

    }
    
    public static String getGenericClassName(final Class<?> pojoClass ) {
        final Class<?> clazz = handleBindableProxyClass( pojoClass );
        return clazz.getName();
    }

    private static String getGenericClassId(final Class<?> pojoClass ) {
        final Class<?> clazz = handleBindableProxyClass( pojoClass );
        return clazz.getSimpleName();
    }

    // When porting bindable models from backend, they objects are proxied by errai databinder classes.
    // This does not happens if the model is created on client side.
    // TODO: Deep into this... check pere's forms callback, as it's where this happens.
    public static Class<?> handleBindableProxyClass(final Class<?> pojoClass) {

        if ( pojoClass.getName().startsWith("org.jboss.errai.databinding") ) {
            return pojoClass.getSuperclass();
        }

        return pojoClass;
    }

    @SuppressWarnings("unchecked")
    public static <T> T newInstance( final Class<?> pojoType ) {

        if ( null != pojoType ) {

            return (T) DataBinder.forType( pojoType ).getModel();

        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T clone( final T pojo ) {
        
        if ( null != pojo ) {

            final BindableProxy proxy = (BindableProxy) DataBinder.forModel (pojo ).getModel();
            
            return (T) proxy.deepUnwrap();

        }
        
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T, R> R morph( final T pojo,
                                  final Class<?> targetType ) {

        if (null != pojo) {
            
            final HasProperties hasProperties = (HasProperties) DataBinder.forModel( pojo ).getModel();

            if ( null != hasProperties ) {
                
                final Map<String, PropertyType> propertyTypeMap = hasProperties.getBeanProperties();

                if ( null != propertyTypeMap && !propertyTypeMap.isEmpty() ) {

                    final Object target = DataBinder.forType( targetType ).getModel();
                    final HasProperties targetProperties = (HasProperties) DataBinder.forModel( target ).getModel();

                    for ( final Map.Entry<String, PropertyType> entry : propertyTypeMap.entrySet() ) {
                        
                        final String pId = entry.getKey();
                        
                        // TODO: Check here if property must be set into target taget type.
                        if ( true ) {
                            
                            targetProperties.set( pId, hasProperties.get( pId ) );
                        }
                        
                    }
                    
                    return (R) target;
                    
                }
                
            }

        }

        return null;
    }
    
}
