package org.wirez.core.client.components.palette.model;

public interface PaletteGroupBuilder<B, G, I> extends PaletteItemBuilder<B, G> {

    B addItem( I item );

    I getItem( String id );

}
