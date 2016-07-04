package org.wirez.core.client.components.palette.view;

import org.wirez.core.client.components.palette.model.PaletteItem;

public interface PaletteItemView<I extends PaletteItem, V> extends PaletteElementView<V> {

    I getPaletteItem();

}
