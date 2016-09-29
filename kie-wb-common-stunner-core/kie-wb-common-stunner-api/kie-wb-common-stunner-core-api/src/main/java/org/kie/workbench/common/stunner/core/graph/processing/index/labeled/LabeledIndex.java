package org.kie.workbench.common.stunner.core.graph.processing.index.labeled;

import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.processing.index.Index;
import org.kie.workbench.common.stunner.core.graph.Edge;

import java.util.Collection;
import java.util.List;

/**
 * <p>A graph index for labeled elements.</p>
 */
public interface LabeledIndex<N extends Node, E extends Edge> extends Index<N, E> {

    /**
     * Returns the nodes with the given labels.
     */
    Collection<N> findNodes(List<String> labels);

    /**
     * Returns the edges with the given labels.
     */
    Collection<E> findEdges(List<String> labels);
    
}
