package org.wirez.bpmn.backend.marshall.json.parser;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;

public class EdgeParser extends ElementParser<Edge<View, Node>> {
    
    public EdgeParser(String name, Edge<View, Node> element) {
        super(name, element);
    }
    
}
