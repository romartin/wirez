package org.wirez.core.client.shape.factory;

import org.wirez.core.api.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.client.shape.Shape;

import java.util.Set;

public abstract class AbstractBindableShapeFactory<W, S extends Shape> extends AbstractShapeFactory<W, S> {

    protected abstract Set<Class<?>> getSupportedModelClasses();

    @Override
    public boolean accepts(final String definitionId) {
        final Set<Class<?>> supportedClasses = getSupportedModelClasses();
        if ( null != supportedClasses && !supportedClasses.isEmpty() ) {
            for ( final Class<?> supportedClass : supportedClasses ) {
                final String _id = BindableAdapterUtils.getDefinitionId( supportedClass );
                if ( _id.equals(definitionId) ) {
                    return true;
                }
            }
        }
        return false;
    }


}
