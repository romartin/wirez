package org.wirez.bpmn.backend.marshall.json.builder;

import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;

public interface EdgeObjectBuilder<W extends Definition,  T extends Edge<View<W>, Node>> 
        extends GraphObjectBuilder<W, T> {
    
}
