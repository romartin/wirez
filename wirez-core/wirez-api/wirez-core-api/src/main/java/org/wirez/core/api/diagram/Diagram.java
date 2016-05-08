package org.wirez.core.api.diagram;

import org.wirez.core.api.graph.Graph;

public interface Diagram<G extends Graph, S extends Settings> {
    
    String getUUID();
    
    G getGraph();
    
    S getSettings();    
    
}
