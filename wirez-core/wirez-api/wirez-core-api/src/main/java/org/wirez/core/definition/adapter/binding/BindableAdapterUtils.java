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
        return pojoClass.getName();
    }

    private static String getGenericClassId(final Class<?> pojoClass ) {
        return pojoClass.getSimpleName();
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
