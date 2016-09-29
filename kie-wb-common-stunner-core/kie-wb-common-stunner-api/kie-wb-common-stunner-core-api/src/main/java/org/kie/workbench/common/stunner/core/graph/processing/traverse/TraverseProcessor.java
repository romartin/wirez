package org.kie.workbench.common.stunner.core.graph.processing.traverse;

/**
 * <p>Basic contract for any processor that traverse over the graph by moving to each adjacent nodes/vertices.</p>
 */
public interface TraverseProcessor<G, K> {
    
    void traverse(G graph, K callback);
    
}
