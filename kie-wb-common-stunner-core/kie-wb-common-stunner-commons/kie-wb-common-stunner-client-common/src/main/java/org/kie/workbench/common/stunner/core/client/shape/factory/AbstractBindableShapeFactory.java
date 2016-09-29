package org.kie.workbench.common.stunner.core.client.shape.factory;

import org.kie.workbench.common.stunner.core.definition.adapter.binding.BindableAdapterUtils;
import org.kie.workbench.common.stunner.core.client.shape.Shape;
import org.kie.workbench.common.stunner.core.client.shape.view.ShapeGlyph;

import java.util.Set;

public abstract class AbstractBindableShapeFactory<W, S extends Shape> extends AbstractShapeFactory<W, S> {

    public abstract Set<Class<?>> getSupportedModelClasses();

    protected abstract String getDescription( Class<?> clazz );

    protected abstract ShapeGlyph glyph( Class<?> clazz, double width, double height );

    @Override
    public ShapeGlyph glyph( final String definitionId,
                             final double width,
                             final double height) {
        return glyph( getDefinitionClass( definitionId ) , width, height );
    }
    
    @Override
    public boolean accepts( final String definitionId ) {
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

    @Override
    public String getDescription( final String definitionId ) {
        final Class<?> clazz = getDefinitionClass( definitionId );
        return getDescription( clazz );
    }

    protected String getDefinitionId( final Class<?> definitionClazz ) {
        return BindableAdapterUtils.getDefinitionId( definitionClazz );
    }

    protected Class<?> getDefinitionClass( final String definitionId ) {

        final Set<Class<?>> supportedClasses = getSupportedModelClasses();
        if ( null != supportedClasses && !supportedClasses.isEmpty() ) {
            for ( final Class<?> supportedClass : supportedClasses ) {
                final String _id = BindableAdapterUtils.getDefinitionId( supportedClass );
                if ( _id.equals(definitionId) ) {
                    return supportedClass;
                }
            }
        }
        
        return null;
    }
    
}
