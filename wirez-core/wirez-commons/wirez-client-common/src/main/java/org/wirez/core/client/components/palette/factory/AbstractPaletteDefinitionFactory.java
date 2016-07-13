package org.wirez.core.client.components.palette.factory;

import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.components.palette.model.PaletteDefinitionBuilder;

public abstract class AbstractPaletteDefinitionFactory<B extends PaletteDefinitionBuilder>
        implements PaletteDefinitionFactory<B> {

    protected ShapeManager shapeManager;
    protected B paletteBuilder;

    public AbstractPaletteDefinitionFactory( final ShapeManager shapeManager,
                                             final B paletteBuilder ) {
        this.shapeManager = shapeManager;
        this.paletteBuilder = paletteBuilder;
    }

    @Override
    public B newBuilder( final String defSetId ) {
        return paletteBuilder;
    }

}
