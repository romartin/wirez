package org.wirez.core.backend.graph.factory;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.Bounds;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.factory.ElementFactory;
import org.wirez.core.api.graph.impl.DefaultBound;
import org.wirez.core.api.graph.impl.DefaultBounds;

import javax.inject.Inject;

public abstract class BaseElementFactory<W extends Definition, T extends Element<ViewContent<W>>>  implements ElementFactory<W, T> {

    // TODO: ??
    protected Bounds buildBounds() {
        return new DefaultBounds(new DefaultBound(0d, 0d), new DefaultBound(0d, 0d));
    }
    
}
