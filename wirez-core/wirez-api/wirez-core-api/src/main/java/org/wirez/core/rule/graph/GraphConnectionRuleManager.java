package org.wirez.core.rule.graph;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.rule.ConnectionRuleManager;

public interface GraphConnectionRuleManager 
        extends ConnectionRuleManager<Edge<? extends View<?>, ? extends Node>, Node<? extends View<?>, ? extends Edge>> {
}
