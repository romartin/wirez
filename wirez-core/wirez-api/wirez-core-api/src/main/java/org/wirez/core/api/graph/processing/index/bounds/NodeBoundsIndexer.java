package org.wirez.core.api.graph.processing.index.bounds;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;

public interface NodeBoundsIndexer<C> extends BoundsIndexer<C, Node<View<?>, Edge>> {
    
}
