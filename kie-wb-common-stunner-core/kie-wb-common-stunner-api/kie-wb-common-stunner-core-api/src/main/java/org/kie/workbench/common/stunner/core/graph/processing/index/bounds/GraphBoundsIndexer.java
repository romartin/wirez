package org.kie.workbench.common.stunner.core.graph.processing.index.bounds;

import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.view.View;

public interface GraphBoundsIndexer extends NodeBoundsIndexer<Graph<View, Node<View, Edge>>> {

    GraphBoundsIndexer setRootUUID( String uuid );

}
