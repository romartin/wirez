package org.wirez.core.api.definition.factory;

import org.wirez.core.api.definition.adapter.binding.BindableAdapterUtils;

import java.util.Set;

public abstract class BindableModelFactory<W> implements ModelFactory<W> {

    public abstract Set<Class<?>> getAcceptedClasses();

    public abstract W build( final Class<?> clazz );

    @Override
    public boolean accepts(final String id) {
        return getClass( id ) != null;
    }

    @Override
    public W build(final String id) {
        final Class<?> clazz = getClass( id );

        if ( null != clazz ) {
            return build( clazz );
        }

        return null;
    }

    protected Class<?> getClass( final String id ) {
        final Set<Class<?>> acceptedClasses = getAcceptedClasses();
        if ( null != acceptedClasses && !acceptedClasses.isEmpty() ) {
            for ( final Class<?> clazz : acceptedClasses ) {
                if ( BindableAdapterUtils.getGenericId( clazz ).equals( id ) ) {
                    return clazz;
                }
            }
        }

        return null;
    }

}
