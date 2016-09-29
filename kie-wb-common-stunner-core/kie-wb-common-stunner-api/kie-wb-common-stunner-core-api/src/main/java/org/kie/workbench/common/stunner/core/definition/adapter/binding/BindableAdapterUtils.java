package org.kie.workbench.common.stunner.core.definition.adapter.binding;

import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.definition.adapter.exception.NotPojoTypeException;
import org.kie.workbench.common.stunner.core.registry.definition.AdapterRegistry;

import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;

public class BindableAdapterUtils {

    private static Logger LOGGER = Logger.getLogger(BindableAdapterUtils.class.getName());
    
    public static final String SHAPE_SET_SUFFIX = "ShapeSet";
    
    public static String getDefinitionId( final Class<?> type ) {
        return getDefinitionId( type, null );
    }

    public static String getDefinitionId( final Class<?> type,
                                          final AdapterRegistry registry ) {
        
        if ( null != registry &&
                !registry.getDefinitionAdapter( type ).isPojoModel() ) {

            throw new NotPojoTypeException( type );

        }

        return getGenericClassName( type );
    }

    public static String getDefinitionSetId( final Class<?> type ) {
        return getDefinitionSetId(type, null);
    }

    public static String getDefinitionSetId( final Class<?> type,
                                             final AdapterRegistry registry ) {

        if ( null != registry &&
                !registry.getDefinitionSetAdapter( type ).isPojoModel() ) {

            throw new NotPojoTypeException( type );

        }

        return getGenericClassName(type);
    }

    public static String getPropertySetId( final Class<?> type ) {
        return getPropertySetId(type, null);
    }

    public static String getPropertySetId( final Class<?> type,
                                           final DefinitionManager definitionManager ) {

        if ( null != definitionManager &&
                !definitionManager.adapters().registry().getPropertySetAdapter( type ).isPojoModel() ) {

            throw new NotPojoTypeException( type );

        }

        return getGenericClassName(type);
    }

    public static String getPropertyId( final Class<?> type,
                                        final DefinitionManager definitionManager ) {

        if ( null != definitionManager &&
                !definitionManager.adapters().registry().getPropertyAdapter( type ).isPojoModel() ) {

            throw new NotPojoTypeException( type );

        }

        return getGenericClassName(type);
    }

    public static String getPropertyId( final Class<?> type ) {
        return getPropertyId(type, null);
    }

    public static String getShapeSetId( final Class<?> defSetClass ) {
        final String id = getGenericClassId( defSetClass );
        return id + SHAPE_SET_SUFFIX;

    }
    
    public static String getGenericClassName(final Class<?> type ) {
        return type.getName();
    }

    private static String getGenericClassId(final Class<?> type ) {
        return type.getSimpleName();
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
