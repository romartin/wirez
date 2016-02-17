package org.wirez.core.api.graph.factory;

import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.Bounds;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.impl.BoundImpl;
import org.wirez.core.api.graph.impl.BoundsImpl;

public abstract class BaseElementFactory<W extends Definition, C extends ViewContent<W>, T extends Element<C>>  implements ElementFactory<W, C, T> {

    protected Bounds buildBounds() {
        return new BoundsImpl(new BoundImpl(0d, 0d), new BoundImpl(0d, 0d));
    }
    
}
