package org.wirez.bpmn.backend.marshall.json.builder;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;

public interface EdgeObjectBuilder<W,  T extends Edge<View<W>, Node>> 
        extends GraphObjectBuilder<W, T> {

    Class<?> getDefinitionClass();
    
}
