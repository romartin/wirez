package org.wirez.core.graph.processing.index.bounds;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;

public interface GraphBoundsIndexer extends NodeBoundsIndexer<Graph<View, Node<View, Edge>>> {

    GraphBoundsIndexer setRootUUID( String uuid );

}
