package org.wirez.client.widgets.palette;

import org.wirez.client.widgets.palette.view.PaletteWidgetView;
import org.wirez.core.client.components.palette.model.PaletteDefinition;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.factory.ShapeFactory;

public interface PaletteWidget<D extends PaletteDefinition, V extends PaletteWidgetView>
        extends org.wirez.core.client.components.palette.Palette<D> {

    interface ItemDropCallback {

        void onDropItem(Object definition,
                        ShapeFactory<?, ?, ? extends Shape> factory,
                        double x,
                        double y );

    }

    PaletteWidget<D, V> onItemDrop( ItemDropCallback callback );

    PaletteWidget<D, V> setMaxWidth( int maxWidth );

    PaletteWidget<D, V> setMaxHeight( int maxHeight );

    PaletteWidget<D, V> expand();

    PaletteWidget<D, V> collapse();

    void unbind();

    V getView();

}
