package org.wirez.core.api.graph.factory;

import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.view.BoundImpl;
import org.wirez.core.api.graph.content.view.Bounds;
import org.wirez.core.api.graph.content.view.BoundsImpl;
import org.wirez.core.api.graph.content.view.View;

public abstract class BaseViewElementFactory<W, C extends View<W>, T extends Element<C>>  implements ViewElementFactory<W, C, T> {

    protected Bounds buildBounds() {
        return new BoundsImpl(new BoundImpl(0d, 0d), new BoundImpl(0d, 0d));
    }
    
}
