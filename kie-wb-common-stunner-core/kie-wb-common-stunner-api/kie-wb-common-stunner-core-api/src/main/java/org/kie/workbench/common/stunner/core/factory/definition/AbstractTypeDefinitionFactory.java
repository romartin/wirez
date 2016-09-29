package org.kie.workbench.common.stunner.core.factory.definition;

import org.kie.workbench.common.stunner.core.definition.adapter.binding.BindableAdapterUtils;

import java.util.Set;

public abstract class AbstractTypeDefinitionFactory<T> implements TypeDefinitionFactory<T> {

    public abstract Set<Class<? extends T>> getAcceptedClasses();

    @Override
    public boolean accepts( final Class<? extends T> type ) {
        return getAcceptedClasses().contains( type );
    }

    @Override
    public boolean accepts( final String id ) {
        return getClass( id ) != null;
    }

    @Override
    public T build( final String id ) {
        final Class<? extends T> clazz = getClass( id );

        if ( null != clazz ) {
            return build( clazz );
        }

        return null;
    }

    protected Class<? extends T> getClass( final String id ) {
        final Set<Class<? extends T>> acceptedClasses = getAcceptedClasses();
        if ( null != acceptedClasses && !acceptedClasses.isEmpty() ) {
            for ( final Class<? extends T> clazz : acceptedClasses ) {
                if ( BindableAdapterUtils.getGenericClassName( clazz ).equals( id ) ) {
                    return clazz;
                }
            }
        }

        return null;
    }

}
