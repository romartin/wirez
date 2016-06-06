package org.wirez.core.definition.adapter.binding;

import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;

public class BindableAdapterUtils {

    private static Logger LOGGER = Logger.getLogger(BindableAdapterUtils.class.getName());
    
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

    public static  <T> Collection<Class<?>> toClassCollection( final Iterable<T> source ) {

        if ( null != source && source.iterator().hasNext() ) {

            final LinkedList<Class<?>> result = new LinkedList<>();

            for ( final Object sourceObject : source ) {

                result.add( sourceObject.getClass() );

            }

            return result;

        }

        return null;

    }

}
