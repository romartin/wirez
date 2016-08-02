package org.wirez.core.factory.impl;

import org.wirez.core.factory.graph.ElementFactory;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.content.view.BoundImpl;
import org.wirez.core.graph.content.view.Bounds;
import org.wirez.core.graph.content.view.BoundsImpl;

public abstract class AbstractElementFactory<C, T extends Element<C>>
        implements ElementFactory<C, T> {

    protected AbstractElementFactory() {
    }

    // TODO
    protected Bounds buildBounds() {
        return new BoundsImpl(new BoundImpl(0d, 0d), new BoundImpl(0d, 0d));
    }

}
