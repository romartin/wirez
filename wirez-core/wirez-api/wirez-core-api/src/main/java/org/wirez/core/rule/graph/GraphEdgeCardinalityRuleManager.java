package org.wirez.core.rule.graph;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.rule.EdgeCardinalityRuleManager;

import java.util.List;

public interface GraphEdgeCardinalityRuleManager
        extends EdgeCardinalityRuleManager<Edge<? extends View<?>, Node>,
                Node<? extends View<?>, Edge>,
                List<? extends Edge>> {

}
