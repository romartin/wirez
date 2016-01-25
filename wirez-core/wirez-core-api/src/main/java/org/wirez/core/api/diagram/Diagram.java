package org.wirez.core.api.diagram;

import org.wirez.core.api.graph.Graph;

public interface Diagram<S extends Settings> {
    
    String getUUID();
    
    Graph getGraph();
    
    S getSettings();    
    
}
