package org.wirez.core.client.components.palette.factory;

import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.components.palette.model.PaletteDefinitionBuilder;
import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;

public abstract class BindablePaletteDefinitionFactory<B extends PaletteDefinitionBuilder>
        extends AbstractPaletteDefinitionFactory<B> {

    public BindablePaletteDefinitionFactory( final ShapeManager shapeManager,
                                             final B paletteBuilder ) {
        super( shapeManager, paletteBuilder );
    }

    protected abstract Class<?> getDefinitionSetType();

    protected abstract B newBuilder();

    @Override
    public boolean accepts( final String defSetId ) {
        final String s = getId( getDefinitionSetType() );
        return null != defSetId && defSetId.equals( s );
    }

    @Override
    public B newBuilder( final String defSetId ) {
        return newBuilder();
    }

    protected String getId( final Class<?> defSetType ) {
        return BindableAdapterUtils.getDefinitionSetId( defSetType );
    }

}
