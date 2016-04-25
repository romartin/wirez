package org.wirez.core.api.rule.graph;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.rule.EdgeCardinalityRuleManager;

import java.util.List;

public interface GraphEdgeCardinalityRuleManager
        extends EdgeCardinalityRuleManager<Edge<? extends View<?>, Node>,
        Node<? extends View<?>, Edge>,
        List<? extends Edge>> {

}
