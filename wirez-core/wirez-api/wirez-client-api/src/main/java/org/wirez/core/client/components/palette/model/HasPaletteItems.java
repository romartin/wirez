package org.wirez.core.client.components.palette.model;

import java.util.List;

public interface HasPaletteItems<I extends PaletteItem> {

    List<I> getItems();

}
