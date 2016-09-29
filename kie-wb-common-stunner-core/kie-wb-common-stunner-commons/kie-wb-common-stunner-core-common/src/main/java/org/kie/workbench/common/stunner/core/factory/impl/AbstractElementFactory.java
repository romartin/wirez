package org.kie.workbench.common.stunner.core.factory.impl;

import org.kie.workbench.common.stunner.core.factory.graph.ElementFactory;
import org.kie.workbench.common.stunner.core.graph.Element;
import org.kie.workbench.common.stunner.core.graph.content.view.BoundImpl;
import org.kie.workbench.common.stunner.core.graph.content.Bounds;
import org.kie.workbench.common.stunner.core.graph.content.view.BoundsImpl;

public abstract class AbstractElementFactory<C, T extends Element<C>>
        implements ElementFactory<C, T> {

    protected AbstractElementFactory() {
    }

    // TODO
    protected Bounds buildBounds() {
        return new BoundsImpl(new BoundImpl(0d, 0d), new BoundImpl(0d, 0d));
    }

}
