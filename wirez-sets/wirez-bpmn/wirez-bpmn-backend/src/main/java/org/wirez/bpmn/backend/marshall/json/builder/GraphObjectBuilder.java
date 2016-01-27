package org.wirez.bpmn.backend.marshall.json.builder;


import org.uberfire.ext.wirez.bpmn.api.BPMNWirez;
import org.uberfire.ext.wirez.core.api.graph.DefaultEdge;
import org.uberfire.ext.wirez.core.api.graph.DefaultGraph;
import org.uberfire.ext.wirez.core.api.graph.DefaultNode;
import org.uberfire.ext.wirez.core.api.graph.Element;

import java.util.Collection;

public interface GraphObjectBuilder<W extends BPMNWirez, T extends Element<W>> {

    GraphObjectBuilder<W, T> nodeId(String nodeId);

    GraphObjectBuilder<W, T> stencil(String stencilId);

    GraphObjectBuilder<W, T> property(String key, String value);

    GraphObjectBuilder<W, T> out(String nodeId);
    
    // TODO: Bounds, etc
    
    T build(BuilderContext context);
    
    interface BuilderContext<W extends BPMNWirez> {

        void init(DefaultGraph<W, DefaultNode, DefaultEdge> graph);

        DefaultGraph<W, DefaultNode, DefaultEdge> getGraph();
        
        Collection<GraphObjectBuilder<?, ?>> getBuilders();
        
    }
    
}
