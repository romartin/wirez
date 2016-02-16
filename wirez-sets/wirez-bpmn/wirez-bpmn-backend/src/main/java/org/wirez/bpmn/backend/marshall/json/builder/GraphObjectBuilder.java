package org.wirez.bpmn.backend.marshall.json.builder;


import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.impl.DefaultGraph;

import java.util.Collection;

public interface GraphObjectBuilder<W extends Definition, T extends Element<ViewContent<W>>> {
    
    GraphObjectBuilder<W, T> nodeId(String nodeId);

    GraphObjectBuilder<W, T> stencil(String stencilId);

    GraphObjectBuilder<W, T> property(String key, String value);

    GraphObjectBuilder<W, T> out(String nodeId);

    GraphObjectBuilder<W, T> boundUL(Double x, Double y);

    GraphObjectBuilder<W, T> boundLR(Double x, Double y);
    
    // TODO: Bounds, etc
    
    T build(BuilderContext context);
    
    interface BuilderContext<W extends Definition> {

        void init(DefaultGraph<ViewContent<W>, Node, Edge> graph);

        DefaultGraph<ViewContent<W>, Node, Edge> getGraph();
        
        Collection<GraphObjectBuilder<?, ?>> getBuilders();
        
    }
    
}
