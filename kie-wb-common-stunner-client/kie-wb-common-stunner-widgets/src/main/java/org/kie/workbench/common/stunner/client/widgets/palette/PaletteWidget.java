package org.kie.workbench.common.stunner.client.widgets.palette;

import org.kie.workbench.common.stunner.core.client.components.palette.Palette;
import org.kie.workbench.common.stunner.core.client.components.palette.model.PaletteDefinition;
import org.kie.workbench.common.stunner.core.client.shape.Shape;
import org.kie.workbench.common.stunner.core.client.shape.factory.ShapeFactory;

public interface PaletteWidget<D extends PaletteDefinition, V extends PaletteWidgetView>
        extends Palette<D> {

    interface ItemDropCallback {

        void onDropItem(Object definition,
                        ShapeFactory<?, ?, ? extends Shape> factory,
                        double x,
                        double y );

    }

    PaletteWidget<D, V> onItemDrop( ItemDropCallback callback );

    PaletteWidget<D, V> setMaxWidth( int maxWidth );

    PaletteWidget<D, V> setMaxHeight( int maxHeight );

    void unbind();

    V getView();

}
