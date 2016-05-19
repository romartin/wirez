package org.wirez.core.graph.processing.index.bounds;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;

public interface NodeBoundsIndexer<C> extends BoundsIndexer<C, Node<View<?>, Edge>> {
    
}
