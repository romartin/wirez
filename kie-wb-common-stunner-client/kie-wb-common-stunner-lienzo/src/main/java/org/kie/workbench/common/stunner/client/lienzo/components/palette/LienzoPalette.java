package org.kie.workbench.common.stunner.client.lienzo.components.palette;

import org.kie.workbench.common.stunner.client.lienzo.components.palette.view.LienzoPaletteView;
import org.kie.workbench.common.stunner.core.client.components.palette.Palette;
import org.kie.workbench.common.stunner.core.client.components.palette.model.HasPaletteItems;

public interface LienzoPalette<D extends HasPaletteItems, V extends LienzoPaletteView> extends Palette<D> {

    enum Layout {
        HORIZONTAL, VERTICAL;
    }

    void setLayout( Layout layout );

    LienzoPalette<D, V> setIconSize( int iconSize );

    LienzoPalette<D, V> setPadding( int padding );

    LienzoPalette<D, V> setExpandable( boolean canExpand );

    LienzoPalette<D, V> expand();

    LienzoPalette<D, V> collapse();

    V getView();

}
