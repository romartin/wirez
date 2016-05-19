package org.wirez.core.diagram;

import org.wirez.core.graph.Graph;

public interface Diagram<G extends Graph, S extends Settings> {
    
    String getUUID();
    
    G getGraph();
    
    S getSettings();    
    
}
