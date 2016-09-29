package org.kie.workbench.common.stunner.core.client.components.palette.model;

public interface PaletteItemBuilder<B, I> {

    String getId();

    B title( String title );

    B description( String description );

    B tooltip( String tooltip );

    I build();

}
