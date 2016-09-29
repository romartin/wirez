package org.kie.workbench.common.stunner.core.graph.processing.index.bounds;

import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.content.view.View;

public interface NodeBoundsIndexer<C> extends BoundsIndexer<C, Node<View<?>, Edge>> {
    
}
