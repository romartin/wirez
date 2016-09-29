package org.kie.workbench.common.stunner.core.client.components.palette.model;

public interface PaletteGroupBuilder<B, G, I> extends PaletteItemBuilder<B, G> {

    B addItem( I item );

    B addItem( int index, I item);

    I getItem( String id );

}
