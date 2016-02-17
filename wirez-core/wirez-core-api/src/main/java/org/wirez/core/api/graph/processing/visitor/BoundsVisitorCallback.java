package org.wirez.core.api.graph.processing.visitor;

import org.wirez.core.api.graph.Bounds;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.ViewContent;

public interface BoundsVisitorCallback {

    void visitBounds(Element<? extends ViewContent> element, Bounds.Bound ul, Bounds.Bound lr);
    
}
