package org.wirez.core.client.components.palette;

public interface PaletteCategory<V> {

    String getTitle();

    PaletteGrid getGrid();

    V[] getItems();

}
